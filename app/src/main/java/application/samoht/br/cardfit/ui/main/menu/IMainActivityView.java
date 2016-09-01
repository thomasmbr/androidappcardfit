package application.samoht.br.cardfit.ui.main.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;

/**
 * Created by Thomas on 18/08/16.
 */
public interface IMainActivityView {
    Context getMyContext();
    void setParamView();
    void enableMenu();
    void showInfo(String message);
    void callFragmentProfile(FragmentManager fm);
    void callFragmentCard(FragmentManager fm);
    void callFragmentActivities(FragmentManager fm);
    void callFragmentStudents(FragmentManager fm);
    void callFragmentSettings(FragmentManager fm);
    void callFragmentFeedback(FragmentManager fm);

    void deleteAllCards(DialogInterface.OnClickListener onClickListener);

    void logoutUser(DialogInterface.OnClickListener onClickListener);
    void callLoginActivity();
}
