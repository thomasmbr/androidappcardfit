package application.samoht.br.cardfit.ui.signup;
import android.text.TextUtils;

import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 08/08/16.
 */
public class SignUpPresenter{

    private ISignupView signupView;

    public SignUpPresenter(ISignupView s){
        this.signupView = s;
        initSetupFirebase();
    }

    public void initSetupFirebase() {
        FirebaseHelper.autoLogin(this);
    }

    public void changedSwitch(boolean isChecked) {
        if (isChecked) {
            signupView.showAlert();
        }
    }

    public void finishProcessLogin() {
        signupView.finishLoading();
        signupView.getActivity().onBackPressed();
        signupView.showInfo();

    }

    public void setError(String message) {
        signupView.finishLoading();
        signupView.showError(message);
    }

    public void setOnStart() {
        FirebaseHelper.addListenerFirebaseAuth();
    }

    public void setOnStop() {
        FirebaseHelper.removeAuthStateListener();
    }

    public void doSignUp(String[] userData){
        if (validateForm(userData)){
            signupView.startLoading();
            FirebaseHelper.doSignUpUser(signupView.getActivity(),this,userData);
        }
    }

    private boolean validateForm(String[] userForm) {
        boolean valid = true;
        if (TextUtils.isEmpty(userForm[1])) {//email
            signupView.setErrorEmail("Obrigatório");
            valid = false;
        } else {
            signupView.setErrorEmail(null);
        }
        if ((TextUtils.isEmpty(userForm[2])) || (userForm[2].length() < 6)) {//password
            signupView.setErrorPassword("Mínimo de 6 caracteres.");
            valid = false;
        } else {
            signupView.setErrorPassword(null);
        }
        if(!userForm[2].matches(userForm[3])){//password and repeatPassword
            signupView.setErrorPassword("Senha não confere");
            signupView.setErrorRepeatPassword("Senha não confere");
            valid = false;
        }else {
            signupView.setErrorRepeatPassword(null);
        }
        return valid;
    }

}
