package org.netbeans.modules.form.layoutsupport;

/**
 * Interface für den Benachrichtigungsmechanismus
 * des Layoutnodes.
 * @author Thomas Tasior 15.03.2016, 12:50
 */
public interface ILayoutNode
{
  void fireLayoutPropertiesChange();

  void fireLayoutPropertySetsChange();
}
