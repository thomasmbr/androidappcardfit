package application.samoht.br.cardfit.ui.main.menu.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.samoht.br.cardfit.base.BaseFragment;
import application.samoht.br.cardfit.R;

/**
 * Created by Thomas on 09/04/16.
 */
public class SettingsFragment extends BaseFragment implements ISettingsView {

    private SettingsPresenter settingsPresenter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.settings,container,false);
        settingsPresenter = new SettingsPresenter(this);
        return rootView;
    }

}
