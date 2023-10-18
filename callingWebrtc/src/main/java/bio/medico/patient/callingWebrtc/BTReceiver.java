package bio.medico.patient.callingWebrtc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class BTReceiver extends BroadcastReceiver {
    public IChangeBt iChangeBt;

    public BTReceiver() {
    }

    public BTReceiver(IChangeBt iChangeBt) {
        this.iChangeBt = iChangeBt;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.e("onReceive:" + intent.getAction());
        if (iChangeBt != null) {
            iChangeBt.onChange(intent);
        }
    }

    public interface IChangeBt {
        void onChange(Intent intent);
    }
}
