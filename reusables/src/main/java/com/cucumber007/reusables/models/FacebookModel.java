package com.cucumber007.reusables.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.cucumber007.reusables.R;
import com.cucumber007.reusables.utils.logging.LogUtil;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.HttpMethod;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;

import java.util.Arrays;


public class FacebookModel {

    private static FacebookModel instance;
    private CallbackManager callbackManager;
    private Context context;

    private String profileImageUrl;
    private String profileName;
    private String profileEmail;

    private boolean pictureLoaded = false;
    private boolean infoLoaded = false;

    public static final String ERROR = "Unknown error";
    public static final String KEY_PROFILE_IMAGE = "profile_image";
    public static final String KEY_PROFILE_NAME = "profile_name";
    public static final String KEY_PROFILE_EMAIL = "profile_email";

    public FacebookModel(Context context) {
        this.context = context;
        callbackManager = CallbackManager.Factory.create();
    }

    public static FacebookModel getInstance(Context context) {
        if (instance == null) {
            instance = new FacebookModel(context.getApplicationContext());
        }
        return instance;
    }

    public void login(Activity activity, LoginCallback loginCallback) {
        LoginManager.getInstance().setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Bundle params = new Bundle();
                params.putBoolean("redirect", false);
                params.putString("type", "large");
                params.putInt("widht", 200);
                params.putInt("height", 200);
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/picture",
                        params,
                        HttpMethod.GET,
                        response -> {
                            try {
                                setProfileImageUrl(response.getJSONObject().getJSONObject("data").getString("url"));
                            } catch (JSONException e) {
                                Toast.makeText(context, context.getResources().getString(R.string.facebook_login_error), Toast.LENGTH_SHORT).show();
                                LogUtil.logError(e);
                                loginCallback.error(e.getMessage());
                                e.printStackTrace();
                            }
                            pictureLoaded = true;
                            if(pictureLoaded && infoLoaded)loginCallback.login(loginResult.getAccessToken().getToken());
                        }
                ).executeAsync();

                Bundle bundle = new Bundle();
                bundle.putString("fields", "name,email");

                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/me/",
                        bundle,
                        HttpMethod.GET,
                        response -> {
                            try {
                                if(response.getJSONObject().has("name"))
                                    setProfileName(response.getJSONObject().getString("name"));
                                else
                                    setProfileName("");
                                if(response.getJSONObject().has("email"))
                                    setProfileEmail(response.getJSONObject().getString("email"));
                                else
                                    setProfileEmail("");
                            } catch (JSONException e) {
                                Toast.makeText(context, context.getResources().getString(R.string.facebook_login_error), Toast.LENGTH_SHORT).show();
                                LogUtil.logError(e);
                                loginCallback.error(e.getMessage());
                                e.printStackTrace();
                            }
                            infoLoaded = true;
                            if(pictureLoaded && infoLoaded)loginCallback.login(loginResult.getAccessToken().getToken());
                        }
                ).executeAsync();
            }


            @Override
            public void onCancel() {
                Toast.makeText(context, context.getResources().getString(R.string.facebook_login_cancelled), Toast.LENGTH_SHORT).show();
                loginCallback.error(context.getResources().getString(R.string.facebook_login_cancelled));
            }


            @Override
            public void onError(FacebookException error) {
                LogUtil.logError(error, error.getMessage());
                LogUtil.logDebug(error.toString());
                if(error.getMessage().equals("CONNECTION_FAILURE: CONNECTION_FAILURE"))
                    Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, context.getResources().getString(R.string.facebook_login_error), Toast.LENGTH_SHORT).show();
                loginCallback.error(error.toString());
            }
        });

        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("email"));
    }

    public void logout() {
        LoginManager.getInstance().logOut();
    }


    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public String getProfileImageUrl() {
        if (profileImageUrl == null) {
            profileImageUrl = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PROFILE_IMAGE, null);
        }
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_PROFILE_IMAGE, profileImageUrl).apply();
    }

    public String getProfileName() {
        if (profileName == null) profileName = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PROFILE_NAME, null);
        if (profileName == null) return "";
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_PROFILE_NAME, profileName).apply();
    }

    public String getProfileEmail() {
        if (profileEmail == null) profileEmail = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_PROFILE_EMAIL, null);
        if (profileEmail == null) return "";
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_PROFILE_EMAIL, profileName).apply();
    }

    public interface LoginCallback {
        void login(String token);
        void error(String message);
    }

}
