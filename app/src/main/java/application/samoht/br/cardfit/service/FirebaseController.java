package application.samoht.br.cardfit.service;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.models.CardItem;
import application.samoht.br.cardfit.models.StandardActivity;
import application.samoht.br.cardfit.models.StandardClass;
import application.samoht.br.cardfit.models.User;
import application.samoht.br.cardfit.ui.login.LoginPresenter;
import application.samoht.br.cardfit.ui.main.menu.MainActivityPresenter;
import application.samoht.br.cardfit.ui.main.menu.activities.ActivitiesPresenter;
import application.samoht.br.cardfit.ui.main.menu.cards.CardsPresenter;
import application.samoht.br.cardfit.ui.main.menu.perfil.ProfilePresenter;
import application.samoht.br.cardfit.ui.newcard.NewCardPresenter;
import application.samoht.br.cardfit.ui.signup.SignUpPresenter;

/**
 * Created by Thomas on 7/4/16.
 */
public class FirebaseController{

    private static final String TAG = FirebaseController.class.getSimpleName();
    private  NewCardPresenter newCardPresenter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private Map<String,StandardClass> classes;
    private LoginPresenter loginPresenter;
    private SignUpPresenter signUpPresenter;
    private MainActivityPresenter mainActivityPresenter;
    private static String LANG = "pt-br";
    private static String USERS = "users";
    private FirebaseAuth.AuthStateListener authStateListener;

