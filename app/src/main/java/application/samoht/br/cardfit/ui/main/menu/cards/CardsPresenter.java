package application.samoht.br.cardfit.ui.main.menu.cards;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardClass;
import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 19/08/16.
 */
public class CardsPresenter {

    private ICardsView cardsView;
    private ArrayList<ArrayList<CardItem>> arrayOfArrayListCardItem;

    public CardsPresenter(ICardsView iCardsView){
        this.cardsView = iCardsView;
        cardsView.startLoading();
        FirebaseHelper.retrieveCards(this);
    }

    public void onSucessFully(ArrayList<ArrayList<CardItem>> arrayOfArrayList){
        this.arrayOfArrayListCardItem = arrayOfArrayList;
        cardsView.finishLoading();
        cardsView.createList(arrayOfArrayListCardItem);
    }

    public void onError(){
        cardsView.showError();
    }

    public void newCard() {
        cardsView.callNewCardActivity();
    }

    public void newActivity(){
        FirebaseHelper.retrieveStandardClasses(this);
    }

    public void notifyCreateDialogNewActivity(Map<String, StandardClass> classes){
        cardsView.createDialogNewActivity(classes);
    }

    public void deleteCards(){
        FirebaseHelper.deleteAllCards();
    }

    public void setError(String error) {
        cardsView.showError(error);
    }

    public void registerNewActivity(int cardID,int cardItemID, CardItem cardItem) {
        FirebaseHelper.addNewActivity(cardID,cardItemID,cardItem);
    }
}
