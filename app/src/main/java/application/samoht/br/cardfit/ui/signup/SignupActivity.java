package application.samoht.br.cardfit.ui.signup;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseActivity;
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
    @BindView(R.id.til_name) protected TextInputLayout til_name;
    @BindView(R.id.til_email) protected TextInputLayout til_email;
    @BindView(R.id.til_password) protected TextInputLayout til_password;
    @BindView(R.id.til_repeat_password) protected TextInputLayout til_repeat_password;
    @BindView(R.id.tb_instrutor) protected Switch switchTrainer;
    @BindView(R.id.imageView2) protected ImageView imageViewBack;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        if (!getIntent().getStringExtra("email").isEmpty())
            editTextEmail.setText(getIntent().getStringExtra("email"));
        if (!getIntent().getStringExtra("password").isEmpty())
            editTextPassword.setText(getIntent().getStringExtra("password"));
        switchTrainer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                signUpPresenter.changedSwitch(isChecked);
            }
        });
        signUpPresenter = new SignUpPresenter(this);
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
        til_email.setError(message);
        if (imageViewBack.getLayoutParams().height <= 750)
            imageViewBack.getLayoutParams().height += 150;
    }

    @Override
    public void setErrorPassword(String message){
        til_password.setError(message);
        if (imageViewBack.getLayoutParams().height <= 750)
            imageViewBack.getLayoutParams().height += 150;
    }

    @Override
    public void setErrorRepeatPassword(String message){
        til_repeat_password.setError(message);
        if (imageViewBack.getLayoutParams().height <= 750)
            imageViewBack.getLayoutParams().height += 150;
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
