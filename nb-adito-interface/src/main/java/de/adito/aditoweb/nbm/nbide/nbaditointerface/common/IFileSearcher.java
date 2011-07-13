package de.adito.aditoweb.nbm.nbide.nbaditointerface.common;

import org.openide.filesystems.FileObject;

import java.util.*;

/**
 * @author J. Boesl, 13.07.11
 */
public interface IFileSearcher
{

  Set<FileObject> getMatchingFiles(Collection<FileObject> pSearchIn, FileMatcher pMatcher);

  interface FileMatcher
  {
    boolean fits(FileObject pFo);
  }

}
