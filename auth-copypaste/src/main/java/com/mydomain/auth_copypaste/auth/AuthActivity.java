package com.mydomain.auth_copypaste.auth;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.cucumber007.prototypes.R;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthActivity extends AppCompatActivity {

    @BindView(R.id.login_google) Button loginGoogle;

    private GoogleApiClient mGoogleApiClient;
    private String authCode = "NO DATA";
    private String token = "NO DATA";

    private static final int RC_SIGN_IN = 7002;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);

        String clientId = getString(R.string.google_api_client_id);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestId()
                .requestEmail()
                .requestScopes(new Scope(Scopes.EMAIL), new Scope(Scopes.PLUS_LOGIN), new Scope(Scopes.PLUS_ME), new Scope(Scopes.PROFILE))
                .requestProfile()
                .requestServerAuthCode(clientId)
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
                token = account.getIdToken();
                authCode = account.getServerAuthCode();
                LogUtil.makeToast("Login success");
                /*if (account.getServerAuthCode() != null) {
                    LogUtil.logDebug(account.getServerAuthCode());
                    AuthRequestManager.getService().login(new LoginParams(account.getServerAuthCode(), LoginParams.SocialType.GOOGLE))
                            .compose(AuthRequestManager.applySchedulersAndHandleErrors())
                            .subscribe(user -> {
                                LogUtil.makeToast("Login success");
                            });
                } else LogUtil.makeToast("Token null");*/
            } else {
                LogUtil.makeToast("Google login failed " + result.getStatus().toString());
            }
        }
    }

    @OnClick({R.id.copy_token, R.id.b_copy_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.copy_token:
                LogUtil.makeToast("Copied : "+token);
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("token", token));
                break;
            case R.id.b_copy_code:
                LogUtil.makeToast("Copied : "+authCode);
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("auth code", authCode));
                break;
        }
    }
}