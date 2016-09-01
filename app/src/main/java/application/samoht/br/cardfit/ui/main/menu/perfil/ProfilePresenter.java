package application.samoht.br.cardfit.ui.main.menu.perfil;

import application.samoht.br.cardfit.service.FirebaseController;

/**
 * Created by Thomas on 17/08/16.
 */
public class ProfilePresenter {

    private IProfileView profileView;
    private FirebaseController firebaseController;

    public ProfilePresenter(IProfileView profile) {
        this.profileView = profile;
        firebaseController  = new FirebaseController();
        showInfoProfile();
    }

    public void setError() {
        profileView.showError();
    }

    private void showInfoProfile() {
        firebaseController.retrieveInfoUser(this, profileView.getFragment().getContext(), profileView.getListView());
    }
}
