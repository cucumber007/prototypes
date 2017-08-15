package com.cucumber007.prototypes.sandbox.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.cucumber007.prototypes.R;
import com.cucumber007.prototypes.reusables.logging.LogUtil;
import com.cucumber007.prototypes.reusables.objects.LoginParams;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.login_google) Button loginGoogle;

    private GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 7002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        String clientId = getString(R.string.google_api_client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .requestIdToken(clientId)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionFailResult -> {
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void loginByGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @OnClick(R.id.login_google)
    public void onClick1() {
        loginByGoogle();
    }

    @OnClick(R.id.logout_google)
    public void onClick() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> LogUtil.makeToast(status.getStatusMessage()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                if (account.getIdToken() != null)
                    AuthRequestManager.getService().login(new LoginParams(account.getIdToken(), LoginParams.SocialType.GOOGLE))
                            .compose(AuthRequestManager.applySchedulersAndHandleErrors())
                            .subscribe(user -> {
                                LogUtil.makeToast("Login success");

                            });
                else LogUtil.makeToast("Token null");
            } else {
                LogUtil.makeToast("Google login failed " + result.getStatus().toString());
            }
        }
    }
}