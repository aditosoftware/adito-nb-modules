package de.adito.nbm.translation.api;

/**
 * @author W.Glanzer, 05.04.2017
 */
public class TranslationException extends RuntimeException
{

  public TranslationException(String message)
  {
    super(message);
  }

  public TranslationException(String message, Throwable cause)
  {
    super(message, cause);
  }
}
