/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.spi.editor.completion.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import org.netbeans.modules.editor.completion.PatchedHtmlRenderer;

/**
 * Various code completion utilities including completion item
 * contents rendering.
 *
 * @author Miloslav Metelka
 * @version 1.00
 */

public final class CompletionUtilities {

    /**
     * The gap between left edge and icon.
     */
    private static final int BEFORE_ICON_GAP = 1;
    
    /**
     * The gap between icon and the left text.
     */
    private static final int AFTER_ICON_GAP = 4;
    
    /**
     * By default 16x16 icons should be used.
     */
    private static final int ICON_HEIGHT = 16;
    private static final int ICON_WIDTH = 16;
    
    /**
     * The gap between left and right text.
     */
    private static final int BEFORE_RIGHT_TEXT_GAP = 5;

    /**
     * The gap between right text and right edge.
     */
    private static final int AFTER_RIGHT_TEXT_GAP = 3;
    
    private CompletionUtilities() {
        // no instances
    }

    /**
     * Get preferred width of the item by knowing its left and right html texts.
     * <br/>
     * It is supposed that the item will have an icon 16x16 and an appropriate
     * space is reserved for it.
     *
     * @param leftHtmlText html text displayed on the left side of the item
     *  next to the icon. It may be null which means no left text will be displayed.
     * @param rightHtmlText html text aligned on the right edge of the item's
     *  rendering area. It may be null which means no right text will be displayed.
     * @return &gt;=0 preferred rendering width of the item.
     */
    public static int getPreferredWidth(String leftHtmlText, String rightHtmlText,
    Graphics g, Font defaultFont) {
        int width = BEFORE_ICON_GAP + ICON_WIDTH + AFTER_ICON_GAP + AFTER_RIGHT_TEXT_GAP;
        if (leftHtmlText != null && leftHtmlText.length() > 0) {
            width += (int)PatchedHtmlRenderer.renderHTML(leftHtmlText, g, 0, 0, Integer.MAX_VALUE, 0,
                    defaultFont, Color.black, PatchedHtmlRenderer.STYLE_CLIP, false, true);
        }
        if (rightHtmlText != null && rightHtmlText.length() > 0) {
            if (leftHtmlText != null) {
                width += BEFORE_RIGHT_TEXT_GAP;
            }
            width += (int)PatchedHtmlRenderer.renderHTML(rightHtmlText, g, 0, 0, Integer.MAX_VALUE, 0,
                    defaultFont, Color.black, PatchedHtmlRenderer.STYLE_CLIP, false, true);
        }
        return width;
    }
    
    /**
     * Render a completion item using the provided icon and left and right
     * html texts.
     *
     * @param icon icon 16x16 that will be displayed on the left. It may be null
     *  which means that no icon will be displayed but the space for the icon
     *  will still be reserved (to properly align with other items
     *  that will provide an icon).
     * 
     * @param leftHtmlText html text that will be displayed on the left side
     *  of the item's rendering area next to the icon.
     *  <br/>
     *  It may be null which indicates that no left text will be displayed.
     *  <br/>
     *  If there's not enough horizontal space in the rendering area
     *  the text will be shrinked and "..." will be displayed at the end.
     *
     * @param rightHtmlText html text that will be aligned to the right edge
     *  of the item's rendering area.
     *  <br/>
     *  It may be null which means that no right text will be displayed.
     *  <br/>
     *  The right text is always attempted to be fully displayed unlike
     *  the left text that may be shrinked if there's not enough rendering space
     *  in the horizontal direction.
     *  <br/>
     *  If there's not enough space even for the right text it will be shrinked
     *  and "..." will be displayed at the end of the rendered string.
     * @param g non-null graphics through which the rendering happens.
     * @param defaultFont non-null default font to be used for rendering.
     * @param defaultColor non-null default color to be used for rendering.
     * @param width &gt;=0 available width for rendering.
     * @param height &gt;=0 available height for rendering.
     * @param selected whether the item being rendered is currently selected
     *  in the completion's JList. If selected the foreground color is forced
     *  to be black for all parts of the rendered strings.
     */
    public static void renderHtml(ImageIcon icon, String leftHtmlText, String rightHtmlText,
    Graphics g, Font defaultFont, Color defaultColor,
    int width, int height, boolean selected) {
        if (icon != null) {
            // The image of the ImageIcon should already be loaded
            // so no ImageObserver should be necessary
            g.drawImage(icon.getImage(), BEFORE_ICON_GAP, (height - icon.getIconHeight()) /2, null);
        }
        int iconWidth = BEFORE_ICON_GAP + (icon != null ? icon.getIconWidth() : ICON_WIDTH) + AFTER_ICON_GAP;
        int rightTextX = width - AFTER_RIGHT_TEXT_GAP;
        FontMetrics fm = g.getFontMetrics(defaultFont);
        int textY = (height - fm.getHeight())/2 + fm.getHeight() - fm.getDescent();
        if (rightHtmlText != null && rightHtmlText.length() > 0) {
            int rightTextWidth = (int)PatchedHtmlRenderer.renderHTML(rightHtmlText, g, 0, 0, Integer.MAX_VALUE, 0,
                    defaultFont, defaultColor, PatchedHtmlRenderer.STYLE_CLIP, false, true);
            rightTextX = Math.max(iconWidth, rightTextX - rightTextWidth);
            // Render right text
            PatchedHtmlRenderer.renderHTML(rightHtmlText, g, rightTextX, textY, rightTextWidth, textY,
                defaultFont, defaultColor, PatchedHtmlRenderer.STYLE_CLIP, true, selected);
            rightTextX = Math.max(iconWidth, rightTextX - BEFORE_RIGHT_TEXT_GAP);
        }

        // Render left text
        if (leftHtmlText != null && leftHtmlText.length() > 0 && rightTextX > iconWidth) { // any space for left text?
            PatchedHtmlRenderer.renderHTML(leftHtmlText, g, iconWidth, textY, rightTextX - iconWidth, textY,
                defaultFont, defaultColor, PatchedHtmlRenderer.STYLE_TRUNCATE, true, selected);
        }
    }
    
}
