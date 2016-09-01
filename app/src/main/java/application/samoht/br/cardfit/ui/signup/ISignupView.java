package application.samoht.br.cardfit.ui.signup;

import android.app.Activity;

/**
 * Created by Thomas on 08/08/16.
 */
public interface ISignupView {
    Activity getActivity();
    void showError(String message);
    void startLoading();
    void finishLoading();
    void showAlert();
    void showInfo();
    void onClickSignUp();
    void setErrorEmail(String message);
    void setErrorPassword(String message);
    void setErrorRepeatPassword(String message);
}
