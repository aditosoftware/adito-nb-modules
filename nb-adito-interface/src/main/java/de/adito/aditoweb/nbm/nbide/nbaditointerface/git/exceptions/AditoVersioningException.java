package de.adito.aditoweb.nbm.nbide.nbaditointerface.git.exceptions;

/**
 * Exception that is thrown because the versioning support could not perform the intended operation
 *
 * @author Michael Kaspera 20.10.2021
 */
public class AditoVersioningException extends Exception
{

  /**
   * Default constructor for Exceptions
   *
   * @param pMessage Message to throw further up
   */
  public AditoVersioningException(String pMessage)
  {
    super(pMessage);
  }

  /**
   * Default constructor for Exceptions
   *
   * @param pMessage Message to throw further up
   * @param pE       Exception causing this exception to be thrown
   */
  public AditoVersioningException(String pMessage, Exception pE)
  {
    super(pMessage, pE);
  }

  /**
   * Default constructor for Exceptions
   *
   * @param pE Exception causing this exception to be thrown
   */
  public AditoVersioningException(Exception pE)
  {
    super(pE);
  }
}
