package application.samoht.br.cardfit.ui.main.menu.perfil;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import application.samoht.br.cardfit.base.BaseFragment;
import application.samoht.br.cardfit.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thomas on 09/04/16.
 */
public class ProfileFragment extends BaseFragment implements IProfileView{

    @BindView(R.id.listview_perfil) protected ListView listView;
    private View rootView;
    private ProfilePresenter profilePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.perfil,container,false);
        ButterKnife.bind(ProfileFragment.this, rootView);
        profilePresenter = new ProfilePresenter(this);
        return rootView;
    }

    @Override
    public ListView getListView(){
        return this.listView;
    }

    @Override
    public Fragment getFragment(){
        return this;
    }

    @Override
    public void showError(){
        showSimpleAlertDialog(getContext(),getString(R.string.error_retrieve_profile));
    }
}
