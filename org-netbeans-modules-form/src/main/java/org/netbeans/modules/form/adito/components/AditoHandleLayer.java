package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.INonSwingComponent;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.RADNonVisualContainerNonVisualComponent;

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


  public static boolean canHandle(RADComponent pMetacomp)
  {
    return pMetacomp instanceof RADNonVisualContainerNonVisualComponent;
  }

  public static void layerPaint(Graphics2D pG, FormDesigner pFormDesigner, RADComponent pMetacomp)
  {

    Point translationPoint = new Point();
    RADComponent parentRad = pMetacomp;
    while (true)
    {
      _translate(translationPoint, _getTranslationPoint(pFormDesigner, parentRad));
      if (parentRad == null || parentRad instanceof RADVisualComponent)
        break;
      parentRad = parentRad.getParentComponent();
    }

    INonSwingComponent nonSwingComponent = (INonSwingComponent) pFormDesigner.getComponent(pMetacomp);
    Rectangle visRect = AditoAccess.visibleRect(pFormDesigner, (Component) pFormDesigner.getComponent(parentRad));
    _layerPaint(pG, translationPoint, visRect, nonSwingComponent);


    //NonvisContainerRADVisualComponent parentRad = (NonvisContainerRADVisualComponent) component.getParentComponent();
    //Component parentComp = (Component) pFormDesigner.getComponent(parentRad);
    //
    //if (parentComp == null || !parentComp.isShowing())
    //  return;
    //
    //Object comp = pFormDesigner.getComponent(pMetacomp);
    //Point containerCoords = AditoAccess.pointFromComponentToHandleLayer(pFormDesigner, parentComp);
    //Rectangle visRect = AditoAccess.visibleRect(pFormDesigner, parentComp);
    //
    //_layerPaint(pG, containerCoords, visRect, parentComp, comp);
  }

  private static void _layerPaint(Graphics2D pG, Point pTranslationPoint, Rectangle pVisibleRect,
                                  INonSwingComponent pNonSwingComponent)
  {
    Color oldColor = pG.getColor();
    Shape oldClip = pG.getClip();
    Area visibleArea = new Area(pVisibleRect);
    if (oldClip != null)
      visibleArea.intersect(new Area(oldClip));
    pG.setClip(visibleArea);
    pG.translate(pTranslationPoint.x, pTranslationPoint.y);
    pNonSwingComponent.paintSelection(pG);
    pG.translate(-pTranslationPoint.x, -pTranslationPoint.y);
    pG.setClip(oldClip);
    pG.setColor(oldColor);
  }

  //private static void _layerPaint(Graphics2D pG, Point pContainerCoords, Rectangle pVisibleRect,
  //                                Component pParentComp, Object pComp)
  //{
  //  Color oldColor = pG.getColor();
  //  Shape oldClip = pG.getClip();
  //  Area visibleArea = new Area(pVisibleRect);
  //  if (oldClip != null)
  //    visibleArea.intersect(new Area(oldClip));
  //  pG.setClip(visibleArea);
  //  pG.translate(pContainerCoords.x, pContainerCoords.y);
  //  _layerPaint(pG, pParentComp, pComp);
  //  pG.translate(-pContainerCoords.x, -pContainerCoords.y);
  //  pG.setClip(oldClip);
  //  pG.setColor(oldColor);
  //}

  //private static void _layerPaint(Graphics2D pG, Component pParentComp, Object pComp)
  //{
  //  Rectangle rect = ((INonSwingContainer) pParentComp).getChildBounds(pComp);
  //  if (rect == null)
  //    return;
  //  pG.drawRect(rect.x, rect.y, rect.width, rect.height);
  //  pG.setColor(new Color(pG.getColor().getRed(), pG.getColor().getGreen(), pG.getColor().getBlue(), 32));
  //  pG.fillRect(rect.x, rect.y, rect.width, rect.height);
  //}

  private static void _translate(Point pPoint, Point pTranslationPoint)
  {
    pPoint.translate(pTranslationPoint.x, pTranslationPoint.y);
  }

  private static Point _getTranslationPoint(FormDesigner pFormDesigner, RADComponent pRadComponent)
  {
    Object comp = pRadComponent == null ? null : pFormDesigner.getComponent(pRadComponent);
    if (comp instanceof INonSwingComponent)
    {
      Rectangle bounds = ((INonSwingComponent) comp).getBounds();
      if (bounds != null)
        return bounds.getLocation();
    }
    else if (comp instanceof Component)
      return AditoAccess.pointFromComponentToHandleLayer(pFormDesigner, (Component) comp);
    return new Point();
  }

}