    private ValueEventListener classesListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            classes = new HashMap<>();
            for (DataSnapshot dsClasse:dataSnapshot.getChildren()) {
                StandardClass classe = dataSnapshot.child(dsClasse.getKey()).getValue(StandardClass.class);

                Map<String,StandardActivity> activities = new HashMap<>();
                for (DataSnapshot dsActivity:dsClasse.getChildren()) {
                    if(!dsActivity.getKey().matches("name")) {
                        StandardActivity activity = dataSnapshot.child(dsClasse.getKey()).child(dsActivity.getKey()).getValue(StandardActivity.class);
                        activities.put(dsActivity.getKey(),activity);
                    }
                }
                classe.setActivities(activities);
                classes.put(dsClasse.getKey(),classe);
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public FirebaseController(){
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        getClassesDBRef().addValueEventListener(classesListener);
    }

    public FirebaseController(LoginPresenter l){
        this.loginPresenter = l;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public FirebaseController(SignUpPresenter s) {
        this.signUpPresenter = s;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public FirebaseController(MainActivityPresenter m) {
        this.mainActivityPresenter = m;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public FirebaseController(NewCardPresenter n) {
        this.newCardPresenter = n;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getUserDBRef(){
        return firebaseDatabase.getReference().child(USERS).child(firebaseUser.getUid());
    }

    public DatabaseReference getClassesDBRef(){
        return firebaseDatabase.getReference().child(LANG);
    }

    public Map<String,StandardClass> getClasses(){
        return this.classes;
    }

    public void addNewCard(int id, ArrayList<CardItem> elements){
        getUserDBRef().child("cards").child(String.valueOf(id)).setValue(elements);
    }

    public void deleteAllCards(){
        getUserDBRef().child("cards").removeValue();
    }

    public void registerFeedback(String message){
        firebaseDatabase.getReference().child("feedback").child(firebaseUser.getUid()).child("user").setValue(firebaseUser.getEmail());
        firebaseDatabase.getReference().child("feedback").child(firebaseUser.getUid()).child("messages").push().setValue(message);

    }



    //LoginActivity
    //SignUpActivity
    public void autoLogin(){
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

    public void doLoginUser(Activity activity, String email, String password){
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

    public void addListenerFirebaseAuth(){
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void removeAuthStateListener(){
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

    //SignUpActivity
    public void doSignUpUser(Activity activity, final String[] userData){
        if ((firebaseDatabase != null)&&(firebaseAuth != null)){
            firebaseAuth.createUserWithEmailAndPassword(userData[1], userData[3])
                        .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    DatabaseReference dbRef = firebaseDatabase.getReference();
                                    dbRef.child("users").child(user.getUid()).child("name").setValue(userData[0]);
                                    dbRef.child("users").child(user.getUid()).child("trainer").setValue(Boolean.valueOf(userData[4]));
                                    signUpPresenter.finishProcessLogin();
                                } else {
                                    signUpPresenter.setError(task.getException().toString());
                                    FirebaseCrash.report(task.getException());
                                }
                            }
                        });
        }
    }


    //ProfileFragment
    public void retrieveInfoUser(final ProfilePresenter profilePresenter,final Context context, final ListView listView) {
        getUserDBRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                ArrayList<String> data = new ArrayList<>();
                data.add(user.getName());
                if(user.getTrainer()){
                    data.add(context.getString(R.string.instructor));
                }else {
                    data.add(context.getString(R.string.student));
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,R.layout.simple_list,data);
                listView.setAdapter(arrayAdapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                profilePresenter.setError();
                //FirebaseCrash.report(databaseError.toException());
                Log.e(TAG,"ErrorFirebaseListener: "+databaseError.toString());
            }
        });
    }

    //ActivitiesFragment
    public void retrieveStandardActivities(final ActivitiesPresenter activitiesPresenter) {

        final ArrayList<StandardActivity> arrayListActivities = new ArrayList<>();
        getClassesDBRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,StandardClass>classes = new HashMap<>();
                for (DataSnapshot dsClasse:dataSnapshot.getChildren()) {
                    StandardClass classe = dataSnapshot.child(dsClasse.getKey()).getValue(StandardClass.class);
                    Map<String,StandardActivity> activities = new HashMap<>();
                    for (DataSnapshot dsActivity:dsClasse.getChildren()) {
                        if(!dsActivity.getKey().matches("name")) {
                            StandardActivity activity = dataSnapshot.child(dsClasse.getKey()).child(dsActivity.getKey()).getValue(StandardActivity.class);
                            activities.put(dsActivity.getKey(),activity);
                        }
                    }
                    classe.setActivities(activities);
                    classes.put(dsClasse.getKey(),classe);
                }
                for (Map.Entry<String,StandardClass> classe: classes.entrySet()) {
                    StandardClass standardClasse = classe.getValue();
                    for(Map.Entry<String,StandardActivity> activity: standardClasse.getActivities().entrySet()){
                        StandardActivity standardActivity = activity.getValue();
                        arrayListActivities.add(standardActivity);
                    }
                }
                activitiesPresenter.onSucessFully(arrayListActivities);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                activitiesPresenter.onError();
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }

    //MainActivity
    public void customizeMenuProfile() {
        getUserDBRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User usercurrent = dataSnapshot.getValue(User.class);
                if (usercurrent.getTrainer()) {
                    mainActivityPresenter.changeItensMenu();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void logoutUser(){
        firebaseAuth.getInstance().signOut();
    }


    //CardsFragment
    public void retrieveCards(final CardsPresenter cardsPresenter) {
        getUserDBRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    cardsPresenter.onSucessFully(user.getCards());
                }else {
                    cardsPresenter.onError();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error","CardView2: "+databaseError.getMessage());
            }
        });
    }

    public void retrieveStandardClasses(final CardsPresenter cardsPresenter) {
        getClassesDBRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classes = new HashMap<>();
                if(classes != null){
                    for (DataSnapshot dsClasse:dataSnapshot.getChildren()) {
                        StandardClass classe = dataSnapshot.child(dsClasse.getKey()).getValue(StandardClass.class);

                        Map<String,StandardActivity> activities = new HashMap<>();
                        for (DataSnapshot dsActivity:dsClasse.getChildren()) {
                            if(!dsActivity.getKey().matches("name")) {
                                StandardActivity activity = dataSnapshot.child(dsClasse.getKey()).child(dsActivity.getKey()).getValue(StandardActivity.class);
                                activities.put(dsActivity.getKey(),activity);
                            }
                        }
                        classe.setActivities(activities);
                        classes.put(dsClasse.getKey(),classe);
                    }
                    cardsPresenter.notifyCreateDialogNewActivity(classes);
                }else {
                    cardsPresenter.setError("Falha ao buscar dados");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewActivity(int cardID, int cardItemID, CardItem cardItem) {
        getUserDBRef().child("cards").child(String.valueOf(cardID)).child(String.valueOf(cardItemID)).setValue(cardItem);
    }

    //newCard Activity
    public void retrieveStandardClasses(final NewCardPresenter newCardPresenter) {
        getClassesDBRef().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                classes = new HashMap<>();
                    for (DataSnapshot dsClasse:dataSnapshot.getChildren()) {
                        StandardClass classe = dataSnapshot.child(dsClasse.getKey()).getValue(StandardClass.class);

                        Map<String,StandardActivity> activities = new HashMap<>();
                        for (DataSnapshot dsActivity:dsClasse.getChildren()) {
                            if(!dsActivity.getKey().matches("name")) {
                                StandardActivity activity = dataSnapshot.child(dsClasse.getKey()).child(dsActivity.getKey()).getValue(StandardActivity.class);
                                activities.put(dsActivity.getKey(),activity);
                            }
                        }
                        classe.setActivities(activities);
                        classes.put(dsClasse.getKey(),classe);
                    }
                Log.i("script","classes.size: "+classes.size());
                if(classes != null){
                    newCardPresenter.notifyCreateDialogNewActivity(classes);
                }else {
                    newCardPresenter.setError("Falha ao buscar dados");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
