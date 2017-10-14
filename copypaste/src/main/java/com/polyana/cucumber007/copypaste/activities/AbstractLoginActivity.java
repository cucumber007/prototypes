package com.polyana.cucumber007.copypaste.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.cucumber007.reusables.listeners.LoadingListener;
import com.cucumber007.reusables.logging.LogUtil;
import com.cucumber007.reusables.models.FacebookModel;
import com.cucumber007.reusables.models.UserModel;
import com.cucumber007.reusables.network.RequestManager;
import com.cucumber007.reusables.objects.LoginParams;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.polyana.cucumber007.copypaste.BuildConfig;
import com.polyana.cucumber007.copypaste.R;

import rx.Observable;


public abstract class AbstractLoginActivity extends AppCompatActivity implements LoadingListener {

    private static final int REQUEST_CODE_GOOGLE_LOGIN = 7002;

    protected Context context = this;

    private GoogleApiClient mGoogleApiClient;
    //private TwitterAuthClient twitterAuthClient;

    protected abstract void nextActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGoogle();
        initTwitter();
    }

    private void initTwitter() {
        //twitterAuthClient = new TwitterAuthClient();
    }

    private void initGoogle() {
        String clientId = /*getString(R.string.google_api_client_id);*/"lol";

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestServerAuthCode(clientId)
                .requestIdToken(clientId)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionFailResult -> {

                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (UserModel.getInstance().isLoggedIn()) nextActivity();
    }

    protected void loginByTwitter() {
        /*twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(final Result<TwitterSession> result) {
                final TwitterSession session = result.data;

                TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
                TwitterAuthToken authToken = session.getAuthToken();

                OAuthSigning oauthSigning = new OAuthSigning(authConfig, authToken);

                Map<String, String> authHeaders = oauthSigning.getOAuthEchoHeadersForVerifyCredentials();

                UserModel.getInstance().getTwitterLoginObservable(
                        new LoginParams(session.getAuthToken().token,
                                "{mail}",
                                "{name}",
                                ""))
                        .onErrorResumeNext((throwable -> {
                            RequestManager.handleError(throwable);
                            finish();
                            return Observable.never();
                        }))
                        .subscribe(user -> {
                            onStopLoading();
                            nextActivity();
                        });
            }



            @Override
            public void failure(final TwitterException e) {
                // Do something on fail
            }
        });*/
    }

    protected void loginByFacebook() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected;
        if (activeNetwork == null) isConnected = false;
        else isConnected = activeNetwork.isConnected();

        if (isConnected) {
            UserModel.getInstance().logout();
            FacebookModel.getInstance().login(this, new FacebookModel.LoginCallback() {
                @Override
                public void login(String token) {
                    onStartLoading();
                    UserModel.getInstance().getLoginObservable(
                            new LoginParams(token,
                                    FacebookModel.getInstance().getProfileEmail(),
                                    FacebookModel.getInstance().getProfileName(),
                                    FacebookModel.getInstance().getProfileImageUrl()))
                            .onErrorResumeNext((throwable -> {
                                RequestManager.handleError(throwable);
                                finish();
                                return Observable.never();
                            }))
                            .subscribe(user -> {
                                onStopLoading();
                                nextActivity();
                            });
                }

                @Override
                public void error(String message) {
                }
            });
        } else {
            Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
    }

    protected void loginByGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_CODE_GOOGLE_LOGIN);
    }

    protected void loginByEmail(String email, String password, boolean register) {
        /*onStartLoading();
        UserModel.getInstance().getEmailLoginObservable(
                new LoginParams(email, password), register)
                .onErrorResumeNext(throwable -> {
                    onStopLoading();
                    RequestManager.handleError(throwable);
                    return Observable.never();
                })
                .subscribe(response -> {
                    onStopLoading();

                    if (response.errorBody() == null)
                        nextActivity();
                    else if (response.code() == 401) {
                        if(register) LogUtil.makeToast(R.string.already_registered);
                        else LogUtil.makeToast(R.string.wrong_login_data);
                    } else if (response.code() == 400) {
                        if(register) LogUtil.makeToast(R.string.wrong_password_format);
                    } else {
                        LogUtil.makeToast(R.string.unknown_error);
                        LogUtil.logDebug(response.code()+"", response.message());
                        // todo RequestManager.handleError(response.errorBody());
                    }

                });*/
    }

    private void twitterOnResult(int requestCode, int resultCode, Intent data) {
        //if (twitterAuthClient!= null) twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookOnResult(int requestCode, int resultCode, Intent data) {
        FacebookModel.getInstance().getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    @SuppressWarnings("ConstantConditions")
    private void googleOnResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GOOGLE_LOGIN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                if(BuildConfig.DEBUG_MODE) {
                    LogUtil.logDebug(account.getIdToken());
                    LogUtil.logDebug(account.getServerAuthCode());
                }
                if (account.getServerAuthCode() != null)
                    UserModel.getInstance().getLoginObservable(
                            new LoginParams(account.getServerAuthCode(), LoginParams.SocialType.GOOGLE,
                                    account.getEmail(),
                                    account.getDisplayName(),
                                    account.getPhotoUrl().toString()))
                            .onErrorResumeNext((throwable -> {
                                RequestManager.handleError(throwable);
                                return Observable.never();
                            }))
                            .subscribe(user -> {
                                onStopLoading();
                                nextActivity();
                            });
                else LogUtil.makeToast("Token null");
            } else {
                LogUtil.makeToast("Google login failed "+result.getStatus().toString());
                LogUtil.logDebug(result.getStatus().toString());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookOnResult(requestCode, resultCode, data);
        googleOnResult(requestCode, resultCode, data);
        twitterOnResult(requestCode, resultCode, data);
    }
}
