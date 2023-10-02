package com.theroyalsoft.mydoc.apputil.internet;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class NetworkChangeReceiver extends BroadcastReceiver {

    public boolean isConnected = true;
    private String status;
    private IInternetConnectionListener iInternetConnectionListener;

    public NetworkChangeReceiver() {
        super();
    }

    public NetworkChangeReceiver(IInternetConnectionListener iInternetConnectionListener) {
        this.iInternetConnectionListener = iInternetConnectionListener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {


        if (iInternetConnectionListener == null) return;

        if (NetworkUtils.isConnected()) {
            iInternetConnectionListener.haveInternetConnection(true);
        } else {
            iInternetConnectionListener.haveInternetConnection(false);
        }

    }


    public boolean is_connected() {
        return isConnected;
    }


    public interface IInternetConnectionListener {
        void haveInternetConnection(boolean haveConnection);

    }


}