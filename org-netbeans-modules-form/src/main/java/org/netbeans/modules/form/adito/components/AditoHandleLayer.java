package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonVisualsContainer;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.RADNonVisualContainerVisualComponent;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Funktionen die über die Klasse HandleLayer angeboten werden, aber Erweiterungen von Adito sind.
 *
 * @author J. Boesl, 01.07.11
 */
public final class AditoHandleLayer
{

  private AditoHandleLayer()
  {
  }

  public static RADVisualComponent getParent(RADComponent pMetacomp)
  {
    RADComponent parent = pMetacomp.getParentComponent();
    if (parent instanceof RADVisualComponent)
      return (RADVisualComponent) parent;
    return null;
  }

  public static boolean canHandle(RADComponent pMetacomp)
  {
    return pMetacomp.getParentComponent() instanceof RADNonVisualContainerVisualComponent;
  }

  public static void layerPaint(Graphics2D pG, FormDesigner pFormDesigner, RADComponent pMetacomp)
  {
    RADNonVisualContainerVisualComponent parentRad =
        (RADNonVisualContainerVisualComponent) pMetacomp.getParentComponent();
    Component parentComp = (Component) pFormDesigner.getComponent(parentRad);

    if (parentComp == null || !parentComp.isShowing())
      return;

    Object comp = pFormDesigner.getComponent(pMetacomp);
    Point containerCoords = AditoAccess.pointFromComponentToHandleLayer(pFormDesigner, new Point(), parentComp);
    Rectangle visRect = AditoAccess.visibleRect(pFormDesigner, parentComp);

    _layerPaint(pG, containerCoords, visRect, parentComp, comp);
  }

  private static void _layerPaint(Graphics2D pG, Point pContainerCoords, Rectangle pVisibleRect,
                                  Component pParentComp, Object pComp)
  {
    Color oldColor = pG.getColor();
    Shape oldClip = pG.getClip();
    Area visibleArea = new Area(pVisibleRect);
    if (oldClip != null)
      visibleArea.intersect(new Area(oldClip));
    pG.setClip(visibleArea);
    pG.translate(pContainerCoords.x, pContainerCoords.y);
    _layerPaint(pG, pParentComp, pComp);
    pG.translate(-pContainerCoords.x, -pContainerCoords.y);
    pG.setClip(oldClip);
    pG.setColor(oldColor);
  }

  private static void _layerPaint(Graphics2D pG, Component pParentComp, Object pComp)
  {
    Rectangle rect = ((INonVisualsContainer) pParentComp).getChildBounds(pComp);
    if (rect == null)
      return;
    pG.drawRect(rect.x, rect.y, rect.width, rect.height);
    pG.setColor(new Color(pG.getColor().getRed(), pG.getColor().getGreen(), pG.getColor().getBlue(), 32));
    pG.fillRect(rect.x, rect.y, rect.width, rect.height);
  }

}
