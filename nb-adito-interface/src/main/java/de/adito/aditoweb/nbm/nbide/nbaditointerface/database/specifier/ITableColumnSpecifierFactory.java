package de.adito.aditoweb.nbm.nbide.nbaditointerface.database.specifier;

/**
 * @author c.stadler, 14.11.2017
 */
public interface ITableColumnSpecifierFactory
{

  ITableColumnSpecifier getTableColumnSpecifier(String pDbName);

}
