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
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.form;

import java.awt.Color;
import java.util.prefs.Preferences;
import org.openide.util.HelpCtx;
import org.openide.util.NbPreferences;

/**
 * Settings for the form editor.
 */
public class FormLoaderSettings implements HelpCtx.Provider   {
    private static final FormLoaderSettings INSTANCE = new FormLoaderSettings();
    public static final String PROP_USE_INDENT_ENGINE = "useIndentEngine"; // NOI18N
    /** Property name of the event listener code generation style option. */
    public static final String PROP_LISTENER_GENERATION_STYLE = "listenerGenerationStyle"; // NOI18N
    /** Property name of the selectionBorderSize property */
    public static final String PROP_SELECTION_BORDER_SIZE = "selectionBorderSize"; // NOI18N
    /** Property name of the selectionBorderColor property */
    public static final String PROP_SELECTION_BORDER_COLOR = "selectionBorderColor"; // NOI18N
    /** Property name of the connectionBorderColor property */
    public static final String PROP_CONNECTION_BORDER_COLOR = "connectionBorderColor"; // NOI18N
    /** Property name of the dragBorderColor property */
    private static final String PROP_DRAG_BORDER_COLOR = "dragBorderColor"; // NOI18N
    /** Property name of the guidingLineColor property */
    private static final String PROP_GUIDING_LINE_COLOR = "guidingLineColor"; // NOI18N
    /** Property name of the formDesignerBackgroundColor property */
    public static final String PROP_FORMDESIGNER_BACKGROUND_COLOR = "formDesignerBackgroundColor"; // NOI18N
    /** Property name of the formDesignerBorderColor property */
    public static final String PROP_FORMDESIGNER_BORDER_COLOR = "formDesignerBorderColor"; // NOI18N
    /** Property name of the gridX property */
    private static final String PROP_GRID_X = "gridX"; // NOI18N
    /** Property name of the gridY property */
    private static final String PROP_GRID_Y = "gridY"; // NOI18N
    /** Property name of the applyGridToPosition property */
    private static final String PROP_APPLY_GRID_TO_POSITION = "applyGridToPosition"; // NOI18N
    /** Property name of the applyGridToSize property */
    private static final String PROP_APPLY_GRID_TO_SIZE = "applyGridToSize"; // NOI18N
    /** Property name of the variablesModifier property */
    public static final String PROP_VARIABLES_MODIFIER = "variablesModifier"; // NOI18N
    /** Property name of the variablesLocal property */
    public static final String PROP_VARIABLES_LOCAL = "variablesLocal"; // NOI18N

    /** Property name of the autoSetComponentName property */
    public static final String PROP_AUTO_SET_COMPONENT_NAME = "autoSetComponentName"; // NOI18N
    private static final int AUTO_NAMING_DEFAULT = 0;
    static final int AUTO_NAMING_ON = 1;

  /** Property name of the generateMnemonicsCode property */
    public static final String PROP_GENERATE_MNEMONICS = "generateMnemonicsCode"; // NOI18N
  /** Property name of the editorSearchPath property */
    private static final String PROP_EDITOR_SEARCH_PATH = "editorSearchPath"; // NOI18N
    /** Property name of the toolBarPalette property */
    public static final String PROP_PALETTE_IN_TOOLBAR = "toolBarPalette"; // NOI18N
  /** Property name of the assistantShown property. */
    public static final String PROP_ASSISTANT_SHOWN = "assistantShown"; // NOI18N
  /** Property name of the generate FQN property. */
    public static final String PROP_GENERATE_FQN = "generateFQN"; // NOI18N

  /** Array of package names to search for property editors used in Form Editor */
    private static String[] editorSearchPath = null;

  public static Preferences getPreferences() {
        return NbPreferences.forModule(FormLoaderSettings.class);
    }
    
    public static FormLoaderSettings getInstance() {
        return INSTANCE;
    }

    // property access methods

  /**
     * Getter for the event listener code generation style option.
     *
     * @return listener generation style.
     */
    public int getListenerGenerationStyle() {
        return getPreferences().getInt(PROP_LISTENER_GENERATION_STYLE, 0);
    }

  /**
     * Getter for the selectionBorderSize option.
     * 
     * @return selection border size.
     */
    public int getSelectionBorderSize() {
        return getPreferences().getInt(PROP_SELECTION_BORDER_SIZE, 1);
    }

  /**
     * Getter for the selectionBorderColor option.
     * 
     * @return color of selection border.
     */
    public java.awt.Color getSelectionBorderColor() {
        int rgb = getPreferences().getInt(PROP_SELECTION_BORDER_COLOR, new Color(255, 164, 0).getRGB());                
        return new Color(rgb);
    }

  /**
     * Getter for the connectionBorderColor option.
     * 
     * @return color of connection border.
     */
    public java.awt.Color getConnectionBorderColor() {
        int rgb = getPreferences().getInt(PROP_CONNECTION_BORDER_COLOR, Color.red.getRGB());
        return new Color(rgb);
    }

  /**
     * Getter for the dragBorderColor option.
     * 
     * @return color of drag border.
     */
    public java.awt.Color getDragBorderColor() {
        int rgb = getPreferences().getInt(PROP_DRAG_BORDER_COLOR, Color.gray.getRGB());
        return new Color(rgb);        
    }

