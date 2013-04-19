package org.netbeans.modules.form.adito.components;

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

      if (compName != null)
      {
        for (NonvisContainerRADComponent a : subComponents)
        {
          for (Node.PropertySet propertySet : a.getProperties())
            for (Node.Property property : propertySet.getProperties())
            {

              if (property.getName().equals("columnName"))
              {
                String value = "";
                try
                {
                  value = property.getValue().toString();
                }
                catch (IllegalAccessException e1)
                {
                  e1.printStackTrace();
                }
                catch (InvocationTargetException e1)
                {
                  e1.printStackTrace();
                }

                if (value.equals(compName))
                  component = a;

              }
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
