package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.layout.common;

import org.openide.nodes.Node;

import java.util.Collection;

/**
 * LayoutConstraints für den FormEditor. Liefert die Verfügbaren Properties ({@link #getProperties}) und bietet einen
 * Weg zum duplizieren des Objekts ({@link #cloneConstraints})
 *
 * @author J. Boesl, 20.05.11
 */
public interface IAditoLayoutConstraints<TypeInfo>
{

  /**
   * Liefert alle Properties, die es für diese Constraints gibt.
   *
   * @return Properties, alle.
   */
  Collection<Node.Property> getProperties();

  /**
   * Liefert ein Property.
   *
   * @param pType Beschreibung des Properties.
   * @return das Property wen gefunden, ansonsten <tt>null</tt>.
   */
  <T> Node.Property<T> get(IAditoLayoutPropertyType<T> pType);

  /**
   * Liefert einen Wert eines Properties.
   *
   * @param pType beschreibt das Property.
   * @param <T>   von diesem Typ ist der Wert, der zurück geliefert wird.
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
   * Soll alle verwendbaren 'IAditoLayoutPropertyType'-Objekte verfügbar machen.
   *
   * @return ein spezielles TypeInfo für diese Constraints.
   */
  TypeInfo getTypeInfo();

  /**
   * Klont die Constraints. Alle Properties werden auch geklont.
   *
   * @return neue Constraints-Instanz vom gleichen Typ und mit den gleichen Properties.
   */
  IAditoLayoutConstraints<TypeInfo> cloneConstraints();

}
