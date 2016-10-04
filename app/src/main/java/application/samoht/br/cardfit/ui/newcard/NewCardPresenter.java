package application.samoht.br.cardfit.ui.newcard;

import java.util.ArrayList;
import java.util.Map;

import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;
import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 23/08/16.
 */
public class NewCardPresenter {

    private INewCardView newCardView;

    public NewCardPresenter(INewCardView INewCardView) {
        this.newCardView = INewCardView;
        newCardView.initActivity();
    }

    public void clickFloatingActionButton() {
        FirebaseHelper.retrieveStandardClasses(this);
    }

    public void notifyCreateDialogNewActivity(Map<String, StandardClass> classes) {
        newCardView.createDialogNewActivity(classes);
    }

    public CharSequence[] convertMapToArrayCharSequence(Map<String, StandardClass> classes) {
        ArrayList<String> arrayClasses = new ArrayList<>();
        for (String key:classes.keySet()) {
            arrayClasses.add(classes.get(key).getName());
        }
        return arrayClasses.toArray(new CharSequence[arrayClasses.size()]);
    }

    public ArrayList<StandardActivity> convertMapToArrayListActivities(Map<String, StandardActivity> activities) {
        ArrayList<StandardActivity> arrayListActivities = new ArrayList<>();
        for(String key:activities.keySet()){
            arrayListActivities.add(activities.get(key));
        }
        return arrayListActivities;
    }

    public void setError(String s) {
    }

    public void registerNewCard(int cardID, ArrayList<CardItem> arrayListCardItem) {
        FirebaseHelper.addNewCard(cardID, arrayListCardItem);
    }
}