  /**
     * Getter for the guidingLineColor option.
     * 
     * @return color of guiding lines.
     */
    public java.awt.Color getGuidingLineColor() {
        int rgb = getPreferences().getInt(PROP_GUIDING_LINE_COLOR, new Color(143, 171, 196).getRGB());
        return new Color(rgb);        
        
    }

  /**
     * Getter for the gridX option.
     * 
     * @return size of horizontal grid.
     */
    public int getGridX() {
        return getPreferences().getInt(PROP_GRID_X, 10);
    }

  /**
     * Getter for the gridY option.
     * 
     * @return size of vertical grid.
     */
    public int getGridY() {        
        return getPreferences().getInt(PROP_GRID_Y, 10);
    }

  /**
     * Getter for the applyGridToPosition option.
     * 
     * @return determines whether position of component should snap to grid.
     */
    public boolean getApplyGridToPosition() {
        return getPreferences().getBoolean(PROP_APPLY_GRID_TO_POSITION, true);
    }

  /**
     * Getter for the applyGridToSize option.
     * 
     * @return determines whether size of component should snap to grid.
     */
    public boolean getApplyGridToSize() {
        return getPreferences().getBoolean(PROP_APPLY_GRID_TO_SIZE, true);
        
    }

  /**
     * Getter for the variablesLocal option.
     * 
     * @return determines whether variables should be local.
     */
    public boolean getVariablesLocal() {
        return getPreferences().getBoolean(PROP_VARIABLES_LOCAL, false);
    }

  /**
     * Getter for the variablesModifier option.
     * 
     * @return variables modifier.
     */
    public int getVariablesModifier() {
        return getPreferences().getInt(PROP_VARIABLES_MODIFIER, java.lang.reflect.Modifier.PRIVATE);
    }

  public int getAutoSetComponentName() {
        return getPreferences().getInt(PROP_AUTO_SET_COMPONENT_NAME, AUTO_NAMING_DEFAULT);
    }

  /**
     * Getter for the generateMnemonicsCode option.
     * 
     * @return determines whether to generate <code>Mnemonics</code> code.
     */
    public boolean getGenerateMnemonicsCode() {
        return getPreferences().getBoolean(PROP_GENERATE_MNEMONICS, false);
    }

  private static final boolean USE_STORED_SEARCH_PATH = Boolean.getBoolean("nb.form.useStoredSearchPath"); // Issue 163705
    private static final String DEFAULT_EDITOR_SEARCH_PATH = "org.netbeans.modules.form.editors2 , org.netbeans.modules.swingapp"; // NOI18N
    
    /**
     * Getter for the editorSearchPath option.
     *
     * @return property editor search path.
     */
    public String[] getEditorSearchPath() {
        if (editorSearchPath == null) {
            if (USE_STORED_SEARCH_PATH) {
                editorSearchPath = translatedEditorSearchPath(
                    toArray(getPreferences().get(PROP_EDITOR_SEARCH_PATH, DEFAULT_EDITOR_SEARCH_PATH)));
            } else {
                editorSearchPath = toArray(DEFAULT_EDITOR_SEARCH_PATH);
            }
        }
        return editorSearchPath;
    }

  public boolean isPaletteInToolBar() {
        return getPreferences().getBoolean(PROP_PALETTE_IN_TOOLBAR, false);
    }

    public void setPaletteInToolBar(boolean value) {
        getPreferences().putBoolean(PROP_PALETTE_IN_TOOLBAR, value);    
    }

    /**
     * Getter for the formDesignerBackgroundColor option.
     * 
     * @return background color of the designer.
     */
    public java.awt.Color getFormDesignerBackgroundColor() {
        int rgb = getPreferences().getInt(PROP_FORMDESIGNER_BACKGROUND_COLOR , Color.white.getRGB());
        return new Color(rgb);        
        
    }

  /**
     * Getter for the formDesignerBorderColor option.
     * 
     * @return color of the border of the designer.
     */
    public java.awt.Color getFormDesignerBorderColor() {
        int rgb = getPreferences().getInt(PROP_FORMDESIGNER_BORDER_COLOR , new Color(224, 224, 255).getRGB());
        return new Color(rgb);        
        
    }

  /**
     * Getter for the assistantShown option.
     * 
     * @return <code>true</code> if the assistant should be shown,
     * return <code>false</code> otherwise.
     */
    public boolean getAssistantShown() {
        return getPreferences().getBoolean(PROP_ASSISTANT_SHOWN, true);    
    }

    /**
     * Setter for the foldGeneratedCode option.
     * 
     * @param value determines whether the assistant should be shown.
     */
    public void setAssistantShown(boolean value) {
        getPreferences().putBoolean(PROP_ASSISTANT_SHOWN, value);    
    }

  public boolean getGenerateFQN() {
        return getPreferences().getBoolean(PROP_GENERATE_FQN, true);    
    }

  private static String[] toArray(String esp) {
        return esp.split(" , ");//NOI18N
    }

  // XXX(-tdt) Hmm, backward compatibility with com.netbeans package name
    // again. The property editor search path is stored in user settings, we
    // must translate    
    private  static String[] translatedEditorSearchPath(String[] eSearchPath) {
        String[] retval = new String[eSearchPath.length];
        for (int i = 0; i < eSearchPath.length; i++) {
            String path = eSearchPath[i];
            path = org.openide.util.Utilities.translate(path + ".BogusClass"); // NOI18N
            path = path.substring(0, path.length() - ".BogusClass".length()); // NOI18N
            retval[i] = path;
        }        
        return retval;
    }

  @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("gui.configuring"); // NOI18N
    } 
}
