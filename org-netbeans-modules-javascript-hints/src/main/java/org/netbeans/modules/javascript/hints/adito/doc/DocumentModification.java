package org.netbeans.modules.javascript.hints.adito.doc;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.*;
import org.mozilla.nb.javascript.Node;

import javax.swing.text.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @see de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade.IDocumentModification
 * @author W.Glanzer, 13.10.2017
 */
public class DocumentModification
{

  /**
   * @return ein IDocumentModification-Objekt
   * @see de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade.IDocumentModification
   */
  public static IJsUpgrade.IDocumentModification<Node> create(Document pDocument, Node pNode)
  {
    Node root = pNode;
    while(root.getParentNode() != null)
      root = root.getParentNode();

    return new ModificationContainer(pDocument, root);
  }

  /**
   * @see de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade.IDocumentModification
   */
  public static class ModificationContainer implements IJsUpgrade.IDocumentModification<Node>
  {
    private final Document document;
    private final List<List<Node>> positions = new ArrayList<>();

    private ModificationContainer(Document pDocument, Node pRootNode)
    {
      document = pDocument;
      _register(pRootNode);
    }

    @NotNull
    @Override
    public Map.Entry<Integer, Integer> getPositionOfObj(Node pObject)
    {
      // todo hier könnte man noch performance rausholen
      List<List<Node>> collect = positions.stream()
          .filter(pNodes -> pNodes.contains(pObject))
          .collect(Collectors.toList());
      if(collect.size() == 0)
        return new AbstractMap.SimpleImmutableEntry<>(-1, -1);
      List<Node> first = collect.get(0);
      List<Node> last = collect.get(collect.size() - 1);
      return new AbstractMap.SimpleImmutableEntry<>(positions.indexOf(first), positions.lastIndexOf(last) + 1); // +1, da Ende exklusiv
    }

    @Override
    public void insertString(int pIndex, @Nullable Node pNode, String pString) throws BadLocationException
    {
      document.insertString(pIndex, pString, null);

      if(pIndex >= positions.size())
      {
        for (int i = positions.size() - 1; i <= pIndex; i++)
          positions.add(new ArrayList<>());
      }

      List<Node> nodeToAddInto = positions.get(pIndex);
      for (int i = pIndex; i < pIndex + pString.length(); i++)
      {
        ArrayList<Node> listcopy = new ArrayList<>(nodeToAddInto);
        if (pNode != null && !listcopy.contains(pNode))
          listcopy.add(pNode);

        positions.add(i, listcopy);
      }
    }

    @Override
    public void remove(int pIndex, int pLength) throws BadLocationException
    {
      document.remove(pIndex, pLength);

      for(int i = 0; i < pLength; i++)
        positions.remove(pIndex);
    }

    @Override
    public int getLength()
    {
      return document.getLength();
    }

    @Override
    public String getText(int pIndex, int pLength) throws BadLocationException
    {
      return document.getText(pIndex, pLength);
    }

    private void _registerNode(Node pNode)
    {
      int start = pNode.getSourceStart();
      int end = pNode.getSourceEnd();
      for(int i = start; i < end; i++)
      {
        if(positions.size() > i)
          positions.get(i).add(pNode);
        else
        {
          ArrayList<Node> list = new ArrayList<>();
          list.add(pNode);
          positions.add(i, list);
        }
      }
    }

    private void _register(Node pNode)
    {
      _registerNode(pNode);
      Node child = pNode.getFirstChild();
      while(child != null)
      {
        _register(child);
        child = child.getNext();
      }
    }

  }

}
