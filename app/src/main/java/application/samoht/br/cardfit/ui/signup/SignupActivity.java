package application.samoht.br.cardfit.ui.signup;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import application.samoht.br.cardfit.base.BaseActivity;
import application.samoht.br.cardfit.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Thomas on 05/08/16.
 */
public class SignupActivity extends BaseActivity implements ISignupView{

    @BindView(R.id.et_name) protected EditText editTextName;
    @BindView(R.id.et_email) protected EditText editTextEmail;
    @BindView(R.id.et_password) protected EditText editTextPassword;
    @BindView(R.id.et_repeat_password) protected EditText editTextRepeatPassword;
    @BindView(R.id.tb_instrutor) protected Switch switchTrainer;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        signUpPresenter = new SignUpPresenter(this);

        editTextEmail.setText(getIntent().getStringExtra("email"));
        switchTrainer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signUpPresenter.changedSwitch(isChecked);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        signUpPresenter.setOnStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        signUpPresenter.setOnStop();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showError(String message) {
        showToast(message);
    }

    @Override
    public void startLoading() {
        showProgressDialog(getString(R.string.registerUser));
    }

    @Override
    public void finishLoading() {
        hideProgressDialog();
    }

    @Override
    public void showAlert() {
        showSimpleAlertDialog(getString(R.string.info_to_new_instructor));
    }

    @Override
    public void showInfo(){
        showToast(getString(R.string.signup_successfuly));
    }

    @Override
    public void setErrorEmail(String message){
        editTextEmail.setError(message);
    }

    @Override
    public void setErrorPassword(String message){
        editTextPassword.setError(message);
    }

    @Override
    public void setErrorRepeatPassword(String message){
        editTextRepeatPassword.setError(message);
    }

    @OnClick(R.id.button_signup)
    public void onClickSignUp(){
        String[] userData = new String[5];
        userData[0] = editTextName.getText().toString();
        userData[1] = editTextEmail.getText().toString();
        userData[2] = editTextPassword.getText().toString();
        userData[3] = editTextRepeatPassword.getText().toString();
        userData[4] = String.valueOf(switchTrainer.isChecked());
        signUpPresenter.doSignUp(userData);
    }

}
