package de.adito.aditoweb.nbm.nbide.nbaditointerface.git;

/**
 * Represents a Reference in a Git Repository, such as a tag or a branch
 *
 * @author m.kaspera, 03.11.2021
 */
public interface IRef
{

  /**
   *
   * @return the name of the reference
   */
  String getName();

  /**
   *
   * @return the tree object/commit this reference points to
   */
  String getId();

}
