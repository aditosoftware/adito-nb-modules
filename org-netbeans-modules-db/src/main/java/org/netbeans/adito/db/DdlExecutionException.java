package org.netbeans.adito.db;

/**
 * Eine Exception, die beim Ausführen eines CreateTableDDLs aufgetreten ist.
 *
 * @author J. Boesl, 13.02.13
 */
public class DdlExecutionException extends Exception
{
  public DdlExecutionException(Throwable cause)
  {
    super(cause);
  }
}
