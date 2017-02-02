/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * Portions Copyrighted 2007 Sun Microsystems, Inc.
 */
package org.netbeans.modules.db.explorer.dlg;

import java.util.*;

import org.netbeans.lib.ddl.impl.*;

/**
 * DDL for creating an index, refactored out of the AddIndex dialog
 *
 * @author <a href="mailto:david@vancouvering.com">David Van Couvering</a>
 */
public class AddIndexDDL
{
  public static final String UNIQUE_STRING = "UNIQUE";
  public static final String INDEX_STRING = "_IDX";

    private Specification       spec;
    private String              schema;
    private String              tablename;

    public AddIndexDDL (
            Specification spec, 
            String schema,
            String tablename) {
        this.spec       = spec;
        this.schema     = schema;
        this.tablename  = tablename;
    }
    
    /**
     * Execute the DDL to create an index.  
     * 
     * @param indexName the name of the index
     * @param isUnique set to true if a unique index
     * @param columns - A Vector of ColumnItem representing the columns
     *      in the index
     */
    public boolean execute(String indexName, 
            boolean isUnique, Set columns) throws Exception {
        CreateIndex icmd = spec.createCommandCreateIndex(tablename);
        
        icmd.setObjectOwner(schema);
        icmd.setIndexName(indexName);
        icmd.setIndexType(isUnique ? ColumnItem.UNIQUE : "");
        
        Iterator enu = columns.iterator();
        while (enu.hasNext()) {
            icmd.specifyColumn((String)enu.next());
        }
        
        icmd.execute();

        return icmd.wasException();
    }

  public CreateIndex getDDL(String pColumnName, boolean pIsUnique) throws Exception
  {
    CreateIndex index = spec.createCommandCreateIndex(tablename);
    index.setIndexName(_getIndexName(tablename, pColumnName));
    if(pIsUnique)
      index.setIndexType(UNIQUE_STRING);
    else
      index.setIndexType("");
    index.specifyColumn(pColumnName);
    index.setObjectOwner(schema);

    return index;
  }

  private String _getIndexName(String pTablename, String pColumnName)
  {
    int maxNameLength = Integer.MAX_VALUE;
    try
    {
      maxNameLength = (Integer) spec.getProperties().get("MaxObjectNameLength");
    }
    catch (Exception e)
    {
      // nix tun. Wenn keine Maxl�nge gesetzt, wird auch keine n�tig sein.
    }

    String table = pTablename;
    String column = pColumnName;

    String indexName = table + "_" + column + INDEX_STRING;
    while(indexName.length() > maxNameLength)
    {
      table = table.substring(0, table.length()-1);
      column = column.substring(0, column.length()-1);
      indexName = table + "_" + column + INDEX_STRING;
    }
    return indexName;
  }
}
