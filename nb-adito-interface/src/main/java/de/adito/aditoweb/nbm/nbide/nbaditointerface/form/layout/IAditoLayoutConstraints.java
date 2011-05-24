package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout;

import org.openide.nodes.Node;

import java.util.Collection;

/**
 * Layoutconstraints f�r den Formeditor. Liefert die Verf�gbaren Properties ({@link #getProperties}) und bietet einen
 * Weg zum duplizieren des Objekts ({@link #cloneConstraints})
 *
 * @author J. Boesl, 20.05.11
 */
public interface IAditoLayoutConstraints<TypeInfo>
{

  /**
   * Liefert alle Properties, die es f�r diese Constraints gibt.
   *
   * @return Properties, alle.
   */
  Collection<Node.Property> getProperties();

  <T> Node.Property<T> get(IAditoLayoutPropertyType<T> pType);

  /**
   * Liefert einen Wert eines Properties.
   *
   * @param pType beschreibt das Property.
   * @param <T>   von diesem Typ ist der Wert, der zur�ck geliefert wird.
   * @return das Property.
   */
  <T> T getValue(IAditoLayoutPropertyType<T> pType);

  /**
   * Setzt einen Wert der Properties.
   *
   * @param pType  eine Beschreibung des Properties.
   * @param pValue der Wert.
   * @param <T>    von diesem Typ ist der Wert.
   */
  <T> void setValue(IAditoLayoutPropertyType<T> pType, T pValue);

  /**
   * Soll alle verwendbaren 'IAditoLayoutPropertyType'-Objekte verf�gbar machen.
   *
   * @return ein spezielles TypeInfo f�r diese Constraints.
   */
  TypeInfo getTypeInfo();

  /**
   * Clont die Constraints. Alle Properties werden auch geklont.
   *
   * @return neue Constraints-Instanz vom gleichen Typ und mit den gleichen Properties.
   */
  IAditoLayoutConstraints<TypeInfo> cloneConstraints();

}
