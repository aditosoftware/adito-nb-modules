package de.adito.aditoweb.nbm.nbide.nbaditointerface.git;

import java.io.File;
import java.util.List;

/**
 * Represents a possible action that can be undertaken before a commit is performed
 *
 * @author m.kaspera, 16.05.2022
 */
public interface IBeforeCommitAction
{

  /**
   * Perform an action, for example checking the formatting of the files to commit.
   *
   * @param pFilesToCommit List with all files selected for the commit
   * @return true if the commit should be performed, false if the commit should be cancelled
   */
  boolean performAction(List<File> pFilesToCommit);

  /**
   * @return the name that this action should be displayed as
   */
  String getName();


  /**
   * @return tooltip that should be shown when hovering over the visual representation of this action
   */
  String getTooltip();

}
