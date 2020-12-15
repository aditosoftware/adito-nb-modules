package de.adito.nbm.blueprints.api;

import de.adito.picoservice.IPicoRegistry;
import org.jetbrains.annotations.*;
import org.openide.filesystems.FileObject;

import java.io.InputStream;
import java.util.*;

/**
 * Contains a single document, that a blueprint can create.
 * Those are not compiled and contain placeholders for parameter values.
 *
 * @author w.glanzer, 06.07.2020
 */
public interface IBlueprintPreset
{
  /**
   * Contains all available BlueprintProviders
   */
  Map<EContentType, List<IBlueprintPreset>> INSTANCES = load();

  /**
   * @return the data
   */
  @NotNull
  String getContent();

  /**
   * @param pContent the data
   */
  void setContent(@NotNull String pContent);

  /**
   * @param pPath the path, where the file created by the blueprint should be stored
   */
  void setPath(@Nullable String pPath);

  /**
   * Saves the compiled Blueprint into file/s.
   *
   * @param pParent       the parent of the new file, must be a directory. Is only used, if the path {@link #setPath(String)} is null.
   * @param pBlueprint    the blueprint
   * @param pCompiledData the compiled data, which should be stored
   * @param pName         the name of the new file/model
   * @see #setPath(String)
   */
  void store(@NotNull FileObject pParent, @NotNull IBlueprint pBlueprint, @NotNull InputStream pCompiledData, @NotNull String pName) throws Exception;

  /**
   * @return the type of the IBlueprintPreset
   */
  default EContentType type()
  {
    return getClass().getAnnotation(BlueprintPresets.class).type();
  }

  /**
   * Loads all available IBlueprintPresets.
   */
  static Map<EContentType, List<IBlueprintPreset>> load()
  {
    Map<EContentType, List<IBlueprintPreset>> result = new HashMap<>();
    IPicoRegistry.INSTANCE.find(IBlueprintPreset.class, BlueprintPresets.class).forEach((key, value) -> {
      try
      {
        EContentType type = value.type();
        List<IBlueprintPreset> presets = result.get(type);
        IBlueprintPreset instance = key.getDeclaredConstructor().newInstance();

        if (presets == null)
          presets = new ArrayList<>();
        presets.add(instance);
        result.put(type, presets);

      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
    });
    result.values().forEach(pPresets -> pPresets.sort(Comparator.comparing(pPreset -> pPreset.getClass().getAnnotation(BlueprintPresets.class).position())));
    return result;
  }

  /**
   * Returns the corresponding BlueprintPreset
   *
   * @param pType type of the required preset
   * @return a new instantiated preset
   */
  @Nullable
  static IBlueprintPreset get(@NotNull EContentType pType)
  {
    List<IBlueprintPreset> presets = INSTANCES.get(pType);
    if (presets != null && !presets.isEmpty())
    {
      try
      {
        // clone it, because it can be used multiple times
        IBlueprintPreset preset = presets.get(0);
        return preset.getClass().getDeclaredConstructor().newInstance();
      }
      catch (Exception e)
      {
        throw new RuntimeException(e);
      }
    }
    return null;
  }
}
