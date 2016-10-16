package application.samoht.br.cardfit.ui.main.menu.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import application.samoht.br.cardfit.base.BaseFragment;
import application.samoht.br.cardfit.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thomas on 7/18/16.
 */
public class FeedbackFragment extends BaseFragment implements IFeedbackView{

    @BindView(R.id.messageInput) protected EditText editText;
    @BindView(R.id.sendButton) protected ImageButton sendButton;
    private View rootView;
    private FeedbackPresenter feedbackPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feedback,container,false);
        ButterKnife.bind(FeedbackFragment.this, rootView);
        feedbackPresenter = new FeedbackPresenter(this);
        return rootView;
    }

    @Override
    @OnClick(R.id.sendButton)
    public void onClickButtonSend(){
        if (!editText.getText().toString().isEmpty()){
            feedbackPresenter.sendMessage(editText.getText().toString());
        }else {
            showSimpleAlertDialog(getContext(), getString(R.string.error_empty_field));
        }
    }

    @Override
    public void finishSend(){
        editText.getText().clear();
        showSimpleAlertDialog(rootView.getContext(), getString(R.string.thank_you_feedback));
    }
}
