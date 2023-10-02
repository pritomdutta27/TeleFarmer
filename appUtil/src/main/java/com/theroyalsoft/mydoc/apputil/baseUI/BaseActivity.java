package com.theroyalsoft.mydoc.apputil.baseUI;

import android.app.Activity;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils;
import com.skh.hkhr.util.thread.AppHandler;

import com.theroyalsoft.mydoc.apputil.internet.InternetConnectionConfigUi;

import timber.log.Timber;


public abstract class BaseActivity extends AppCompatActivity {


    protected Activity activity;
    protected Handler uiHandler;

    private boolean isFirstTime = true;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
        uiHandler = AppHandler.getActivityHandler();

        initConnectionStatus();

    }

    private void initConnectionStatus() {
        InternetConnectionConfigUi.iConnectionStatus = iConnectionStatus;
        onConnection(NetworkUtils.isConnected());
    }

    public void removeListener() {
        Timber.d("Call removeListener");
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isFirstTime) {
            isFirstTime = false;
            return;
        }

        initConnectionStatus();
    }


    @Override
    protected void onPause() {
        super.onPause();
        removeListener();
    }

    @Override
    protected void onDestroy() {
        AppHandler.destroyActivityHandler();
        super.onDestroy();
    }

    public abstract void onConnection(boolean isAvailable);

    private InternetConnectionConfigUi.IConnectionStatus iConnectionStatus = haveConnection -> {
        Timber.e("haveConnection:" + haveConnection);
        onConnection(haveConnection);
    };

}
