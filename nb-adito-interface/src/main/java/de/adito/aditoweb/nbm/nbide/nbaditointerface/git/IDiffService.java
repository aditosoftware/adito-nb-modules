package de.adito.aditoweb.nbm.nbide.nbaditointerface.git;

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
   * @param pVersion1 String to be compared to pVersion2
   * @param pVersion2 String to be compared to pVersion1
   */
  void showDiff(String pVersion1, String pVersion2);

  /**
   * Compares two byte Arrays (assumed to represent Strings) by converting the byte arrays to Strings (should include encoding detection), compares the Strings
   * and then displays a Dialog that shows the differences of the two Versions
   *
   * @param pVersion1 byte array to be compared to pVersion2
   * @param pVersion2 byte array to be compared to pVersion1
   */
  void showDiff(byte[] pVersion1, byte[] pVersion2);

}
