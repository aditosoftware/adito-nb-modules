package de.adito.aditoweb.nbm.nbide.nbaditointerface.database;

import lombok.NonNull;
import org.jetbrains.annotations.*;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author w.glanzer, 13.08.2020
 */
public class AditoConnectionManager
{

  /**
   * Returns all possible database connections, that a project can connect to.
   * They do not have to be connectable or even correct - they are just defined in project and may be valid.
   * This method returns all connections over all opened projects
   *
   * @return a list of all possible connections
   */
  @NonNull
  public static List<IPossibleConnectionProvider.IPossibleDBConnection> getPossibleConnections()
  {
    if(SwingUtilities.isEventDispatchThread())
      throw new RuntimeException("AditoConnectionManager.getPossibleConnections() must not be called in EDT!");

    return getPossibleConnections(Arrays.asList(OpenProjects.getDefault().getOpenProjects()));
  }

  /**
   * Returns all possible database connections, that a project can connect to.
   * They do not have to be connectable or even correct - they are just defined in project and may be valid
   * This method returns all connections for the given projects
   *
   * @return a list of all possible connections
   */
  @NonNull
  public static List<IPossibleConnectionProvider.IPossibleDBConnection> getPossibleConnections(@NonNull Collection<Project> pProjects)
  {
    if(SwingUtilities.isEventDispatchThread())
      throw new RuntimeException("AditoConnectionManager.getPossibleConnections() must not be called in EDT!");

    return pProjects.stream()
        .flatMap(pProj -> getPossibleConnections(pProj).stream())
        .distinct()
        .collect(Collectors.toList());
  }

  /**
   * Returns all possible database connections, that a project can connect to.
   * They do not have to be connectable or even correct - they are just defined in project and may be valid
   * This method returns all connections for the given project
   *
   * @return a list of all possible connections
   */
  @NonNull
  public static List<IPossibleConnectionProvider.IPossibleDBConnection> getPossibleConnections(@NonNull Project pProject)
  {
    if(SwingUtilities.isEventDispatchThread())
      throw new RuntimeException("AditoConnectionManager.getPossibleConnections() must not be called in EDT!");

    return pProject.getLookup().lookupAll(IPossibleConnectionProvider.class).stream()
        .flatMap(pProvider -> pProvider.getPossibleConnections().stream())
        .distinct()
        .collect(Collectors.toList());
  }

}
