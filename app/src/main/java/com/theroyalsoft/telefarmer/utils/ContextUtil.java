package com.theroyalsoft.telefarmer.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class ContextUtil {

    //==============================================================================================
    public static boolean isContextNull(Context context, String message) {
        if (context == null) {
            System.out.println(message);
            return true;
        }
        return false;
    }


    public static boolean isSharedPreferencesNull(SharedPreferences preferences) {
        if (preferences == null) {
            System.out.println("Provide Context! Call CacheDataUtil.init(context);");
            return true;
        }
        return false;
    }

}

