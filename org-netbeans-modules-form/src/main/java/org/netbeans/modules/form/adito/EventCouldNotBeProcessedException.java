package org.netbeans.modules.form.adito;

/**
 * Exception die geworfen wird, wenn die Änderungen aus einem NetBeans-ChangeEvent wieder rückabgewickelt werden sollen.
 *
 * @author J. Boesl, 28.04.2014
 */
public class EventCouldNotBeProcessedException extends RuntimeException
{

  public EventCouldNotBeProcessedException(Throwable cause)
  {
    super(cause);
  }

}
