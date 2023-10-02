package com.theroyalsoft.mydoc.apputil.internet;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import timber.log.Timber;

public class NetworkUtils {


    private static Context context;

    public static void init(Context context) {
        Timber.e("NetworkUtils init");
        context.registerReceiver(new NetworkChangeReceiver(iInternetConnectionListener), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        NetworkUtils.context = context;
    }


    private static NetworkChangeReceiver.IInternetConnectionListener iInternetConnectionListener = haveConnection -> {
        Timber.d("haveConnection:" + haveConnection);
        ConnectionListener.setConnection(haveConnection);

        if (InternetConnectionConfig.iConnectionStatus != null) {
            InternetConnectionConfig.iConnectionStatus.updateStatus(haveConnection);
        }

        if (InternetConnectionConfigUi.iConnectionStatus != null) {
            InternetConnectionConfigUi.iConnectionStatus.updateStatus(haveConnection);
        } else {
            Timber.e("InternetConnectionConfigUi.iConnectionStatus == null");
        }
    };


    //===========================================================================
    public static Boolean isConnected() {
        if (context == null) {
            Timber.e("isConnected :: context == null");
            return false;
        }
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean result = false;
        if (activeNetwork != null) {
            result = activeNetwork.isConnectedOrConnecting();
        }
        return result;
    }

}
