package com.theroyalsoft.mydoc.apputil.log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import timber.log.Timber;

/**
 * Created by Samiran Kumar on 12,October,2022
 **/
public class BeautifulJson {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

    public static String beautiful(String unformattedJSON) {
        if (unformattedJSON.isEmpty()) {
            return "";
        }

        String perfectJSON = unformattedJSON;
        try {
            perfectJSON = gson.toJson(JsonParser.parseString(unformattedJSON));
        } catch (Exception e) {
            Timber.e("error:" + e.toString());
        }
        return perfectJSON;
    }
}
