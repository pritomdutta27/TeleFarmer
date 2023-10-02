package com.theroyalsoft.mydoc.apputil.baseUI;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import androidx.fragment.app.Fragment;

import com.skh.hkhr.util.thread.AppHandler;


public abstract class BaseFragment extends Fragment {

    protected Activity activity;
    private Handler uiHandler;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;

    }

    public Handler getUiHandler() {
        if (uiHandler == null) {
            this.uiHandler = AppHandler.getUiHandlerNew();
        }
        return uiHandler;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (uiHandler == null) return;
        AppHandler.destroyHandler(uiHandler);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (uiHandler == null) return;
        AppHandler.destroyHandler(uiHandler);
    }
}
