package application.samoht.br.cardfit.ui.login;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Thomas on 05/08/16.
 */
public interface ILoginView {
    Activity getActivity();
    void showError(String message);
    void startLoading();
    void finishLoading();
    void callMainActivity();
    void callSignUpActivity(String email, String password);
}
