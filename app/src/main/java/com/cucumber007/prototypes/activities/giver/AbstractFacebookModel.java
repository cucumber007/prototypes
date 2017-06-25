package com.cucumber007.prototypes.activities.giver;


import android.app.Activity;
import android.graphics.Bitmap;

public interface AbstractFacebookModel {

    void loginWithReadPermissions(Activity activity, LoginCallback loginCallback);

    void loginWithWritePermissions(Activity activity, LoginCallback loginCallback);

    void logout();

    void post(String caption, byte[] bytes, Activity activity, PostCallback callback);

    void post(String caption, byte[] bytes, Bitmap bitmap, Activity activity, PostCallback callback);

    //public CallbackManager getCallbackManager();

    String getProfileImageUrl();

    String getProfileName();

    String getProfileEmail();

    String getFacebookToken();

    interface PostCallback {
        void post(String postId, String photoId);

        void error(String message);
    }

    interface LoginCallback {
        void login(String token);

        void error(String message);
    }

}
