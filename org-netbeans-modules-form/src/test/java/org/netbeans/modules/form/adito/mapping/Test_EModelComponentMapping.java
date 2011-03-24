package org.netbeans.modules.form.adito.mapping;

import de.adito.aditoweb.core.checkpoint.exception.mechanics.AditoException;
import de.adito.aditoweb.core.util.lang.InstanceUtility;
import de.adito.aditoweb.system.crmcomponents.IDataModel;
import de.adito.aditoweb.system.crmcomponents.annotations.PROCESS;
import de.adito.aditoweb.system.crmcomponents.datamodels.*;
import org.junit.*;

import java.util.*;

/**
 * @author J. Boesl, 21.03.11
 */
public class Test_EModelComponentMapping
{

  @Ignore
  @Test
  public void testMapping() throws AditoException
  {
    IDataModel dataModel = new TreeDataModel();

    String[] fields = dataModel.getFields();
    List<String> layoutProps = Arrays.asList("x", "y", "width", "height");
    for (String field : fields)
    {
      if (!field.equals("type"))
      {
        String mapped = "NO_MAPPING";
        PROCESS annotation = InstanceUtility.getAnnotation(dataModel.getAnnotations(field), PROCESS.class);
        if (annotation == null)
        {
          if (layoutProps.contains(field))
            mapped = "AALC_" + field;
          else
            mapped = field;
          mapped = "\"" + mapped + "\"";
        }
        System.out.println("pMapping.put(\"" + field + "\", " + mapped + ");");
      }
    }
  }

}
