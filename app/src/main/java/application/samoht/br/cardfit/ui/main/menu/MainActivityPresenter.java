package application.samoht.br.cardfit.ui.main.menu;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 18/08/16.
 */
public class MainActivityPresenter {

    private IMainActivityView mainActivityView;

    public MainActivityPresenter(IMainActivityView iMainActivityView){
        this.mainActivityView = iMainActivityView;
        mainActivityView.setParamView();
        FirebaseHelper.customizeMenuProfile(this);
    }

    public void changeItensMenu() {
        mainActivityView.enableMenu();
    }

    public void onClickMenu(MenuItem item, FragmentManager fragment) {
        if (item.getItemId() == R.id.item_perfil) {
            mainActivityView.callFragmentProfile(fragment);
        } else if (item.getItemId() == R.id.item_card){
            mainActivityView.callFragmentCard(fragment);
        } else if (item.getItemId() == R.id.item_activites) {
            mainActivityView.callFragmentActivities(fragment);
        } else if(item.getItemId() == R.id.item_student){
            mainActivityView.callFragmentStudents(fragment);
        } else if (item.getItemId() == R.id.item_settings) {
            mainActivityView.callFragmentSettings(fragment);
        } else if (item.getItemId() == R.id.item_feedback){
            mainActivityView.callFragmentFeedback(fragment);
        } else if (item.getItemId() == R.id.item_sair) {
            onClickMenuLogout();
        }
    }

    public void onClickMenuLogout(){
        mainActivityView.logoutUser(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseHelper.logoutUser();
                mainActivityView.showInfo(mainActivityView.getMyContext().getString(R.string.info_logout_sucessfuly));
                mainActivityView.callLoginActivity();
            }});
    }

    public void onClickDeleteAllCards() {
        mainActivityView.deleteAllCards(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseHelper.deleteAllCards();
            }
        });
    }
}
