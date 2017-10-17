package org.netbeans.modules.javascript.hints.adito.doc;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.javascript.IJsUpgrade;
import org.jetbrains.annotations.Nullable;
import org.junit.*;
import org.mozilla.nb.javascript.*;
import org.netbeans.editor.BaseDocument;

import javax.swing.text.*;
import java.util.Map;

/**
 * @author W.Glanzer, 13.10.2017
 */
public class Test_DocumentModification
{

  private BaseDocument document;
  private Node root;
  private Node child1;
  private Node child2;
  private Node child21;
  private Node child22;
  private IJsUpgrade.IDocumentModification<Node> modification;

  @Before
  public void setUp() throws Exception
  {
    document = new BaseDocument(false, "text/javascript");
    root = _createNode(null, "root", 0, 100);

    // Random Text im Dokument erzeugen
    StringBuilder text = new StringBuilder();
    for(int i = root.getSourceStart(); i < root.getSourceEnd(); i++)
      text.append(i % 10);
    document.insertString(0, text.toString(), null);

    child1 = _createNode(root, "child1", 0, 10);
    child2 = _createNode(root, "child2", 30, 100);
    child21 = _createNode(child2, "child2-1", 40, 60);
    child22 = _createNode(child2, "child2-2", 60, 70);

    modification = DocumentModification.create(document, root);
  }

  @Test
  public void test_insert() throws Exception
  {
    modification.insertString(0, "insertedString");
    _assertPositionEquals(modification.getPositionOfObj(child1), 0, 23);
    _assertPositionEquals(modification.getPositionOfObj(child21), 54, 73);

    modification.insertString(60, "fifty");
    _assertPositionEquals(modification.getPositionOfObj(root), 0, 118);
    _assertPositionEquals(modification.getPositionOfObj(child21), 54, 78);
    _assertPositionEquals(modification.getPositionOfObj(child22), 79, 88);
  }

  @Test
  public void test_remove() throws Exception
  {
    modification.remove(0, 5);
    _assertPositionEquals(modification.getPositionOfObj(root), 0, 94);
    _assertPositionEquals(modification.getPositionOfObj(child1), 0, 4);
    _assertPositionEquals(modification.getPositionOfObj(child2), 25, 94);
    _assertPositionEquals(modification.getPositionOfObj(child22), 55, 64);

    modification.remove(55, 10);
    _assertPositionEquals(modification.getPositionOfObj(child22), -1, -1);
    _assertPositionEquals(modification.getPositionOfObj(root), 0, 84);

  }

  private void _assertPositionEquals(Map.Entry<Integer, Integer> pPosition, int pStart, int pEnd)
  {
    Assert.assertEquals((Object) pStart, pPosition.getKey());
    Assert.assertEquals((Object) pEnd, pPosition.getValue());
  }

  /**
   * @return Erstellt eine Dummy-Node und fügt sie beim Parent ein
   */
  private Node _createNode(@Nullable Node pParent, String pName, int pStart, int pEnd)
  {
    FunctionNode node = new FunctionNode(pName);
    node.setSourceBounds(pStart, pEnd);
    if(pParent != null)
    {
      node.setParentNode(pParent);
      pParent.addChildrenToBack(node);
    }
    return node;
  }

  /**
   * @return Wandelt eine Node in einen lesbaren String um, bei dem die Typen ausgegeben werden
   */
  public static String toDebugString(Node pNode, int pLevel, IJsUpgrade.IDocumentModification<Node> pModification) throws BadLocationException
  {
    StringBuilder result = new StringBuilder();

    // meine Node printen
    for(int i = 0; i < pLevel; i++)
      result.append("  ");

    Map.Entry<Integer, Integer> pos = pModification.getPositionOfObj(pNode);
    if(pos.getKey() == -1 || pos.getValue() == -1)
      return "";

    result.append(((FunctionNode) pNode).getFunctionName())
        .append(" -> '")
        .append(pos.getKey()).append("-").append(pos.getValue())
        .append(" -> '")
        .append(pModification.getText(pos.getKey(), pos.getValue() - pos.getKey()).replaceAll("\n", " \\\\n"))
        .append("'\n");

    // Kinder
    Node child = pNode.getFirstChild();
    while(child != null)
    {
      result.append(toDebugString(child, pLevel+1, pModification));
      child = child.getNext();
    }

    return result.toString();
  }

}
