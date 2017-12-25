package com.polyana.cucumber007.copypaste.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.cucumber007.reusables.logging.HttpLogUtil;
import com.cucumber007.reusables.logging.LogUtil;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Deprecated
public class RequestManager {

    public static final String SERVER_URL = "http://aaa/";
    public static final String TEST_SERVER_URL = "http://aaa/";
    private boolean testServer = false;

    private static RequestManager instance = new RequestManager();

    private static Context context;
    private static RetrofitService service;
    private static String cookie;

    //todo save cookie - DI?
    //todo to copypaste?
    public static final String KEY_COOKIE = "cookie";

    private RequestManager() {
        //context = ContextApplication.getContext();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.interceptors().add(HttpLogUtil.getHttpInterceptor());

        httpClient.interceptors().add((chain) -> {
                Request original = chain.request();

                if (cookie == null) cookie = "";
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Cookie", cookie)
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(testServer ? TEST_SERVER_URL : SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        service = retrofit.create(RetrofitService.class);

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

    public static  <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void handleError(Throwable t) {
        if (t instanceof HttpException) {
            HttpException httpException = (HttpException) t;
            try {
                switch (httpException.code()) {
                    case 401:
                        //UserModel.getInstance().logout();
                        break;
                    default:
                        LogUtil.logDebug("SERVER: " + httpException.message() + " // "+t.getMessage() + " " + httpException.response().errorBody().string());
                        LogUtil.makeToastWithDebug("Unknown server error", "SERVER: " + httpException.message() + " // "+t.getMessage() + " " + httpException.response().errorBody().string());
                        break;
                }

            } catch (IOException e) {
                LogUtil.logDebug("SERVER: " + "unknown");
                e.printStackTrace();
            }
            return;
        }
        if (isNetworkError(t)) {
            LogUtil.makeToast("No internet connection");
            LogUtil.logDebug("No internet connection");
            return;
        }
        LogUtil.logDebug(t.getClass()+" "+t.getMessage());
    }

    public static boolean isNetworkError(Throwable t) {
        return t instanceof SocketTimeoutException
                ||
                t instanceof UnknownHostException
                ||
                t instanceof ConnectException
                ||
                t instanceof NoRouteToHostException
                ||
                (t instanceof RuntimeException && t.getMessage().contains("Looper.prepare()"));
    }

    public static RetrofitService getService() {
        return service;
    }

    public static void setCookie(String cookie) {
        RequestManager.cookie = cookie;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
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
        cookie = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_COOKIE, null);
        return getCookie();
    }

    public static void logout() {
        cookie = null;
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(KEY_COOKIE).commit();
    }

    public static String responseToText(Response resp) {
        try {
            return resp.body() != null ? resp.body().toString() : resp.errorBody().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "IOError";
        }
    }


}
