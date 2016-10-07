package application.samoht.br.cardfit.ui.main.menu;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseActivity;
import application.samoht.br.cardfit.ui.login.LoginActivity;
import application.samoht.br.cardfit.ui.main.menu.activities.ActivitiesFragment;
import application.samoht.br.cardfit.ui.main.menu.cards.CardsFragment;
import application.samoht.br.cardfit.ui.main.menu.feedback.FeedbackFragment;
import application.samoht.br.cardfit.ui.main.menu.perfil.ProfileFragment;
import application.samoht.br.cardfit.ui.main.menu.settings.SettingsFragment;
import application.samoht.br.cardfit.ui.main.menu.students.StudentsFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainActivityView {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.drawer_layout) protected DrawerLayout drawer;
    @BindView(R.id.nav_view) protected NavigationView navigationView;

    private MainActivityPresenter mainActivityPresenter;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainActivityPresenter = new MainActivityPresenter(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sair){
            mainActivityPresenter.onClickMenuLogout();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        mainActivityPresenter.onClickMenu(item, fm);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Context getMyContext() {
        return this.getBaseContext();
    }

    @Override
    public void setParamView() {
        setTitle(getString(R.string.cards));
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        fmt.replace(R.id.content_frame, new CardsFragment()).commit();
        navigationView.getMenu().getItem(1).setChecked(true);
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void enableMenu(){
        navigationView.getMenu().getItem(3).setVisible(true);//Seta menu alunos como visible
    }

    @Override
    public void showInfo(String message) {
        showToast(message);
    }

    @Override
    public void callFragmentProfile(FragmentManager fm) {
        MainActivity.this.setTitle(getString(R.string.profile));
        fm.beginTransaction().replace(R.id.content_frame, new ProfileFragment()).commit();
    }

    @Override
    public void callFragmentCard(FragmentManager fm) {
        MainActivity.this.setTitle(getString(R.string.card));
        fm.beginTransaction().replace(R.id.content_frame, new CardsFragment()).commit();
    }

    @Override
    public void callFragmentActivities(FragmentManager fm) {
        setTitle(getString(R.string.activities));
        fm.beginTransaction().replace(R.id.content_frame, new ActivitiesFragment()).commit();
    }

    @Override
    public void callFragmentStudents(FragmentManager fm) {
        setTitle(getString(R.string.students));
        fm.beginTransaction().replace(R.id.content_frame, new StudentsFragment()).commit();
    }

    @Override
    public void callFragmentSettings(FragmentManager fm) {
        setTitle(getString(R.string.settings));
        fm.beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
    }

    @Override
    public void callFragmentFeedback(FragmentManager fm) {
        setTitle(getString(R.string.feedback));
        fm.beginTransaction().replace(R.id.content_frame, new FeedbackFragment()).commit();
    }

    //@OnClick(R.id.button_new_cards)
    public void onClickNewCards(){
    }

    //@OnClick(R.id.button_delete_cards)
    public void onClickDeleteAllCards(){
        mainActivityPresenter.onClickDeleteAllCards();
    }

    @Override
    public void deleteAllCards(DialogInterface.OnClickListener onClickListener){
        showConfirmDialog(getString(R.string.message_confirm_delete_cards), onClickListener);
    }

    @Override
    public void logoutUser(DialogInterface.OnClickListener onClickListener) {
        showConfirmDialog(getString(R.string.message_confirm_logout), onClickListener);
    }

    @Override
    public void callLoginActivity(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.finish();
        MainActivity.this.startActivity(intent);
    }

}


