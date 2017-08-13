package com.cucumber007.prototypes.activities.giver;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;

import com.cucumber007.prototypes.BuildConfig;
import com.cucumber007.prototypes.activities._ui.activity_templates.LoginActivity;
import com.cucumber007.prototypes.reusables.logging.HttpLogUtil;
import com.cucumber007.prototypes.reusables.logging.LogUtil;
import com.cucumber007.prototypes.reusables.network.RetrofitService;
import com.cucumber007.prototypes.reusables.ContextApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RequestManager {

    private static String apiVersion = "2.2";
    public static String TEST_SERVER_URL = "https://dev.tseh20.com/giver/api/v"+apiVersion+"/client/";
    //todo PROD
    //public static final String SERVER_URL = "https://thegiver.tseh20.com/api/v"+apiVersion+"/client/";
    public static final String SERVER_URL = TEST_SERVER_URL;

//    private static boolean isTestServer = BuildConfig.DEBUG_MODE;
    private static boolean isTestServer = false;

    private static RequestManager instance = new RequestManager();

    private static RetrofitService service;
    private static String cookie;
    public static final String KEY_COOKIE = "cookie";

    public static final int ERROR_CODE_SUCCESS = 0;
    public static final int ERROR_CODE_NOT_LOGGED = 8;

    /*@Inject*/ AbstractFacebookModel facebookModel;
    //@Inject static AbstractUserModel userModel;

    private RequestManager() {
        //GiverApplication.getAppComponent().inject(this);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(HttpLogUtil.getHttpInterceptor());

        httpClient.interceptors().add((chain) -> {
            try {
                Request original = chain.request();

                if (cookie == null) cookie = "";
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Cookie", cookie)
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            } catch (SocketTimeoutException e) {
                LogUtil.makeToast("Connection error");
                e.printStackTrace();
                return null;
            }
        });

        if (isTestServer) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(TEST_SERVER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            service = retrofit.create(RetrofitService.class);
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            service = retrofit.create(RetrofitService.class);
        }

        OkHttpClient.Builder facebookHttpClient = new OkHttpClient.Builder();
        if(BuildConfig.DEBUG_MODE) facebookHttpClient.interceptors().add(HttpLogUtil.getFacebookHttpInterceptor());

        facebookHttpClient.interceptors().add((chain) -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "OAuth " + facebookModel.getFacebookToken())
                    .method(original.method(), original.body());
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        loadAndKeepCookie();
    }


    public static  <T> Observable.Transformer<T, T> applySchedulersAndHandleErrors() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext((throwable -> {
                    handleError(throwable);
                    return Observable.never();
                }));
    }

    public static  <T> Observable.Transformer<T, T> handleNetworkErrorsWithActivity(Context context) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext((throwable -> {
                    openConnectionErrorAlert(context);
                    return Observable.never();
                }));
    }

    public static void openConnectionErrorAlert(Context context) {
        //context.startActivity(new Intent(context, ConnectionAlertActivity.class));
    }

    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void handleError(Throwable t) {
        handleError(t, null);
    }

    public static void handleError(Throwable t, Activity activity) {
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;

            try {
                switch (httpException.code()) {
                    case 400:
                        LogUtil.logError(t, httpException.response().raw().message());
                        LogUtil.logDebug(httpException.response().raw().message());
                        LogUtil.makeToastWithDebug("Server error", "400, "+httpException.response().raw().message());
                        try {
                            int code =(new JSONObject(httpException.response().errorBody().string()).getInt("code"));
                            if (code == ERROR_CODE_NOT_LOGGED) {
                                /*UserModel.getInstance().logout();
                                //todo no loginWithReadPermissions activity
                                Intent intent = new Intent(ContextApplication.getContext(), LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                ContextApplication.getContext().startActivity(intent);*/
                            }
                        } catch (JSONException e) {
                            LogUtil.logError(e);
                            e.printStackTrace();
                        }
                        break;
                    case 401:
                        //UserModel.getInstance().logout();
                        LogUtil.logDebug(httpException.response().raw().message());
                        //todo no loginWithReadPermissions activity
                        Intent intent = new Intent(ContextApplication.getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
                        ContextApplication.getContext().startActivity(intent);
                        break;
                    default:
                        LogUtil.logDebug("SERVER: " + httpException.message() + " // "+t.getMessage() + " " + httpException.response().errorBody().string());
                        LogUtil.makeToastWithDebug("Server error", "SERVER: " + httpException.message() + " // "+t.getMessage() + " " + httpException.response().errorBody().string());
                        break;
                }
                return;
            } catch (IOException e) {
                LogUtil.makeToast("Server error");
                LogUtil.logDebug("Unknown server error");
                return;
            }
        }
        if(isNetworkError(t)) {
            if (activity != null) openConnectionErrorAlert(activity);
            else {
                LogUtil.makeToast("No internet connection");
                LogUtil.logDebug("No internet connection");
            }
            return;
        }
        if (BuildConfig.DEBUG_MODE) LogUtil.makeToast(t.getMessage());
        else LogUtil.makeToast("Unknown error");
        LogUtil.logDebug(t.getClass() + " " + t.getMessage());
    }

    public static boolean isNetworkError(Throwable t) {
        return t instanceof SocketTimeoutException || t instanceof UnknownHostException || t instanceof ConnectException || (t instanceof RuntimeException && t.getMessage().contains("Looper.prepare()")) ;
    }

    public static RetrofitService getService() {
        return service;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) ContextApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null) return false;
        else return activeNetwork.isConnected();
    }

    public static void setCookie(String cookie) {
        RequestManager.cookie = cookie;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).edit();
        editor.putString(KEY_COOKIE, cookie);
        editor.apply();
    }

    public static String getCookie() {
        if(cookie != null && cookie.length() == 0) return null;
        return cookie;
    }

    public static boolean isLoggedIn() {
        if(getCookie() != null) return true;
        else if (loadAndKeepCookie() != null) return true;
        else return false;
    }

    public static String loadAndKeepCookie() {
        cookie = PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext())
                .getString(KEY_COOKIE, null);
        return getCookie();
    }

    public static void logout() {
        cookie = null;
        PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).edit().remove(KEY_COOKIE).commit();
        //PreferenceManager.getDefaultSharedPreferences(ContextApplication.getContext()).edit().remove(TutorialActivity.KEY_TUTORIAL_STATE).commit();
    }

    public static void setIsTestServer(boolean isTestServer) {
        RequestManager.isTestServer = isTestServer;
        instance = new RequestManager();
    }

    public static boolean isTestServer() {
        return isTestServer;
    }
}
