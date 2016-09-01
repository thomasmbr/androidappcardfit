package application.samoht.br.cardfit.ui.login;

import application.samoht.br.cardfit.service.FirebaseController;

/**
 * Created by Thomas on 05/08/16.
 */
public class LoginPresenter {

    private FirebaseController firebaseController;
    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
        this.firebaseController = new FirebaseController(this);
        initSetupFirebase();
    }

    public void doLogin(String email, String password) {
        loginView.startLoading();
        firebaseController.doLoginUser(loginView.getActivity(), email, password);
    }

    public void doSignUp(String email){
        loginView.callSignUpActivity(email);
    }

    public void finishProcessLogin(){
        loginView.finishLoading();
        loginView.callMainActivity();
    }

    public void initSetupFirebase() {
        firebaseController.autoLogin();
    }

    public void setOnStart(){
        firebaseController.addListenerFirebaseAuth();
    }

    public void setOnStop() {
        firebaseController.removeAuthStateListener();
    }

    public void setError(String message){
        loginView.finishLoading();
        loginView.showError(message);
    }

}
