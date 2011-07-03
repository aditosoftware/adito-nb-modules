package org.netbeans.modules.form;

import java.awt.*;

/**
 * Für Zugriff auf protected Methoden.
 *
 * @author J. Boesl, 01.07.11
 */
public final class AditoAccess
{

  private AditoAccess()
  {
  }

  public static Point pointFromComponentToHandleLayer(FormDesigner pFormDesigner, Point p, Component targetComp)
  {
    return pFormDesigner.pointFromComponentToHandleLayer(p, targetComp);
  }

  public static Rectangle visibleRect(FormDesigner pFormDesigner, Component pComponent)
  {
    Rectangle rect = new Rectangle(0, 0, pComponent.getWidth(), pComponent.getHeight());
    return pFormDesigner.getHandleLayer().convertVisibleRectangleFromComponent(rect, pComponent);
  }

}
