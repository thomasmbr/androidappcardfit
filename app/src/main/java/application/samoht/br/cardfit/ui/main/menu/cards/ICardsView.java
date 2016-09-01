package application.samoht.br.cardfit.ui.main.menu.cards;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;

/**
 * Created by Thomas on 19/08/16.
 */
public interface ICardsView {

    void createList(ArrayList<ArrayList<CardItem>> arrayListCardItem);

    void showError();

    void startLoading();

    void finishLoading();

    void callNewCardActivity();

    void createDialogNewActivity(Map<String, StandardClass> classes);

    void createDialogParamsActivity(StandardActivity standardActivity);

    void showError(String error);

}
