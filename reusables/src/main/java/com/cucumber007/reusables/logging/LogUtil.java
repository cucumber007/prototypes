package com.cucumber007.reusables.logging;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.List;


public class LogUtil {

    private static boolean debugMode = false;
    private static Context context;
    private static CrashlyticsListener crashlyticsListener;

    public static void setContext(Context context) {
        LogUtil.context = context;
    }

    public static void setDebugMode(boolean debugMode) {
        LogUtil.debugMode = debugMode;
    }

    public static void setCrashlyticsListener(CrashlyticsListener crashlyticsListener) {
        LogUtil.crashlyticsListener = crashlyticsListener;
    }

    public static final String KEY_LOG = "logutil_log";
    private static String TAG = "cutag";

    public static void logError(Throwable throwable) {
        if(!debugMode && crashlyticsListener != null) crashlyticsListener.logException(throwable);
        throwable.printStackTrace();
        logcat(throwable.getMessage());
    }

    public static void logError(Throwable throwable, String message) {
        if (!debugMode && crashlyticsListener != null) {
            crashlyticsListener.log(message);
            crashlyticsListener.logException(throwable);
        }
        throwable.printStackTrace();
        logcat(throwable.getMessage());
    }

    @SafeVarargs
    public static <T> void logDebug(Character delim, T... vars) {
        String log = "";
        for (int i = 0; i < vars.length; i++) {
            log += vars[i].toString() + delim;
        }
        logcat(log);
    }

    @SafeVarargs
    public static <T> void logDebug(String title, T... vars) {
        String log = title+ " : ";
        for (int i = 0; i < vars.length; i++) {
            if(vars[i] == null) log += "Null" + " ";
            else log += vars[i].toString() + " ";
        }
        logcat(log);
    }

    @SafeVarargs
    public static <T> void logDebug(boolean condition, String title, T... vars) {
        if (condition) {
            String log = title + " : ";
            for (int i = 0; i < vars.length; i++) {
                log += vars[i].toString() + " ";
            }
            logcat(log);
        }
    }


    public static void logList(@Nullable String name, List<Object> list) {
        StringBuilder builder = new StringBuilder();
        if(name != null) builder.append(name);
        builder.append("\n");
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i));
            builder.append("\n");
        }
        logcat(builder.toString());
    }

    public static void logList(List<Object> list) {
        logList(null, list);
    }

    public static void logcat(String message) {
        if(debugMode) Log.d(TAG, "==== "+message);
    }

    public static void logcat(String tag, String message) {
        if(debugMode) Log.d(tag, message);
    }

    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(String text) {
        makeToast(context, text);
    }

    public static void makeToast(Context context, int stringId) {
        if (context == null) throw new NullPointerException("Context == null. Try to use LogUtil.setContext()");
        else Toast.makeText(context, context.getResources().getString(stringId), Toast.LENGTH_SHORT).show();
    }

    public static void makeToast(int stringId) {
        makeToast(context, stringId);
    }

    public static void makeToastWithDebug(String text, String debugText) {
        if(!debugMode) makeToast(text);
        else makeToast(debugText);
    }

    public static void makeToastWithDebug(int stringId, String debugText) {
        if(!debugMode) makeToast(stringId);
        else makeToast(debugText);
    }

    public interface CrashlyticsListener {
        void logException(Throwable throwable);
        void log(String throwable);
    }

    public static void logToPreferences(Context context, String text) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String logLine = preferences.getString(KEY_LOG, "") + text + "\n";
        preferences.edit().putString(KEY_LOG, logLine).apply();
    }

    public static void logToPreferences(String text) {
        logToPreferences(context, text);
    }

    public static String getPreferencesLogs(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_LOG, "");
    }


}
