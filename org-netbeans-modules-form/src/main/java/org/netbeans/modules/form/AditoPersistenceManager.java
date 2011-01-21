package org.netbeans.modules.form;

import de.adito.aditoweb.filesystem.common.AfsUrlUtil;
import de.adito.aditoweb.filesystem.datamodelfs.access.model.*;
import org.netbeans.modules.form.layoutdesign.*;
import org.netbeans.modules.form.layoutdesign.support.SwingLayoutBuilder;
import org.netbeans.modules.form.layoutsupport.LayoutSupportManager;
import org.openide.filesystems.*;

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
  public synchronized void loadForm(FormDataObject formObject, FormModel formModel, List<Throwable> nonfatalErrors)
      throws PersistenceException
  {
    _loadForm(new _Info(formObject, formModel, nonfatalErrors));
  }

  @Override
  public void saveForm(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
      throws PersistenceException
  {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  private void _loadForm(_Info pInfo)
      throws PersistenceException
  {
    FormModel formModel = pInfo.getFormModel();
    formModel.setCurrentVersionLevel(FormModel.LATEST_VERSION);
    formModel.setMaxVersionLevel(FormModel.LATEST_VERSION);
    try
    {
      Class<JPanel> formBaseClass = JPanel.class;
      formModel.setFormBaseClass(formBaseClass);
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(formBaseClass);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    formModel.setName(pInfo.getFormObject().getPrimaryFile().getName());

    RADComponent topComp = formModel.getTopRADComponent();
    if (topComp != null) // load the main form component
      _loadComponent(pInfo, pInfo.getModelRoot(), topComp, null);

    FormEditor.updateProjectForNaturalLayout(formModel);
    formModel.setFreeDesignDefaultLayout(true);
  }


  private void _loadComponent(_Info pInfo, FileObject pModelComp, RADComponent pComponent, RADComponent pParentComponent)
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
    FileObject childModels = pInfo.getModelRoot().getFileObject(FieldConst.CHILDDATAMODELS.getName());
    if (childModels != null)
    {
      List<RADComponent> list = new ArrayList<RADComponent>();
      for (FileObject childModel : childModels.getChildren())
      {
        RADComponent newComp = _restoreComponent(pInfo, childModel, pComponent);
        if (newComp != null)
          list.add(newComp);
      }
      childComponents = new RADComponent[list.size()];
      list.toArray(childComponents);
    }
    else
      childComponents = new RADComponent[0];


    Object layout = null; // TODO: um das layout zu testen muss das hier NICHT 'null' sein!

    if (visualContainer != null)
    {
      Throwable layoutEx = null;
      boolean layoutInitialized = false;
      LayoutSupportManager layoutSupport = visualContainer.getLayoutSupport();

      if (layout != null)
      {
        LayoutModel layoutModel = pInfo.getFormModel().getLayoutModel();
        Map<String, String> nameToIdMap = new HashMap<String, String>();
        for (int i = 0; i < childComponents.length; i++)
        {
          RADComponent comp = childComponents[i];
          nameToIdMap.put(comp.getName(), comp.getId());
        }
        try
        {
          layoutModel.loadContainerLayout(visualContainer.getId(), pModelComp /*layoutNode.getChildNodes()*/, nameToIdMap); // TODO
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
              pInfo.getFormModel().getLayoutModel().addRootComponent(
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

  // recognizes, creates, initializes and loads a meta component
  private RADComponent _restoreComponent(_Info pInfo, FileObject pChildModel, RADComponent pParentComponent) throws PersistenceException
  {
    EModelAccessType compType;
    String compName;
    String className;
    try
    {
      Integer type = FieldConst.TYPE.accessField(pChildModel).getValue();
      compName = FieldConst.NAME.accessField(pChildModel).getValue();
      compType = EModelAccessType.get(type);
      switch (compType)
      {
        case BUTTON:
          className = JButton.class.getName();
          break;
        case CHECKBOX:
          className = JCheckBox.class.getName();
          break;
        case COMBOBOX:
          className = JComboBox.class.getName();
          break;
        case EDITFIELD:
          className = JTextField.class.getName();
          break;
        case LABEL:
          className = JLabel.class.getName();
          break;
        case LIST:
          className = JList.class.getName();
          break;
        case RADIOBUTTON:
          className = JRadioButton.class.getName();
          break;
        case REGISTER:
          className = JTabbedPane.class.getName();
          break;
        case TABLE:
          className = JTable.class.getName();
          break;
        case TREE:
          className = JTree.class.getName();
          break;
        default:
          return null;
      }
    }
    catch (Exception e)
    {
      return null; // kein Fehler, aber auch keine Komponente.
    }

    // first load the component class
    Class<?> compClass = null;
    Throwable compEx = null;
    try
    {
      compClass = FormUtils.loadSystemClass(className);
      // Force creation of the default instance in the correct L&F context
      BeanSupport.getDefaultInstance(compClass);
    }
    catch (Exception ex)
    {
      compClass = InvalidComponent.class;
      compEx = ex;
    }
    catch (LinkageError ex)
    {
      compClass = InvalidComponent.class;
      compEx = ex;
    }
    if (compEx != null)
    { // loading the component class failed
      pInfo.getNonfatalErrors().add(compEx);
    }

    compEx = null;
    // create a new metacomponent
    RADComponent newComponent;

    switch (compType)
    {
      case BUTTON:
      case CHECKBOX:
      case COMBOBOX:
      case EDITFIELD:
      case LABEL:
      case LIST:
      case RADIOBUTTON:
      case REGISTER:
      case TABLE:
      case TREE:
        if (FormUtils.isVisualizableClass(compClass))
          newComponent = new RADVisualComponent();
        else
          newComponent = new RADComponent();
        break;
      default:
        PersistenceException ex = new PersistenceException("Unknown component element: " + pChildModel.toString()); // NOI18N
        pInfo.getNonfatalErrors().add(ex);
        return null;
    }

//    if (XML_COMPONENT.equals(nodeName))
//    {
//      if (compClass == InvalidComponent.class)
//      {
//        if (parentComponent instanceof RADVisualContainer)
//        {
//          newComponent = new RADVisualComponent();
//        }
//        else
//        {
//          newComponent = new RADComponent();
//        }
//      }
//      else
//      {
//        if (FormUtils.isVisualizableClass(compClass))
//        {
//          newComponent = new RADVisualComponent();
//        }
//        else
//        {
//          newComponent = new RADComponent();
//        }
//      }
//    }
//    else if (XML_MENU_COMPONENT.equals(nodeName))
//    {
//      if (RADVisualComponent.getMenuType(compClass) != null)
//      {
//        newComponent = new RADVisualComponent();
//      }
//      else
//      {
//        newComponent = new RADMenuItemComponent();
//      }
//    }
//    else if (XML_MENU_CONTAINER.equals(nodeName))
//    {
//      if (RADVisualComponent.getMenuType(compClass) != null)
//      {
//        newComponent = new RADVisualContainer();
//      }
//      else
//      {
//        newComponent = new RADMenuComponent();
//      }
//    }
//    else if (XML_CONTAINER.equals(nodeName))
//    {
//      if (compClass == InvalidComponent.class)
//      {
//        newComponent = new RADVisualContainer();
//      }
//      else
//      {
//        if (java.awt.Container.class.isAssignableFrom(compClass))
//          newComponent = new RADVisualContainer();
//        else newComponent = new RADContainer();
//      }
//    }
//    else
//    {
//      PersistenceException ex = new PersistenceException(
//          "Unknown component element"); // NOI18N
//      annotateException(ex,
//                        ErrorManager.ERROR,
//                        FormUtils.getFormattedBundleString("FMT_ERR_UnknownElement", // NOI18N
//                                                           new Object[]{nodeName})
//      );
//      nonfatalErrors.add(ex);
//      return null;
//    }

    // initialize the metacomponent
    try
    {
      if (compClass == InvalidComponent.class)
      {
        newComponent.setValid(false);
        newComponent.setMissingClassName(className);
      }
      newComponent.initialize(pInfo.getFormModel());
      newComponent.setStoredName(compName);
      newComponent.initInstance(compClass);
      newComponent.setInModel(true);
    }
    catch (Exception ex)
    {
      compEx = ex;
    }
    catch (LinkageError ex)
    {
      compEx = ex;
    }
    if (compEx != null)
    { // creating component instance failed
      pInfo.getNonfatalErrors().add(compEx);
      return null;
    }

    pInfo.getComponentsMap().put(compName, newComponent);

    // load the metacomponent (properties, events, layout, etc)
    _loadComponent(pInfo, pChildModel, newComponent, pParentComponent);

    return newComponent;
  }


  /**
   * Klasse mit Daten über den aktuellen Vorgang.
   */
  private class _Info
  {
    private FormDataObject formObject;
    private FormModel formModel;
    private List<Throwable> nonfatalErrors;
    private FileObject modelRoot;

    private Map<String, RADComponent> loadedComponents;


    private _Info(FormDataObject pFormObject, FormModel pFormModel, List<Throwable> pNonfatalErrors)
    {
      formObject = pFormObject;
      formModel = pFormModel;
      if (formModel == null)
        formModel = new FormModel();
      nonfatalErrors = pNonfatalErrors;

      try
      {
        FileObject aodFile = getFormObject().getPrimaryFile();
        modelRoot = URLMapper.findFileObject(AfsUrlUtil.createAdmFsUrl(aodFile.getURL()));
      }
      catch (Exception e)
      {
        throw new RuntimeException(e); // TODO: runtimeEx
      }
    }

    public FormDataObject getFormObject()
    {
      return formObject;
    }

    public FormModel getFormModel()
    {
      return formModel;
    }

    public List<Throwable> getNonfatalErrors()
    {
      return nonfatalErrors;
    }

    public FileObject getModelRoot()
    {
      return modelRoot;
    }

    public Map<String, RADComponent> getComponentsMap()
    {
      if (loadedComponents == null)
        loadedComponents = new HashMap<String, RADComponent>(50);
      return loadedComponents;
    }
  }

}
