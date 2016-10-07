package application.samoht.br.cardfit.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 05/08/16.
 */
public class LoginPresenter {

    private ILoginView loginView;

    public LoginPresenter(ILoginView loginView){
        this.loginView = loginView;
        initSetupFirebase();
    }

    public void doLogin(String email, String password) {
        loginView.startLoading();
        FirebaseHelper.doLoginUser(loginView.getActivity(), this, email, password);
    }

    public void doSignUp(String email){
        loginView.callSignUpActivity(email);
    }

    public void finishProcessLogin(){
        loginView.finishLoading();
        loginView.callMainActivity();
    }

    public void initSetupFirebase() {
        FirebaseHelper.autoLogin(this);
    }

    public void setOnStart(){
        FirebaseHelper.addListenerFirebaseAuth();
    }

    public void setOnStop() {
        FirebaseHelper.removeAuthStateListener();
    }

    public void setError(String message){
        loginView.finishLoading();
        loginView.showError(message);
    }

    public void authWithGoogle(GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        FirebaseHelper.authWithGoogle(loginView.getActivity(), this, account);
    }
}
