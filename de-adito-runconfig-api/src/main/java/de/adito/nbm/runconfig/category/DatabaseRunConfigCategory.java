package de.adito.nbm.runconfig.category;

import de.adito.nbm.runconfig.api.IRunConfigCategory;
import io.reactivex.rxjava3.core.Observable;
import org.jetbrains.annotations.NotNull;
import org.openide.util.NbBundle;

/**
 * @author m.kaspera, 22.07.2020
 */
public class DatabaseRunConfigCategory implements IRunConfigCategory
{

  @NotNull
  @Override
  public String getName()
  {
    return "de-adito-aditoweb-nbm-editor-system-runconfig-Database-Category"; //NOI18N
  }

  @NotNull
  @Override
  public Observable<String> title()
  {
    return Observable.just(NbBundle.getMessage(DatabaseRunConfigCategory.class, "TITLE_DB_Category")); //NOI18N
  }

}
