package application.samoht.br.cardfit.ui.main.menu.feedback;

import application.samoht.br.cardfit.service.FirebaseHelper;

/**
 * Created by Thomas on 16/08/16.
 */
public class FeedbackPresenter {

    private IFeedbackView feedbackView;

    public FeedbackPresenter(IFeedbackView feedbackView) {
        this.feedbackView = feedbackView;
    }

    public void sendMessage(String message) {
        FirebaseHelper.registerFeedback(message);
        feedbackView.finishSend();
    }

}
