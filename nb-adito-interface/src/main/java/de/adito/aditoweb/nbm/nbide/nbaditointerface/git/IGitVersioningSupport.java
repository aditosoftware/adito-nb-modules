package de.adito.aditoweb.nbm.nbide.nbaditointerface.git;

import de.adito.aditoweb.nbm.nbide.nbaditointerface.git.exceptions.AditoVersioningException;
import org.jetbrains.annotations.*;

import java.io.File;
import java.util.*;

/**
 * Versioning Support for GIT-Integrations
 *
 * @author w.glanzer, 20.03.2019
 */
public interface IGitVersioningSupport
{

  /**
   * Performs a clone of a specific repository
   *
   * @param pRemoteURI Remote-Repository-URL (GitURI.toPrivateString)
   * @param pTarget    Target-Folder
   * @param pOptions   Options for the versioning support
   * @return <tt>true</tt> if successful
   */
  boolean performClone(@NotNull String pRemoteURI, @NotNull File pTarget, @Nullable Map<String, String> pOptions)
      throws Exception;

  /**
   * Fetches a list of all available branches of a given repository
   *
   * @param pRemoteUrl The URL of the remote repository, from which to fetch the list of available branches
   * @return a list of the available branches
   */
  @NotNull
  List<IRemoteBranch> getBranchesInRepository(@NotNull String pRemoteUrl) throws AditoVersioningException;

}
