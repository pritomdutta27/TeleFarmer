package com.theroyalsoft.mydoc.apputil.internet;

public class InternetConnectionConfig {

    public static IConnectionStatus iConnectionStatus;

    public interface IConnectionStatus {
        void updateStatus(boolean haveConnection);
    }

}
