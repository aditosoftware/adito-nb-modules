package de.adito.swing;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import info.clearthought.layout.*;
import lombok.NonNull;
import org.openide.util.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Panel, das allgemein für Ausnahmen verwendet werden kann, um diese dem Benutzer zu präsentieren.<br/>
 * Beispiele sind der SystemEditor, wenn die Datenbank nicht erreicht werden kann oder die Datenbank nicht vorhanden ist.
 *
 * @author J. Boesl, 05.07.13
 */
public class NotificationPanel extends JPanel
{

  public NotificationPanel(Icon pIcon, String pText, Action... pActions)
  {
    super();

    double fill = TableLayoutConstants.FILL;
    double pref = TableLayoutConstants.PREFERRED;
    double gap = IGUIConst.GAP;

    Action[] actions = pActions == null ? new Action[0] : pActions;
    List<JButton> buttons = new ArrayList<>();
    int maxPreferredWidth = 0;

    double[] cols = {fill, gap, pref, gap, fill};
    List<Double> rows = Lists.newArrayList(fill, gap, pref, gap, pref, gap);
    for (Action action : actions)
    {
      JButton button = new JButton(action);
      buttons.add(button);
      // alle Buttons sollen gleich breit sein.
      maxPreferredWidth = Math.max(button.getPreferredSize().width, maxPreferredWidth);
      // 2 Zeilen hinzugefügt. Darauf wird unten aufgebaut.
      rows.add(pref);
      rows.add(gap);
    }
    rows.add(fill);

    setLayout(new TableLayout(cols, Doubles.toArray(rows)));
    TableLayoutUtil tlu = new TableLayoutUtil(this);

    tlu.add(2, 2, TLUConstants.CENTER_CENTER, new JLabel(pIcon));
    tlu.add(2, 4, TLUConstants.CENTER_CENTER, new JLabel(MultilineLabelUtil.getCenteredHtmlText(pText)));
    for (int i = 0; i < buttons.size(); i++)
    {
      JButton button = buttons.get(i);
      if (i == 0)
        button.putClientProperty(IGUIConst.CLIENT_PROPERTY_PREFERRED_FOCUS, true); // der erste Button soll den Focus bekommen.
      Dimension d = button.getPreferredSize();
      d.width = maxPreferredWidth;
      button.setPreferredSize(d);
      tlu.add(2, 6 + i * 2, TLUConstants.CENTER_CENTER, button); // jeweils alle 2 Zeilen eine Komponente.
    }
  }


  public enum NotificationType
  {
    ERROR(ImageUtilities.loadImage("de/adito/swing/error.png")),
    DB_ERROR(ImageUtilities.loadImage("de/adito/swing/data_forbidden.png")),
    WARNING(ImageUtilities.loadImage("de/adito/swing/sign_warning.png"));

    private final Image image;

    NotificationType(Image pImage)
    {
      image = pImage;
    }

    @NonNull
    public Icon toIcon()
    {
      return ImageUtilities.image2Icon(image);
    }
  }

}

