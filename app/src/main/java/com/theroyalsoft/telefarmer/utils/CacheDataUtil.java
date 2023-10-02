package com.theroyalsoft.telefarmer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class CacheDataUtil {
    private static SharedPreferences preferences;

    //==============================================================================================
    public static void init(Context context) {
        init(context, "CASH_DATA_APP_SP");
    }

    public static void init(Context context, String preferencesName) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);
        }
    }


    //==============================================================================================
    public static boolean write(String key, String value) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return false;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    //==============================================================================================
    public static boolean write(String key, boolean value) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return false;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    //==============================================================================================
    public static boolean write(String key, long value) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return false;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    //==============================================================================================
    public static boolean writeInt(String key, int value) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return false;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    //==============================================================================================
    public static String read(String key) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return "";
        }

        return preferences.getString(key, "");
    }

    //==============================================================================================
    public static int readInt(String key) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return 0;
        }

        return preferences.getInt(key, 0);
    }

    //==============================================================================================
    public static long readLong(String key) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return 0;
        }
        return preferences.getLong(key, 0);
    }


    //==============================================================================================
    public static boolean readBoolean(String key) {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return false;
        }
        return preferences.getBoolean(key, false);
    }


    public static void clearPrefData() {
        if (ContextUtil.isSharedPreferencesNull(preferences)) {
            return;
        }
        preferences.edit().clear().apply();
    }
}
