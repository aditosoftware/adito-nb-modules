package de.adito.nbm.runconfig.exception;

/**
 * Thrown if a property that does not exist or cannot be found is accessed
 *
 * @author m.kaspera, 25.05.2022
 */
public class PropertyNotFoundException extends Exception
{

  public PropertyNotFoundException(String property)
  {
    super("Could not find property " + property);
  }

  public PropertyNotFoundException(String property, Throwable cause)
  {
    super("Could not find property " + property, cause);
  }
}
