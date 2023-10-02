package com.theroyalsoft.mydoc.apputil.baseUI;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.Fragment;

public abstract class StatefulFragment extends Fragment {

    protected BaseActivity activity;
    protected Handler uiHandler;

    private Bundle savedState;
    private boolean saved;
    private static String _FRAGMENT_STATE = "_FRAGMENT_STATE";


    @Override
    public void onSaveInstanceState(Bundle state) {
        if (getView() == null) {
            state.putBundle(_FRAGMENT_STATE, savedState);
        } else {
            Bundle bundle = saved ? savedState : getStateToSave();

            state.putBundle(_FRAGMENT_STATE, bundle);
        }

        saved = false;

        super.onSaveInstanceState(state);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (BaseActivity) context;
        this.uiHandler = ((BaseActivity) context).uiHandler;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        if (state != null) {
            savedState = state.getBundle(_FRAGMENT_STATE);
        }
    }

    @Override
    public void onDestroyView() {
        savedState = getStateToSave();
        saved = true;

        super.onDestroyView();
    }


    protected void setTagName(String fragmentStateName) {
        this._FRAGMENT_STATE = fragmentStateName;
    }

    protected Bundle getSavedState() {
        return savedState;
    }

    protected abstract boolean hasSavedState();

    protected abstract Bundle getStateToSave();

}
