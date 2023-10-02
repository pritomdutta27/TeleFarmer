package com.theroyalsoft.telefarmer.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theroyalsoft.mydoc.apputil.log.LogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class JsonUtil {

    static {
        LogUtil.initializeLog("skh");
    }

    public static String getJsonStringFromObject(Object type) {
        Gson gson = new Gson();
        return gson.toJson(type);
    }


    public static <T> List<T> getModelListFromStringJson(String json, Class<T> myType) {

        try {
            Gson gson = new Gson();
            Type collectionType = TypeToken.getParameterized(List.class, myType).getType();
            return gson.fromJson(json, collectionType);
        } catch (Exception e) {

            Timber.e("Error:" + json);
            Timber.e("Error:" + e.toString());
        }

        return new ArrayList<>();
    }


    public static <T> T getModelFromStringJson(String json, Class<T> type) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            Timber.e("Error:" + e.toString());
        }

        return null;
    }

}
