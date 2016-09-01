package application.samoht.br.cardfit.ui.main.menu.activities;

import java.util.ArrayList;

import application.samoht.br.cardfit.service.FirebaseController;
import application.samoht.br.cardfit.models.StandardActivity;

/**
 * Created by Thomas on 17/08/16.
 */
public class ActivitiesPresenter {

    private IActivitiesView activitiesView;
    private FirebaseController firebaseController;

    public ActivitiesPresenter(IActivitiesView iActivitiesView) {
        this.activitiesView = iActivitiesView;
        this.firebaseController = new FirebaseController();
        firebaseController.retrieveStandardActivities(this);
    }

    public void onSucessFully(ArrayList<StandardActivity> arrayListActivities){
        activitiesView.createRecyclerView(arrayListActivities);
    }

    public void onError(){
        activitiesView.showError();
    }
}
