package de.adito.aditoweb.nbm.nbide.nbaditointerface.transpiler;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author p.neub, 27.04.2023
 */
public interface ILinkedFileCompiler
{
  /**
   * Name des Compilers
   *
   * @return Name des Compilers
   */
  @NotNull
  String getName();

  /**
   * Liefert die Dateiendung vor dem Compile zurück
   *
   * @return die Dateiendung vor dem Compile
   */
  @NotNull
  String getSourceExtension();

  /**
   * Liefert die Dateiendung der Outputdateien des Compilers
   *
   * @return Dateiendung der Outputdateien des Compiler
   */
  @NotNull
  String getTargetExtension();

  /**
   * Kompiliert alles in pFiles angegebenen Dateien.
   * Der Kompiliervorgang wird separat für jedes Projekt ausgeführt, damit etwaige Konfigurationsdateien verwendet werden können
   *
   * @param pRoot  Wurzelverzeicznis dees Projektes
   * @param pFiles Dateien die kompiliert werden müssen (z.B. weil sie geändert wurden)
   * @return Future die resolved wenn der Kompiliervorgang abgeschlossen ist
   */
  @NotNull
  CompletableFuture<Void> compileFiles(@NotNull Path pRoot, @NotNull List<Path> pFiles);
}
