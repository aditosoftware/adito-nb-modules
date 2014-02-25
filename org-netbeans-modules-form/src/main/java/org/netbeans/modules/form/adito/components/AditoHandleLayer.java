package org.netbeans.modules.form.adito.components;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.component.*;
import de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.*;
import org.netbeans.modules.form.*;
import org.netbeans.modules.form.adito.perstistencemanager.*;
import org.openide.nodes.Node;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.lang.reflect.InvocationTargetException;

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
    return pMetacomp instanceof NonvisContainerRADComponent;
  }

  public static void layerPaint(Graphics2D pG, FormDesigner pFormDesigner, RADComponent pMetacomp)
  {

    Point translationPoint = new Point();
    RADComponent parentRad = pMetacomp;
    while (true)
    {
      if (parentRad == null)
        return;
      if (parentRad instanceof RADVisualComponent)
        break;
      parentRad = parentRad.getParentComponent();
    }
    _translate(translationPoint, _getTranslationPoint(pFormDesigner, parentRad));

    INonSwingComponent nonSwingComponent = (INonSwingComponent) pFormDesigner.getComponent(pMetacomp);
    Component parentComponent = (Component) pFormDesigner.getComponent(parentRad);
    if (parentComponent == null)
      return;
    Rectangle visRect = AditoAccess.visibleRect(pFormDesigner, parentComponent);
    _layerPaint(pG, translationPoint, visRect, nonSwingComponent);
  }

  public static void mouseClick(FormDesigner pFormDesigner, RADComponent pMetacomp, MouseEvent e)
  {

    Object comp = pMetacomp == null ? null : pFormDesigner.getComponent(pMetacomp);
    if (comp instanceof INonSwingContainer)
    {
      ((INonSwingContainer) comp).executeMouseClick(e);
    }
  }

  public static NonvisContainerRADComponent getSubComponent(RADComponent pComp, FormDesigner pFormDesigner, MouseEvent e)
  {

    String compName = null;
    NonvisContainerRADComponent component = null;

    if (pComp instanceof NonvisContainerRADVisualComponent)
    {
      NonvisContainerRADComponent[] subComponents = ((NonvisContainerRADVisualComponent) pComp).getSubBeans();

      Object comp = pComp == null ? null : pFormDesigner.getComponent(pComp);
      if (comp instanceof INonSwingContainer)
      {
        compName = ((INonSwingContainer) comp).getSubComponentName(e);
      }

      if (compName != null && pComp.getBeanInstance() instanceof IARibbonMarker)  // Unterkomponenten des Ribbons
      {
        for (int i = 0; i < subComponents.length; i++)
          for (NonvisContainerRADComponent sub : subComponents[i].getSubBeans())
          {
            for (NonvisContainerRADComponent child : sub.getSubBeans())
            {
              if (child.getName().equals(compName.substring(0, compName.indexOf("/\\"))) &&
                  child.getParentComponent().getName().equals(compName.substring(compName.indexOf("/\\") + 2)))
              {
                component = child;
                break;
              }
            }
            if (sub.getName().equals(compName.substring(0, compName.indexOf("/\\"))) &&
                sub.getParentComponent().getName().equals(compName.substring(compName.indexOf("/\\") + 2)))
            {
              component = sub;
              break;
            }
          }
      }

      else if (compName != null && pComp.getBeanInstance() instanceof IATableMarker) // Unterkomponenten einer Tabelle
      {
        for (NonvisContainerRADComponent a : subComponents)
        {
                if (a.getName().equals(compName))
                {
                  component = a;
                  break;
                }
        }
      }
    }
    return component;
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

  private static void _translate(Point pPoint, Point pTranslationPoint)
  {
    pPoint.translate(pTranslationPoint.x, pTranslationPoint.y);
  }

  private static Point _getTranslationPoint(FormDesigner pFormDesigner, RADComponent pRadComponent)
  {
    Object comp = pRadComponent == null ? null : pFormDesigner.getComponent(pRadComponent);
    if (comp instanceof INonSwingComponent)
    {
      Rectangle bounds = ((INonSwingComponent) comp).getBoundsNonSwing();
      if (bounds != null)
        return bounds.getLocation();
    }
    else if (comp instanceof Component)
      return AditoAccess.pointFromComponentToHandleLayer(pFormDesigner, (Component) comp);
    return new Point();
  }

}
