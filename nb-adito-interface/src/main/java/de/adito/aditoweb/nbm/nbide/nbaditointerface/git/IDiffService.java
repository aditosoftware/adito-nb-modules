package de.adito.aditoweb.nbm.nbide.nbaditointerface.git;

import lombok.NonNull;
import org.jetbrains.annotations.*;

/**
 * Defines methods for a service that provides capabilities for showing the differences between two Strings in a dialog
 *
 * @author m.kaspera, 23.08.2019
 */
public interface IDiffService
{

  /**
   * Compares two Strings and then displays a Dialog that shows the differences of the two Versions
   *
   * @param pVersionLeft  String to be compared to pVersionRight, will be displayed on the left side of the comparison
   * @param pVersionRight String to be compared to pVersionLeft, will be displayed on the right side of the comparison
   */
  void showDiff(@NonNull String pVersionLeft, @NonNull String pVersionRight);

  /**
   * Compares two byte Arrays (assumed to represent Strings) by converting the byte arrays to Strings (should include encoding detection),
   * compares the Strings and then displays a Dialog that shows the differences of the two Versions
   *
   * @param pVersionLeft  byte array to be compared to pVersionRight, will be displayed on the left side of the comparison
   * @param pVersionRight byte array to be compared to pVersionLeft, will be displayed on the right side of the comparison
   */
  void showDiff(@NonNull byte[] pVersionLeft, @NonNull byte[] pVersionRight);

  /**
   * Compares two Strings and then displays a Dialog that shows the differences of the two Versions
   *
   * @param pVersionLeft  String to be compared to pVersionRight, will be displayed on the left side of the comparison
   * @param pVersionRight String to be compared to pVersionLeft, will be displayed on the right side of the comparison
   * @param pTitle        Will be used as title for the dialog
   * @param pHeaderRight  Used to to give an origin to the right side of the comparison (e.g. commit id, file name or the like)
   * @param pHeaderLeft   Used to to give an origin to the left side of the comparison (e.g. commit id, file name or the like)
   */
  void showDiff(@NonNull String pVersionLeft, @NonNull String pVersionRight, @NonNull String pTitle, @Nullable String pHeaderRight,
                @Nullable String pHeaderLeft);

  /**
   * Compares two byte Arrays (assumed to represent Strings) by converting the byte arrays to Strings (should include encoding detection),
   * compares the Strings and then displays a Dialog that shows the differences of the two Versions
   *
   * @param pVersionLeft  byte array to be compared to pVersionRight
   * @param pVersionRight byte array to be compared to pVersionLeft
   * @param pTitle        Will be used as title for the dialog
   * @param pHeaderRight  Used to to give an origin to the right side of the comparison (e.g. commit id, file name or the like)
   * @param pHeaderLeft   Used to to give an origin to the left side of the comparison (e.g. commit id, file name or the like)
   */
  void showDiff(@NonNull byte[] pVersionLeft, @NonNull byte[] pVersionRight, @NonNull String pTitle, @Nullable String pHeaderRight,
                @Nullable String pHeaderLeft);

}
