package application.samoht.br.cardfit.ui.main.menu.activities;

import java.util.ArrayList;

import application.samoht.br.cardfit.models.StandardActivity;

/**
 * Created by Thomas on 17/08/16.
 */
public interface IActivitiesView {
    void createRecyclerView(ArrayList<StandardActivity> arrayListActivities);
    void showError();
}
