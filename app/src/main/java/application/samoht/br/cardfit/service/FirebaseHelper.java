package application.samoht.br.cardfit.service;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import application.samoht.br.cardfit.ui.login.LoginPresenter;

/**
 * Created by Thomas on 26/08/16.
 */
public class FirebaseHelper {

    private static String LANG = "pt-br";
    private static String USERS = "users";
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static DatabaseReference getUserDBRef(){
        return firebaseDatabase.getReference().child(USERS).child(firebaseUser.getUid());
    }

    private static DatabaseReference getClassesDBRef() {
        return firebaseDatabase.getReference().child(LANG);
    }

    public static void autoLogin(final LoginPresenter loginPresenter){
        if (firebaseAuth != null) {
            authStateListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if ((user != null)&&(loginPresenter != null)) {
                        loginPresenter.finishProcessLogin();
                    }
                }
            };
        }
    }

    public static void doLoginUser(Activity activity, final LoginPresenter loginPresenter, String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            loginPresenter.finishProcessLogin();
                        } else {
                            loginPresenter.setError(task.getException().getMessage());
                        }

                    }
                });
    }

    public static void deleteAllCards(){
        getUserDBRef().child("cards").removeValue();
    }

    public static void registerFeedback(String message){
        firebaseDatabase.getReference().child("feedback").child(firebaseUser.getUid()).child("user").setValue(firebaseUser.getEmail());
        firebaseDatabase.getReference().child("feedback").child(firebaseUser.getUid()).child("messages").push().setValue(message);
    }


}
