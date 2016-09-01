package application.samoht.br.cardfit.ui.main.menu.cards;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.service.FirebaseController;
import application.samoht.br.cardfit.models.StandardClass;

/**
 * Created by Thomas on 19/08/16.
 */
public class CardsPresenter {

    private ICardsView cardsView;
    private FirebaseController firebaseController;
    private ArrayList<ArrayList<CardItem>> arrayOfArrayListCardItem;

    public CardsPresenter(ICardsView iCardsView){
        this.cardsView = iCardsView;
        this.firebaseController = new FirebaseController();
        cardsView.startLoading();
        firebaseController.retrieveCards(this);
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
        firebaseController.retrieveStandardClasses(this);
    }

    public void notifyCreateDialogNewActivity(Map<String, StandardClass> classes){
        cardsView.createDialogNewActivity(classes);
    }

    public void deleteCards(){
        firebaseController.deleteAllCards();
    }

    public void setError(String error) {
        cardsView.showError(error);
    }

    public void registerNewActivity(int cardID,int cardItemID, CardItem cardItem) {
        firebaseController.addNewActivity(cardID, cardItemID, cardItem);
    }
}
