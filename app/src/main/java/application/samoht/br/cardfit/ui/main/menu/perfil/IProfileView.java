package application.samoht.br.cardfit.ui.main.menu.perfil;

import android.support.v4.app.Fragment;
import android.widget.ListView;

/**
 * Created by Thomas on 17/08/16.
 */
public interface IProfileView {
    ListView getListView();
    Fragment getFragment();
    void showError();
}
