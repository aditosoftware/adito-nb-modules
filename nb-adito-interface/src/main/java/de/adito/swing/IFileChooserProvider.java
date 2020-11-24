package de.adito.swing;

import javax.swing.*;

/**
 * Provides a default fileChooser, should be used to make the workflow and characteristics of choosing a file or directory
 *
 * @author m.kaspera, 18.11.2020
 */
public interface IFileChooserProvider
{

  JFileChooser getFileChooser();

}
