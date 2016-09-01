package application.samoht.br.cardfit.ui.main.menu.feedback;

import application.samoht.br.cardfit.service.FirebaseController;

/**
 * Created by Thomas on 16/08/16.
 */
public class FeedbackPresenter {

    private IFeedbackView feedbackView;
    private FirebaseController firebaseController;

    public FeedbackPresenter(IFeedbackView feedbackView) {
        this.feedbackView = feedbackView;
        firebaseController = new FirebaseController();
    }

    public void sendMessage(String message) {
        firebaseController.registerFeedback(message);
        feedbackView.finishSend();
    }

}
