package application.samoht.br.cardfit.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import application.samoht.br.cardfit.R;
import application.samoht.br.cardfit.base.BaseActivity;
import application.samoht.br.cardfit.ui.main.menu.MainActivity;
import application.samoht.br.cardfit.ui.signup.SignupActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView{

    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.et_username_login) protected EditText editTextEmail;
    @BindView(R.id.et_password) protected EditText editTextPassword;
    @BindView(R.id.imageView2) protected ImageView imageViewBack;
    private LoginPresenter loginPresenter;
    private GoogleSignInOptions mGoogleSignInOptions;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void callSignUpActivity(String email, String password){
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    public void onClickLogin(){
        if(loginPresenter != null){
            if((!TextUtils.isEmpty(editTextEmail.getText()))
                    &&(!TextUtils.isEmpty(editTextPassword.getText()))){
                loginPresenter.doLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }else {
                showError(getString(R.string.error_email_pass_empty));
                TextInputLayout til_login = (TextInputLayout)findViewById(R.id.til_login);
                TextInputLayout til_password = (TextInputLayout)findViewById(R.id.til_password);
                til_login.setError(getString(R.string.email_required));
                til_password.setError(getString(R.string.password_required));
                if (imageViewBack.getLayoutParams().height <= 550)
                    imageViewBack.getLayoutParams().height += 100;
            }
        }
    }

    @OnClick(R.id.btn_register)
    public void onClickRegister(){
        loginPresenter.doSignUp(editTextEmail.getText().toString(), editTextPassword.getText().toString());
    }

    @OnClick(R.id.btn_login_google)
    public void signInGoogle() {
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        showError(getString(R.string.error_connect_google_auth));
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                showProgressDialog(getString(R.string.loginUser));
                loginPresenter.authWithGoogle(result);
            } else {
                showError(getString(R.string.error_connect_google_auth));
            }
        }
    }

}