package de.adito.nbm.runconfig.category;

import de.adito.nbm.runconfig.api.IRunConfigCategory;
import io.reactivex.rxjava3.core.Observable;
import lombok.NonNull;
import org.openide.util.NbBundle;

/**
 * @author m.kaspera, 22.07.2020
 */
public class AditoClientRunConfigCategory implements IRunConfigCategory
{

  @NonNull
  @Override
  public String getName()
  {
    return "de-adito-aditoweb-nbm-editor-system-runconfig-AditoClientRunConfigCategory"; //NOI18N
  }

  @NonNull
  @Override
  public Observable<String> title()
  {
    return Observable.just(NbBundle.getMessage(AditoClientRunConfigCategory.class, "TITLE_Client_Category")); //NOI18N
  }

}
