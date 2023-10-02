package com.theroyalsoft.mydoc.apputil.internet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import timber.log.Timber;


public class ConnectionListener {

    private static MutableLiveData<Boolean> connectionData = new MutableLiveData<>();

    public static LiveData<Boolean> geConnection() {
        if (connectionData.getValue() == null) {
            connectionData.setValue(NetworkUtils.isConnected());
        }
        return connectionData;
    }

    public static void setConnection(boolean isAvailable) {
        Timber.e("setConnection:" + isAvailable);
        if (connectionData != null) {
            connectionData.setValue(isAvailable);
        } else {
            Timber.e("connectionData null");
        }
    }

}
