package org.netbeans.modules.javascript.hints.adito.doc;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.*;
import org.mozilla.nb.javascript.Node;

import javax.swing.text.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author W.Glanzer, 13.10.2017
 * @see de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade.IDocumentModification
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
    while (root.getParentNode() != null)
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

      // Den Rest mit den RootNodes auffüllen. Dies wird zb benötigt, da
      // Kommentare in der letzten Zeile nicht als sourceEnd in der rootNode bekannt sind.
      if (positions.size() < getLength())
      {
        int oldSize = getLength() - positions.size();
        for (int i = 0; i < oldSize; i++)
        {
          ArrayList<Node> list = new ArrayList<>();
          list.add(pRootNode);
          positions.add(list);
        }
      }
    }

    @NotNull
    @Override
    public Map.Entry<Integer, Integer> getPositionOfObj(Node pObject)
    {
      // todo hier könnte man noch performance rausholen
      List<List<Node>> collect = positions.stream()
          .filter(pNodes -> pNodes.contains(pObject))
          .collect(Collectors.toList());
      if (collect.size() == 0)
        return new AbstractMap.SimpleImmutableEntry<>(-1, -1);
      List<Node> first = collect.get(0);
      List<Node> last = collect.get(collect.size() - 1);
      return new AbstractMap.SimpleImmutableEntry<>(positions.indexOf(first), positions.lastIndexOf(last) + 1); // +1, da Ende exklusiv
    }

    @Override
    public void insertString(int pIndex, String pString) throws BadLocationException
    {
      _insertString(pIndex, null, pString);
    }

    @Override
    public void remove(int pIndex, int pLength) throws BadLocationException
    {
      document.remove(pIndex, pLength);

      for (int i = 0; i < pLength; i++)
        positions.remove(pIndex);
    }

    @Override
    public void replace(int pIndex, int pLength, String pString) throws BadLocationException
    {
      List<Node> allNodesOnIndex = positions.get(pIndex);
      remove(pIndex, pLength);
      _insertString(pIndex, allNodesOnIndex, pString);
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

    @NotNull
    public String toDisplayString()
    {
      StringBuilder builder = new StringBuilder();
      positions.stream()
          .flatMap(Collection::stream)
          .distinct()
          .sorted((pNode1, pNode2) -> {
            Map.Entry<Integer, Integer> pos1 = getPositionOfObj(pNode1);
            Map.Entry<Integer, Integer> pos2 = getPositionOfObj(pNode2);
            if (pos1.getKey() == -1 || pos1.getValue() == -1 || pos2.getKey() == -1 || pos2.getValue() == -1)
              return 0;
            return pos1.getKey() - pos2.getKey();
          })
          .forEachOrdered(pNode -> {
            try
            {
              int maxLength = 30;
              Map.Entry<Integer, Integer> position = getPositionOfObj(pNode);
              String nodeString = pNode.toString();
              if (nodeString.length() > maxLength)
                builder.append(nodeString.substring(0, maxLength).replaceAll("\\n", "\\n"));
              else
              {
                builder.append(nodeString);
                for (int i = 0; i < maxLength - nodeString.length(); i++)
                  builder.append(" ");
              }

              builder.append(" -> ");

              for (int i = 0; i < position.getKey(); i++)
                builder.append(" ");
              for (int i = position.getKey(); i < position.getValue(); i++)
                builder.append(getText(i, 1));
              builder.append("\n");
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
          });

      return builder.toString();
    }

    private void _insertString(int pIndex, Collection<Node> pNodes, String pString) throws BadLocationException
    {
      document.insertString(pIndex, pString, null);

      if (pIndex >= positions.size())
      {
        for (int i = positions.size() - 1; i <= pIndex; i++)
          positions.add(new ArrayList<>());
      }

      List<Node> nodeToAddInto = positions.get(pIndex);
      for (int i = pIndex; i < pIndex + pString.length(); i++)
      {
        ArrayList<Node> listcopy = new ArrayList<>(nodeToAddInto);
        if (pNodes != null)
          for (Node node : pNodes)
            if (!listcopy.contains(node))
              listcopy.add(node);

        positions.add(i, listcopy);
      }
    }

    private void _registerNode(Node pNode)
    {
      int start = pNode.getSourceStart();
      int end = pNode.getSourceEnd();
      for (int i = start; i < end; i++)
      {
        if (positions.size() > i)
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
      while (child != null)
      {
        _register(child);
        child = child.getNext();
      }
    }

  }

}
