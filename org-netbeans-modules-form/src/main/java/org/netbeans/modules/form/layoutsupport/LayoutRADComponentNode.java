package org.netbeans.modules.form.layoutsupport;

import org.netbeans.modules.form.*;

/**
 * todo Javadoc
 * @author Thomas Tasior 15.03.2016, 12:55
 */
public class LayoutRADComponentNode extends RADComponentNode implements ILayoutNode
{

  public LayoutRADComponentNode(RADComponent component)
  {
    super(component);
  }

  public void fireLayoutPropertiesChange()
  {
    firePropertyChange(null, null, null);
  }

  public void fireLayoutPropertySetsChange()
  {
    firePropertySetsChange(null, null);
  }
}
