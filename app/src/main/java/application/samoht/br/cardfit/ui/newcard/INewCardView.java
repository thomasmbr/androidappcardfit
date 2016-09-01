package application.samoht.br.cardfit.ui.newcard;

import java.util.Map;

import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;

/**
 * Created by Thomas on 23/08/16.
 */
public interface INewCardView {
    void initActivity();

    void createDialogNewActivity(Map<String, StandardClass> classes);

    void createDialogParamsActivity(StandardActivity standardActivity);
}
