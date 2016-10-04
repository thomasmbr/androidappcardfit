package application.samoht.br.cardfit.ui.main.menu.activities;

import java.util.ArrayList;

import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 17/08/16.
 */
public class ActivitiesPresenter {

    private IActivitiesView activitiesView;

    public ActivitiesPresenter(IActivitiesView iActivitiesView) {
        this.activitiesView = iActivitiesView;
        FirebaseHelper.retrieveStandardActivities(this);
    }

    public void onSucessFully(ArrayList<StandardActivity> arrayListActivities){
        activitiesView.createRecyclerView(arrayListActivities);
    }

    public void onError(){
        activitiesView.showError();
    }
}
