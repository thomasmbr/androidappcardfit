package application.samoht.br.cardfit.ui.signup;
import android.text.TextUtils;

import application.samoht.br.cardfit.service.FirebaseController;

/**
 * Created by Thomas on 08/08/16.
 */
public class SignUpPresenter{

    private ISignupView signupView;
    private FirebaseController firebaseController;

    public SignUpPresenter(ISignupView s){
        this.signupView = s;
        this.firebaseController = new FirebaseController(this);
        initSetupFirebase();
    }

    public void initSetupFirebase() {
        firebaseController.autoLogin();
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
        firebaseController.addListenerFirebaseAuth();
    }

    public void setOnStop() {
        firebaseController.removeAuthStateListener();
    }

    public void doSignUp(String[] userData){
        if (validateForm(userData)){
            signupView.startLoading();
            firebaseController.doSignUpUser(signupView.getActivity(), userData);
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
