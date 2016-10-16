package application.samoht.br.cardfit.ui.main.menu.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseFragment;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.ui.main.menu.activities.adapter.ActivitiesAdapter;
import application.samoht.br.cardfit.util.DividerItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Thomas on 09/04/16.
 */
public class ActivitiesFragment extends BaseFragment implements IActivitiesView{

    @BindView(R.id.recycler) protected RecyclerView recyclerView;
    private View rootView;
    private ActivitiesPresenter activitiesPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activities,container,false);
        ButterKnife.bind(this, rootView);
        activitiesPresenter = new ActivitiesPresenter(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void createRecyclerView(final ArrayList<StandardActivity> arrayListActivities){
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setAdapter(new ActivitiesAdapter(arrayListActivities, this.getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setLayoutManager(layout);
    }

    @Override
    public void showError() {
        if(isResumed())
            showSimpleAlertDialog(getContext(), getString(R.string.error_retrieve_activities));
    }



}
