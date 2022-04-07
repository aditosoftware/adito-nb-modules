/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.lsp.client.bindings;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.jetbrains.annotations.NotNull;
import org.netbeans.modules.lsp.client.LSPBindingFactory;
import org.netbeans.modules.lsp.client.LSPBindings;
import org.netbeans.modules.lsp.client.LSPWorkingPool;
import org.netbeans.modules.lsp.client.LSPWorkingPool.BackgroundTask;
import org.netbeans.modules.lsp.client.Utils;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.BeanTreeView;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.URLMapper;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.util.lookup.ServiceProvider;

/**
 * @author lahvac
 */
public class NavigatorPanelImpl extends Children.Keys<NavigatorPanelImpl._NodeKey> implements NavigatorPanel, BackgroundTask, LookupListener
{

  private static final NavigatorPanelImpl INSTANCE = new NavigatorPanelImpl();

  private final ExplorerManager manager;
  private View view;
  private Lookup.Result<FileObject> result;
  private FileObject file;

  public NavigatorPanelImpl()
  {
    manager = new ExplorerManager();
    manager.setRootContext(new AbstractNode(this));
  }

  @Override
  @Messages("DN_Symbols=Symbols")
  public String getDisplayName()
  {
    return Bundle.DN_Symbols();
  }

  @Override
  public String getDisplayHint()
  {
    return "symbols";
  }

  @Override
  public JComponent getComponent()
  {
    if (view == null)
    {
      view = new View();
    }
    return view;
  }

  @Override
  public void panelActivated(Lookup context)
  {
    result = context.lookupResult(FileObject.class);
    result.addLookupListener(this);
    updateFile();
  }

  @Override
  public void panelDeactivated()
  {
    if (result != null)
      result.removeLookupListener(this);
    result = null;
    updateFile();
  }

  private void updateFile()
  {
    if (file != null)
    {
      LSPWorkingPool.removeBackgroundTask(file, this);
      setKeys(Collections.emptyList());
      file = null;
    }
    Collection<? extends FileObject> files = (result != null) ? result.allInstances() : Collections.emptyList();
    file = files.isEmpty() ? null : files.iterator().next();
    if (file != null)
    {
      LSPWorkingPool.addBackgroundTask(file, this);
    }
  }

  @Override
  public Lookup getLookup()
  {
    return Lookup.EMPTY;
  }

  @Override
  public void run(LSPBindings bindings, FileObject file)
  {
    if (file.equals(this.file))
    {
      try
      {
        String uri = Utils.toURI(file);
        TextDocumentService docService = bindings.getTextDocumentService();

        CompletableFuture<List<Either<SymbolInformation, DocumentSymbol>>> s =
            docService.documentSymbol(new DocumentSymbolParams(new TextDocumentIdentifier(uri)));
        List<Either<SymbolInformation, DocumentSymbol>> symbols = s.get();

        if (symbols != null)
        {
          List<_NodeKey> keys = symbols.stream()
              .map(pEither -> {
                _NodeKey key;
                if (pEither.isLeft())
                  key = new _NodeKey(pEither.getLeft());
                else
                  key = new _NodeKey(pEither.getRight());
                return key;
              })
              .collect(Collectors.toList());

          // update own keys
          setKeys(keys);

          // update all other keys
          Arrays.stream(getNodes(true))
              .filter(NodeImpl.class::isInstance)
              .map(NodeImpl.class::cast)
              .forEach(pChildNode -> pChildNode.updateNodeKey(keys.stream()
                                                                  .filter(pChildKey -> Objects.equals(pChildKey, pChildNode.getKey()))
                                                                  .findFirst()
                                                                  .orElseThrow()));
        }
      }
      catch (ExecutionException ex)
      {
//                LOG.log(Level.WARNING, "Can't load data from TextDocumentService", ex);
        setKeys(Collections.emptyList());
      }
      catch (InterruptedException ex)
      {
        //try again:
        LSPWorkingPool.addBackgroundTask(file, this);
      }
    }
    // else ignore, should be called with the other file eventually.
  }

  @Override
  protected Node[] createNodes(_NodeKey sym)
  {
    return new Node[]{new NodeImpl(Utils.toURI(file), sym)};
  }

  @Override
  public void resultChanged(LookupEvent arg0)
  {
    updateFile();
  }

  private static class _Children extends Keys<_NodeKey>
  {
    private final String fileUri;
    private final List<_NodeKey> initialChildren;

    private _Children(String pFileUri, List<_NodeKey> pInitialChildren)
    {
      fileUri = pFileUri;
      initialChildren = pInitialChildren;
    }

    @Override
    protected void addNotify()
    {
      setKeys(initialChildren);
    }

    @Override
    protected Node[] createNodes(_NodeKey sym)
    {
      return new Node[]{
          new NodeImpl(fileUri, sym)
      };
    }

