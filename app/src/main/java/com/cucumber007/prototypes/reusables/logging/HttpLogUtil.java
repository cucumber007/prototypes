package com.cucumber007.prototypes.reusables.logging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogUtil {

    public static HttpLoggingInterceptor getFacebookHttpInterceptor() {
        return getHttpInterceptor("fbhttp", HttpLoggingInterceptor.Level.BASIC);
    }

    public static HttpLoggingInterceptor getHttpInterceptor() {
        return getHttpInterceptor("http", HttpLoggingInterceptor.Level.BODY);
    }

    public static HttpLoggingInterceptor getHttpInterceptor(String tag, HttpLoggingInterceptor.Level level) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            try {
                LogUtil.logcat(tag, "" + new JSONObject(message).toString(4));
            } catch (JSONException e) {
                try {
                    LogUtil.logcat(tag, "" + new JSONArray(message).toString(4));
                } catch (JSONException e1) {
                    //e1.printStackTrace();
                    LogUtil.logcat(tag, "" + message);
                }
            }
        });
        logging.setLevel(level);
        return logging;
    }

}
