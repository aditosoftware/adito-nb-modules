package de.adito.aditoweb.nbm.nbide.nbaditointerface.liquibase;

/**
 * Determines the location of a database using its JDBC URL.
 * The location can be: On the local machine,
 * remote (everywhere in the universe),
 * or not exactly localizable.
 * This can happens if the test mechanism doesn't fail,
 * but cannot exactly determine the location.
 * This is a security feature, because dropall on
 * customer's database wouldn't be good...
 *
 * @author t.tasior, 25.04.2019
 * @see EResult
 */
public interface IJDBCURLTester
{
  EResult test(String pJDBCURL);

  public enum EResult
  {
    LOCAL,
    POTENTIALLY_REMOTE,
    REMOTE;
  }
}
