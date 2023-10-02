package com.theroyalsoft.telefarmer.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class NullRemoveUtil {

    public static String getNotNull(String data) {
        return data == null ? "" : data;
    }

    public static int getNotNull(int data) {
        return data;
    }

    public static long getNotNull(long data) {
        return data;
    }

    public static Date getNotNull(Date data) {
        return data == null ? new Date() : data;
    }


    public static <T> List<T> getNotNull(List<T> data) {
        return data == null ? new ArrayList<>() : data;
    }
}
