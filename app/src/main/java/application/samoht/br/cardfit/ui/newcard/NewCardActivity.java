package application.samoht.br.cardfit.ui.newcard;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.adapters.dialogActivitiesAdapter;
import application.samoht.br.cardfit.base.BaseActivity;
import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;
import application.samoht.br.cardfit.ui.main.menu.MainActivity;
import application.samoht.br.cardfit.ui.newcard.Adapter.NewCardsAdapter;
import application.samoht.br.cardfit.util.DividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewCardActivity extends BaseActivity implements INewCardView{

    @BindView(R.id.title)  protected TextView titleInfo;
    @BindView(R.id.recycler_activities) protected RecyclerView recyclerView;
    private NewCardPresenter newCardPresenter;
    private ArrayList<CardItem> arrayListCardItem;
    private Dialog dialogListActivities, dialogParams;
    private int cardNumber;
    private CardItem cardItem;
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_new_card);
        ButterKnife.bind(this);
        newCardPresenter = new NewCardPresenter(this);
    }

    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_new_card,menu);
        return true;
    }

    public boolean onOptionsItemSelected (final MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.check_card:
                if (recyclerView.getAdapter().getItemCount() < 1){
                    showSimpleAlertDialog(getString(R.string.card_not_empty));
                }else {
                    newCardPresenter.registerNewCard(cardNumber, arrayListCardItem);
                    Intent intent = new Intent(NewCardActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initActivity(){
        cardNumber = getIntent().getIntExtra("countCards",-1);
        setTitle("Ficha "+alphabet.charAt(cardNumber));
        arrayListCardItem = new ArrayList<>();
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(new NewCardsAdapter(arrayListCardItem, this));
        MobileAds.initialize(this, getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        //AdRequest adRequest = new AdRequest.Builder().addTestDevice("C099EF074191BB891F315F42E173345F").build();
        mAdView.loadAd(adRequest);
    }

    @OnClick(R.id.floating_action_button)
    public void onClickFloatingActionButton(){
        newCardPresenter.clickFloatingActionButton();
    }

    @Override
    public void createDialogNewActivity(final Map<String, StandardClass> classes) {
        CharSequence[] csClasses = newCardPresenter.convertMapToArrayCharSequence(classes);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.title_dialog_new_cards))
                .setItems(csClasses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        String[] arrayOfIdsClasses = classes.keySet().toArray(new String[classes.keySet().size()]);
                        createDialogListActivities(classes,arrayOfIdsClasses[position]);
                    }
                })
                .create()
                .show();
    }

    private void createDialogListActivities(Map<String, StandardClass> classes, CharSequence classID){
        dialogListActivities = new Dialog(this);
        dialogListActivities.setContentView(R.layout.dialog_list_actitivies);
        dialogListActivities.setTitle(getString(R.string.title_which_new_activity)+" "+alphabet.charAt(cardNumber));
        dialogListActivities.setCanceledOnTouchOutside(true);
        dialogListActivities.setCancelable(true);
        RecyclerView recyclerViewActivities = ButterKnife.findById(dialogListActivities, R.id.recycler_activities);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewActivities.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        recyclerViewActivities.setLayoutManager(layout);
        recyclerViewActivities.setAdapter(new dialogActivitiesAdapter(newCardPresenter.convertMapToArrayListActivities(classes.get(classID).getActivities()), this.getBaseContext(),this));
        dialogListActivities.show();
    }

    @Override
    public void createDialogParamsActivity(final StandardActivity standardActivity) {
        if (dialogListActivities.isShowing()){
            dialogListActivities.cancel();
        }
        dialogParams = new Dialog(this);
        dialogParams.setContentView(R.layout.dialog_new_activity_param1);
        dialogParams.setTitle(standardActivity.getName());
        dialogParams.setCancelable(false);
        dialogParams.setCanceledOnTouchOutside(false);
        final NumberPicker numberPickerSeries = ButterKnife.findById(dialogParams, R.id.picker_series);
        numberPickerSeries.setMinValue(1);
        numberPickerSeries.setMaxValue(10);
        numberPickerSeries.setValue(4);
        numberPickerSeries.setWrapSelectorWheel(true);
        final NumberPicker numberPickerRestTime = ButterKnife.findById(dialogParams, R.id.picker_rest_time);
        numberPickerRestTime.setMinValue(1);
        numberPickerRestTime.setMaxValue(300);
        numberPickerRestTime.setValue(60);
        numberPickerRestTime.setWrapSelectorWheel(true);
        Button buttonOk = ButterKnife.findById(dialogParams, R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardItem = new CardItem();
                cardItem.setName(standardActivity.getName());
                cardItem.setDescription(standardActivity.getDescription());
                cardItem.setNote(standardActivity.getNote());
                cardItem.setThumbnail(standardActivity.getThumbnail());
                cardItem.setVideo(standardActivity.getVideo());
                cardItem.setRestTime(numberPickerRestTime.getValue());
                createDialogParamsActivity(numberPickerSeries.getValue(),1);
            }
        });
        dialogParams.show();
    }

    private void createDialogParamsActivity(final int countSeries, final int numberSerie){
        if (dialogParams.isShowing()){
            dialogParams.cancel();
        }
        if (countSeries > 0) {
            dialogParams = new Dialog(this);
            dialogParams.setContentView(R.layout.dialog_new_activity_param2);
            dialogParams.setTitle(getString(R.string.serie)+" "+numberSerie);
            dialogParams.setCancelable(false);
            dialogParams.setCanceledOnTouchOutside(false);
            final NumberPicker numberPickerRepetition = ButterKnife.findById(dialogParams, R.id.picker_repetition);
            numberPickerRepetition.setMinValue(1);
            numberPickerRepetition.setMaxValue(100);
            numberPickerRepetition.setValue(10);
            numberPickerRepetition.setWrapSelectorWheel(true);
            final NumberPicker numberPickerCharge = ButterKnife.findById(dialogParams, R.id.picker_charge);
            numberPickerCharge.setMinValue(1);
            numberPickerCharge.setMaxValue(1000);
            numberPickerCharge.setWrapSelectorWheel(true);
            Button buttonOk = ButterKnife.findById(dialogParams, R.id.button_ok);
            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardItem.addSerie(numberPickerRepetition.getValue(),numberPickerCharge.getValue());
                    createDialogParamsActivity(countSeries-1,numberSerie+1);
                }
            });
            dialogParams.show();
        }else {
            arrayListCardItem.add(cardItem);
            recyclerView.getAdapter().notifyDataSetChanged();
            changeVisibilityTextInfo();
            cardItem = null;
        }
    }

    public void changeVisibilityTextInfo(){
        if(recyclerView.getAdapter().getItemCount() > 0){
            titleInfo.setVisibility(View.GONE);
        }else{
            titleInfo.setVisibility(View.VISIBLE);
        }
    }
}
