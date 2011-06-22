package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

/**
 * Ein Layout für den Formeditor.
 *
 * @author J. Boesl, 22.06.11
 */
public interface IAditoLayout<T>
{

  /**
   * Erstellt Constraints.
   *
   * @return LayoutConstraints.
   */
  IAditoLayoutConstraints<T> createLayoutConstraints();

  /**
   * @return die Klasse der LayoutSupports des Property-Editors.
   */
  Class getLayoutClass();


}
