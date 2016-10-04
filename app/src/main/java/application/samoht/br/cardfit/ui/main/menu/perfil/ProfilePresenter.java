package application.samoht.br.cardfit.ui.main.menu.perfil;

import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 17/08/16.
 */
public class ProfilePresenter {

    private IProfileView profileView;

    public ProfilePresenter(IProfileView profile) {
        this.profileView = profile;
        showInfoProfile();
    }

    public void setError() {
        profileView.showError();
    }

    private void showInfoProfile() {
        FirebaseHelper.retrieveInfoUser(this,profileView.getFragment().getContext(), profileView.getListView());
    }
}
