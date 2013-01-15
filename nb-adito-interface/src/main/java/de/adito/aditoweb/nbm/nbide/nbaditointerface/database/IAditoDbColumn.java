package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;


/**
 * Informationen über eine Spalte einer Datenbank.
 *
 * @author J. Boesl, 14.01.13
 */
public interface IAditoDbColumn
{

  /**
   * @return Spaltenname.
   */
  String getName();

  /**
   * @return ob der Wert <tt>null</tt> sein kann.
   */
  boolean isNullable();

  /**
   * @return ob die Spalte ein primary key ist.
   */
  boolean isPrimaryKey();

  /**
   * @return scale
   */
  int getScale();

  /**
   * @return size
   */
  int getSize();

  /**
   * @return eine Konstante aus java.sql.Types.
   */
  int getType();

  /**
   * @return ist der Wert unique.
   */
  boolean isUnique();

  /**
   * @return hat die Spalte einen Index.
   */
  boolean isIndex();

  /**
   * @return eine Funktion für die Spalte.
   */
  String getDefVal();

}
