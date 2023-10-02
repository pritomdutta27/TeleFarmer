package com.theroyalsoft.mydoc.apputil;

import android.content.Context;

import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils;
import com.skh.hkhr.util.CacheDataUtil;
import com.skh.hkhr.util.log.ToastUtil;

import com.theroyalsoft.mydoc.apputil.log.LogUtil;

public class AppUtilConfig {
    private static Context context;

    public static void init(Context context,String logName,String localCacheName) {
        AppUtilConfig.context = context;

        ToastUtil.init(context); // For Use >>LIBRARY
        LogUtil.initializeLog(logName);
        NetworkUtils.init(context);
        CacheDataUtil.init(context, localCacheName);

    }

    public static Context getAppContext() {
        return context;
    }

}
