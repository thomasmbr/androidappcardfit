package application.samoht.br.cardfit.ui.main.menu.cards;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseFragment;
import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;
import application.samoht.br.cardfit.ui.main.menu.cards.adapter.CardsAdapter;
import application.samoht.br.cardfit.adapters.dialogActivitiesAdapter;
import application.samoht.br.cardfit.ui.newcard.NewCardActivity;
import application.samoht.br.cardfit.util.DividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thomas on 23/04/16.
 */
public class CardsFragment extends BaseFragment implements ICardsView{

    @BindView(R.id.spinner_class) protected Spinner spinner;
    @BindView(R.id.recycler_cards) protected RecyclerView recyclerView;
    @BindView(R.id.fab_menu) protected FloatingActionsMenu floatingActionsMenu;
    @BindView(R.id.button_new_activity) protected FloatingActionButton fabNewActivity;
    @BindView(R.id.button_delete_cards) protected FloatingActionButton fabDeleteCards;
    @BindView(R.id.title) protected TextView tvNoCards;

    private View rootView;
    private CardsPresenter cardsPresenter;
    private Dialog dialogParams, dialogListActivities;
    private CardItem cardItem;
    private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public CardsFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.ficha,null);
        ButterKnife.bind(CardsFragment.this, rootView);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(CardsFragment.this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        cardsPresenter = new CardsPresenter(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        cardsPresenter.setOnResume();
    }

    @Override
    public void createList(final ArrayList<ArrayList<CardItem>> arrayOfArrayListCardItem){
        ArrayList<String> cards = new ArrayList<>();
        for (int i=0; i < arrayOfArrayListCardItem.size(); i++) {
            cards.add("Ficha "+alphabet.charAt(i));
        }
        if(cards.size() == 0) {
            cards.add("Nenhuma ficha criada");
            fabNewActivity.setVisibility(View.GONE);
            fabDeleteCards.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            tvNoCards.setVisibility(View.VISIBLE);
        }else if (cards.size() > 0) {
            fabNewActivity.setVisibility(View.VISIBLE);
            fabDeleteCards.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.VISIBLE);
            tvNoCards.setVisibility(View.GONE);
            ArrayAdapter<String> arrayAdapterSpinner = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,cards);
            spinner.setAdapter(arrayAdapterSpinner);
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, final int positionCard, long id) {//Spinner Selected
                    final ArrayList<CardItem> listCardItem = arrayOfArrayListCardItem.get(positionCard);
                    recyclerView.setAdapter(new CardsAdapter(listCardItem, getContext()));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
            });
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void startLoading(){
        showProgressDialog(getString(R.string.wait));
    }

    @Override
    public void finishLoading() {
        hideProgressDialog();
    }

    @Override
    public void callNewCardActivity() {
        if (floatingActionsMenu.isExpanded()){
            floatingActionsMenu.collapse();
        }
        Intent intent = new Intent(getActivity(),NewCardActivity.class);
        if(spinner.getAdapter() != null) {
            intent.putExtra("countCards", spinner.getAdapter().getCount());
        }else{
            intent.putExtra("countCards", 0);
        }
        startActivity(intent);
    }

    @Override
    public void createDialogNewActivity(final Map<String, StandardClass> classes) {
        if (floatingActionsMenu.isExpanded()) {
            floatingActionsMenu.collapse();
        }
        CharSequence[] csClasses = convertMapToArrayCharSequence(classes);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    private CharSequence[] convertMapToArrayCharSequence(Map<String, StandardClass> classes) {
        ArrayList<String> arrayClasses = new ArrayList<>();
        for (String key:classes.keySet()) {
            arrayClasses.add(classes.get(key).getName());
        }
        return arrayClasses.toArray(new CharSequence[arrayClasses.size()]);
    }

    private void createDialogListActivities(Map<String, StandardClass> classes, CharSequence classID){
        dialogListActivities = new Dialog(getContext());
        dialogListActivities.setContentView(R.layout.dialog_list_actitivies);
        dialogListActivities.setTitle(getString(R.string.title_which_new_activity)+" "+alphabet.charAt(spinner.getSelectedItemPosition()));
        dialogListActivities.setCanceledOnTouchOutside(true);
        dialogListActivities.setCancelable(true);
        RecyclerView recyclerViewActivities = ButterKnife.findById(dialogListActivities, R.id.recycler_activities);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewActivities.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerViewActivities.setLayoutManager(layout);
        recyclerViewActivities.setAdapter(new dialogActivitiesAdapter(convertMapToArrayListActivities(classes.get(classID).getActivities()), getContext(),this));
        dialogListActivities.show();

    }

    private ArrayList<StandardActivity> convertMapToArrayListActivities(Map<String, StandardActivity> activities){
        ArrayList<StandardActivity> arrayListActivities = new ArrayList<>();
        for(String key:activities.keySet()){
            arrayListActivities.add(activities.get(key));
        }
        return arrayListActivities;
    }

    @Override
    public void createDialogParamsActivity(final StandardActivity standardActivity) {
        if (dialogListActivities.isShowing()){
            dialogListActivities.cancel();
        }
        dialogParams = new Dialog(getContext());
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
            dialogParams = new Dialog(getContext());
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
            //gravar dados
            cardsPresenter.registerNewActivity(spinner.getSelectedItemPosition(),recyclerView.getAdapter().getItemCount(), cardItem);
            cardItem = null;
        }
    }

    @Override
    public void showError(String error) {
        Log.e("error",error);
    }

    private void reloadFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @OnClick(R.id.button_new_card)
    public void onClickNewCard(){
        cardsPresenter.newCard();
    }

    @OnClick(R.id.button_new_activity)
    public void onClickNewActivity(){
        cardsPresenter.newActivity();
    }

    @OnClick(R.id.button_delete_cards)
    public void onClickDeleteCards(){
        if (floatingActionsMenu.isExpanded()) {
            floatingActionsMenu.collapse();
        }
        showConfirmDialog(getContext(), getString(R.string.message_confirm_delete_cards), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cardsPresenter.deleteCards();
                reloadFragment();
            }
        });
    }

}
