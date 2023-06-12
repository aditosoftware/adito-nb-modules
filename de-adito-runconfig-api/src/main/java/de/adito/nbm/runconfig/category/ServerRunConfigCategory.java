package de.adito.nbm.runconfig.category;

import de.adito.nbm.runconfig.api.IRunConfigCategory;
import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
import org.openide.util.NbBundle;

/**
 * @author m.kaspera, 22.07.2020
 */
public class ServerRunConfigCategory implements IRunConfigCategory
{

  @NonNull
  @Override
  public String getName()
  {
    return "de-adito-aditoweb-nbm-editor-system-runconfig-ServerRunConfig-Category"; //NOI18N
  }

  @NonNull
  @Override
  public Observable<String> title()
  {
    return Observable.just(NbBundle.getMessage(ServerRunConfigCategory.class, "TITLE_Server_Category")); //NOI18N
  }

}
