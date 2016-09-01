package application.samoht.br.cardfit.ui.main.menu.students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseFragment;

/**
 * Created by Thomas on 18/08/16.
 */
public class StudentsFragment extends BaseFragment implements IStudentsView{

    private View rootView;
    private StudentsPresenter studentsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.students,container,false);
        studentsPresenter = new StudentsPresenter(this);
        return rootView;
    }

}
