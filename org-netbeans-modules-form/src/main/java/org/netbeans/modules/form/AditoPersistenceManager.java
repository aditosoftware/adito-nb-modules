package org.netbeans.modules.form;

import de.adito.aditoweb.core.util.debug.Debug;
import org.netbeans.modules.form.layoutdesign.*;
import org.netbeans.modules.form.layoutdesign.support.SwingLayoutBuilder;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.filesystems.FileObject;

import javax.swing.*;
import java.util.*;

/**
 * @author J. Boesl, 19.01.11
 */
public class AditoPersistenceManager extends PersistenceManager
{

  @Override
  public boolean canLoadForm(FormDataObject formObject) throws PersistenceException
  {
    return true;
  }

  @Override
  public void loadForm(FormDataObject formObject, FormModel formModel, List<Throwable> nonfatalErrors)
      throws PersistenceException
  {
    _loadForm(formObject.getFormEntry().getFile(), formObject.getPrimaryFile(), formModel, nonfatalErrors);
  }

  private void _loadForm(FileObject pFormFile, FileObject pAodFile,
                         FormModel pFormModel, List<Throwable> pNonfatalErrors) throws PersistenceException
  {
    if (pFormModel == null)
      pFormModel = new FormModel();

    pFormModel.setCurrentVersionLevel(FormModel.LATEST_VERSION);
    pFormModel.setMaxVersionLevel(FormModel.LATEST_VERSION);
    try
    {
      Class<JPanel> formBaseClass = JPanel.class;
      pFormModel.setFormBaseClass(formBaseClass);
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(formBaseClass);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    pFormModel.setName(pAodFile.getName());

    RADComponent topComp = pFormModel.getTopRADComponent();
    Debug.write("comp to load", topComp); // DEBUG: remove it!
    if (topComp != null) // load the main form component
      _loadComponent(pFormModel, topComp, null);

    FormEditor.updateProjectForNaturalLayout(pFormModel);
    pFormModel.setFreeDesignDefaultLayout(true);
  }

  @Override
  public void saveForm(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }


  private void _loadComponent(FormModel pFormModel, RADComponent pComponent, RADComponent pParentComponent)
      throws PersistenceException
  {
    ComponentContainer container = // is this component a container?
        pComponent instanceof ComponentContainer ?
            (ComponentContainer) pComponent : null;
    if (container == null)
    { // this component is not a container
      if (pParentComponent == null) // this is a root component - load resource properties
        ResourceSupport.loadInjectedResources(pComponent);
      return;
    }


    // we continue in loading container
    RADVisualContainer visualContainer = // is it a visual container?
        pComponent instanceof RADVisualContainer ?
            (RADVisualContainer) pComponent : null;

    if (visualContainer != null)
      visualContainer.setOldLayoutSupport(true);


    // load subcomponents
    RADComponent[] childComponents;
    if (false) // childNodes != null)
    {
      List<RADComponent> list = new ArrayList<RADComponent>();
      // TODO: process children
//      for (int i = 0, n = childNodes.getLength(); i < n; i++)
//      {
//        RADComponent newComp = restoreComponent(componentNode, component);
//        if (newComp != null)
//          list.add(newComp);
//      }
      childComponents = new RADComponent[list.size()];
      list.toArray(childComponents);
    }
    else
      childComponents = new RADComponent[0];


    Object layout = null;

    if (visualContainer != null)
    {
      Throwable layoutEx = null;
      boolean layoutInitialized = false;
      LayoutSupportManager layoutSupport = visualContainer.getLayoutSupport();

      if (layout != null)
      {
        LayoutModel layoutModel = pFormModel.getLayoutModel();
        Map<String, String> nameToIdMap = new HashMap<String, String>();
        for (int i = 0; i < childComponents.length; i++)
        {
          RADComponent comp = childComponents[i];
          nameToIdMap.put(comp.getName(), comp.getId());
        }
        try
        {
          layoutModel.loadContainerLayout(visualContainer.getId(), null /*layoutNode.getChildNodes()*/, nameToIdMap); // TODO
          visualContainer.setOldLayoutSupport(false);
          layoutSupport = null;
          layoutInitialized = true;
          //newLayout = Boolean.TRUE;  // TODO?
        }
        catch (Exception ex)
        {
          // error occurred - treat this container as with unknown layout
          layoutModel.changeContainerToComponent(visualContainer.getId());
          layoutEx = ex;
        }
        visualContainer.initSubComponents(childComponents);
      }
      else if (layout == null)
      { // Issue 63394, 68753: Bean form that is container
        // Issue 70369: Container saved with unknown layout
        // Swing menus go here as well
        // default init - not reading from code - need the subcomponents set
        visualContainer.initSubComponents(childComponents);
        try
        {
          // (default init also builds the primary container)
          layoutInitialized = layoutSupport.prepareLayoutDelegate(false, true);
          if (!layoutInitialized)
          { // not known to the old support (e.g. has GroupLayout)
            // (but we are sure the container instance is empty)
            java.awt.Container cont = layoutSupport.getPrimaryContainerDelegate();
            if (SwingLayoutBuilder.isRelevantContainer(cont))
            {
              // acknowledged by SwingLayoutBuilder - this is new layout
              visualContainer.setOldLayoutSupport(false);
              pFormModel.getLayoutModel().addRootComponent(
                  new LayoutComponent(visualContainer.getId(), true));
              layoutSupport = null;
              //newLayout = Boolean.TRUE; // TODO?
            }
            else
            {
              layoutSupport.setUnknownLayoutDelegate(false);
              System.err.println("[WARNING] Unknown layout " // TODO ?
                                     + " (" + pComponent.getBeanClass().getName() + ")"); // NOI18N
            }
            layoutInitialized = true;
          }
        }
        catch (Exception ex)
        {
          layoutEx = ex;
        }
      }

      if (!layoutInitialized)
        layoutSupport.setUnknownLayoutDelegate(false);

      /*if (layoutSupport != null && newLayout == null)   // TODO ?
      {
        newLayout = Boolean.FALSE;
      */
    }
    else
    { // non-visual container (e.g. AWT menu)
      container.initSubComponents(childComponents);
    }


//    // hack for properties that can't be set until the component is added
//    // to the parent container
//    for (RADComponent childcomp : childComponents)
//    {
//      List postProps;
//      if (parentDependentProperties != null
//          && (postProps = parentDependentProperties.get(childcomp)) != null)
//      {
//        for (Iterator it = postProps.iterator(); it.hasNext();)
//        {
//          RADProperty prop = (RADProperty) it.next();
//          Object propValue = it.next();
//          try
//          {
//            prop.setValue(propValue);
//          }
//          catch (Exception ex)
//          { // ignore
//            String msg = createLoadingErrorMessage(
//                FormUtils.getBundleString("MSG_ERR_CannotSetLoadedValue"), // NOI18N
//                node);
//            annotateException(ex, msg);
//            nonfatalErrors.add(ex);
//          }
//        }
//      }
//      // here it is also safe to load resource properties into sub-component
//      ResourceSupport.loadInjectedResources(childcomp);
//    }
//
//    // hack for properties that can't be set until all subcomponents
//    // are added to the container
//    List postProps;
//    if (childrenDependentProperties != null
//        && (postProps = childrenDependentProperties.get(component)) != null)
//    {
//      for (Iterator it = postProps.iterator(); it.hasNext();)
//      {
//        RADProperty prop = (RADProperty) it.next();
//        Object propValue = it.next();
//        try
//        {
//          prop.setValue(propValue);
//        }
//        catch (Exception ex)
//        { // ignore
//          String msg = createLoadingErrorMessage(
//              FormUtils.getBundleString("MSG_ERR_CannotSetLoadedValue"), // NOI18N
//              node);
//          annotateException(ex, msg);
//          nonfatalErrors.add(ex);
//        }
//      }
//    }

    if (pParentComponent == null) // this is a root component
      ResourceSupport.loadInjectedResources(pComponent);

  }

}
