package de.adito.swing.icon;

/**
 * Exception if an icon is requested but not found
 *
 * @author m.kaspera, 16.07.2020
 */
public class VaadinIconNotFoundException extends RuntimeException
{

  public VaadinIconNotFoundException()
  {
  }

  public VaadinIconNotFoundException(String message)
  {
    super(message);
  }

  public VaadinIconNotFoundException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public VaadinIconNotFoundException(Throwable cause)
  {
    super(cause);
  }
}
