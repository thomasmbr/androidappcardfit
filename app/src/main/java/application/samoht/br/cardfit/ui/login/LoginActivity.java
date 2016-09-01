package application.samoht.br.cardfit.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import application.samoht.br.cardfit.base.BaseActivity;
import application.samoht.br.cardfit.ui.main.menu.MainActivity;
import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.ui.signup.SignupActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView{

    @BindView(R.id.et_username_login) protected EditText editTextEmail;
    @BindView(R.id.et_password) protected EditText editTextPassword;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        loginPresenter.setOnStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        loginPresenter.setOnStop();
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
    public void startLoading(){
        showProgressDialog(getText(R.string.loginUser).toString());
    }

    @Override
    public void finishLoading(){
        hideProgressDialog();
    }

    @Override
    public void callMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void callSignUpActivity(String email){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        if(!email.isEmpty())
            intent.putExtra("email", email);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin(){
        if(loginPresenter != null){
            loginPresenter.doLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        }
    }

    @OnClick(R.id.btn_register)
    public void onClickRegister(){
        loginPresenter.doSignUp(editTextEmail.getText().toString());
    }
}