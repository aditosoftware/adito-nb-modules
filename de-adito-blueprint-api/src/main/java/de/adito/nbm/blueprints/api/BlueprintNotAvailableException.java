package de.adito.nbm.blueprints.api;

/**
 * Exception that gets thrown, if a Blueprint is not available in a specific provider
 *
 * @author w.glanzer, 06.07.2020
 */
public class BlueprintNotAvailableException extends Exception
{

  public BlueprintNotAvailableException(String message)
  {
    super(message);
  }

  public BlueprintNotAvailableException(String message, Throwable cause)
  {
    super(message, cause);
  }

}