    @Override
    protected void removeNotify()
    {
      setKeys(Collections.emptyList());
    }

    public void updateKeys(List<_NodeKey> pKeys)
    {
      setKeys(pKeys.toArray(new _NodeKey[0]));
    }
  }

  private static final class NodeImpl extends AbstractNode
  {
    private _NodeKey key;
    private final Action open;

    private static Children createChildren(String pCurrentFileUri, _NodeKey pKey)
    {
      if (pKey.isSymbol() || pKey.getChildren().isEmpty())
        return LEAF;

      return new _Children(pCurrentFileUri, pKey.getChildren());
    }

    public NodeImpl(String currentFileUri, _NodeKey symbol)
    {
      super(createChildren(currentFileUri, symbol));

      open = new AbstractAction()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          Utils.open(currentFileUri, getKey().getRange());
        }
      };
      updateNodeKey(symbol);
    }

    public _NodeKey getKey()
    {
      return key;
    }

    public void updateNodeKey(@NotNull _NodeKey pKey)
    {
      key = pKey;

      setDisplayName(pKey.getName());
      String symbolIconBase = Icons.getSymbolIconBase(pKey.getKind());
      if (symbolIconBase != null)
        setIconBaseWithExtension(symbolIconBase);

      // adjust children
      Children children = getChildren();
      if (children instanceof _Children)
      {
        // update keys of children
        ((_Children) children).updateKeys(pKey.getChildren());

        // refresh existing children
        Arrays.stream(children.getNodes(true))
            .filter(NodeImpl.class::isInstance)
            .map(NodeImpl.class::cast)
            .forEach(pChildNode -> pChildNode.updateNodeKey(pKey.getChildren().stream()
                                                                .filter(pChildKey -> Objects.equals(pChildKey, pChildNode.getKey()))
                                                                .findFirst()
                                                                .orElseThrow()));
      }
    }

    @Override
    public Action getPreferredAction()
    {
      return open;
    }

  }

  /**
   * Key for a node
   */
  protected static class _NodeKey
  {
    private final String name;
    private final Range range;
    private final SymbolKind kind;
    private final boolean symbol;
    private final List<_NodeKey> children;

    public _NodeKey(@NotNull SymbolInformation pSymbolInformation)
    {
      symbol = true;
      name = pSymbolInformation.getName();
      range = pSymbolInformation.getLocation().getRange();
      kind = pSymbolInformation.getKind();
      children = new ArrayList<>();
    }

    public _NodeKey(@NotNull DocumentSymbol pDocumentSymbol)
    {
      symbol = false;
      name = pDocumentSymbol.getDetail().isEmpty() ? pDocumentSymbol.getName() : pDocumentSymbol.getDetail();
      range = pDocumentSymbol.getRange();
      kind = pDocumentSymbol.getKind();
      children = new ArrayList<>();
      pDocumentSymbol.getChildren().forEach(pSymbol -> children.add(new _NodeKey(pSymbol)));
    }

    public boolean isSymbol()
    {
      return symbol;
    }

    @NotNull
    public String getName()
    {
      return name;
    }

    @NotNull
    public SymbolKind getKind()
    {
      return kind;
    }

    @NotNull
    public List<_NodeKey> getChildren()
    {
      return children;
    }

    @NotNull
    public Range getRange()
    {
      return range;
    }

    @Override
    public boolean equals(Object pO)
    {
      if (this == pO) return true;
      if (!(pO instanceof _NodeKey)) return false;
      _NodeKey nodeKey = (_NodeKey) pO;
      return symbol == nodeKey.symbol && Objects.equals(name, nodeKey.name) && kind == nodeKey.kind;
    }

    @Override
    public int hashCode()
    {
      return Objects.hash(name, kind, symbol);
    }
  }

  private class View extends JPanel implements ExplorerManager.Provider
  {
    private final BeanTreeView internalView;

    public View()
    {
      setLayout(new BorderLayout());
      this.internalView = new BeanTreeView();
      add(internalView, BorderLayout.CENTER);

      internalView.setRootVisible(false);
    }

    @Override
    public ExplorerManager getExplorerManager()
    {
      return manager;
    }
  }

  @SuppressWarnings("unused") // ServiceProvider
  @ServiceProvider(service = DynamicRegistration.class)
  public static final class DynamicRegistrationImpl implements DynamicRegistration
  {

    @Override
    public Collection<? extends NavigatorPanel> panelsFor(URI uri)
    {
      try
      {
        FileObject file = URLMapper.findFileObject(uri.toURL());
        if (file != null)
        {
          return LSPBindingFactory.getBindingForFile(file) != null ? Collections.singletonList(INSTANCE) : Collections.emptyList();
        }
        else
        {
          return Collections.emptyList();
        }
      }
      catch (MalformedURLException ex)
      {
        //ignore
        return Collections.emptyList();
      }
    }

  }
}
