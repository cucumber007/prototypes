package com.cucumber007.reusables.logging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.logging.HttpLoggingInterceptor;

public class HttpLogUtil {
    private static boolean debugMode;

    public static void setDebugMode(boolean debugMode) {
        HttpLogUtil.debugMode = debugMode;
    }

    public static HttpLoggingInterceptor getFacebookHttpInterceptor() {
        return getHttpInterceptor("fbhttp", HttpLoggingInterceptor.Level.BASIC);
    }

    public static HttpLoggingInterceptor getHttpInterceptor() {
        return getHttpInterceptor("cuhttp", HttpLoggingInterceptor.Level.BODY);
    }

    public static HttpLoggingInterceptor getHttpInterceptor(String tag, HttpLoggingInterceptor.Level level) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> {
            if(debugMode) {
                try {
                    LogUtil.logcat(tag, "" + new JSONObject(message).toString(4));
                } catch (JSONException e) {
                    try {
                        LogUtil.logcat(tag, "" + new JSONArray(message).toString(4));
                    } catch (JSONException e1) {
                        //e1.printStackTrace();
                        LogUtil.logcat(tag, "\t" + message);
                    }
                }
            }
        });
        logging.setLevel(level);
        return logging;
    }

}
