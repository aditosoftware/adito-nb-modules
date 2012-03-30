package de.adito.aditoweb.nbm.nbide.nbaditointerface.form.model;

import org.openide.nodes.Node;
import org.openide.util.Lookup;

/**
 * @author J. Boesl, 30.03.12
 */
public interface ICookieLookupHelper
{

  public Lookup getLookup();

  public <T extends Node.Cookie> T getCookie(Class<T> type);

}
