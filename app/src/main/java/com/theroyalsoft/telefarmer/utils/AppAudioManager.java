package com.theroyalsoft.telefarmer.utils;

import android.app.Activity;
import android.bluetooth.BluetoothHeadset;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theroyalsoft.telefarmer.R;
import com.theroyalsoft.telefarmer.TeleFarmerApp;

import java.util.Objects;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class AppAudioManager {

    public static final String HEADPHONE_PLUG_IN = "HEADPHONE_PLUG_IN";
    public static final String HEADPHONE_PLUG_OUT = "HEADPHONE_PLUG_OUT";

    public static final String HEADPHONE_BT_CONNECT = "HEADPHONE_BT_CONNECT";
    public static final String HEADPHONE_BT_DISCONNECT = "HEADPHONE_BT_DISCONNECT";


    public static final String SOUND_IN_PHONE = "SOUND_IN_PHONE";
    public static final String SOUND_IN_LOUDSPEAKER = "SOUND_IN_LOUDSPEAKER";
    public static final String SOUND_IN_HEADPHONE = "SOUND_IN_HEADPHONE";
    public static final String SOUND_IN_BT = "SOUND_IN_BT";

    //==================================================
    private String headphoneBTConnectStatus = "";
    private String headphonePlugStatus = "";
    private String soundInStatus = "";
    private String soundInStatusBeforePlug = "";

    private boolean isVideoCall = false;
    private Activity activity;
    private AudioManager audioManager;
    private FloatingActionButton fbLoudSpeaker;

    public AppAudioManager(boolean isVideoCall, Activity activity, FloatingActionButton fbLoudSpeake) {
        this.isVideoCall = isVideoCall;
        this.activity = activity;
        this.fbLoudSpeaker = fbLoudSpeake;
        audioManager = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);

        detectedHeadPhonePlug();

        setLoudSpeakerOnOff(isVideoCall);
        speakerOnOff();
        // WebRtcAudioUtils.setWebRtcBasedAcousticEchoCanceler(false);



     /*   BTReceiver broadcastReceiver = new BTReceiver(new BTReceiver.IChangeBt() {
            @Override
            public void onChange(Intent intent) {
                deee(intent);
            }
        });

        IntentFilter receiverFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        MedicoApp.getAppContext().registerReceiver(broadcastReceiver, receiverFilter);
*/
    }


    public void setLoudSpeakerOnOff() {
        setLoudSpeakerOnOff(!isLoudSpeakerEnable());
    }

    private void setLoudSpeakerOnOff(boolean isLoudSpeaker) {
        if (isLoudSpeaker) {
            soundInStatus = SOUND_IN_LOUDSPEAKER;
        } else {
            soundInStatus = SOUND_IN_PHONE;
        }

        speakerOnOff();
    }


    public boolean isLoudSpeakerEnable() {
        return Objects.equals(soundInStatus, SOUND_IN_LOUDSPEAKER);
    }


    private void pluginStateChange() {
        switch (headphonePlugStatus) {
            case HEADPHONE_PLUG_IN:
                soundInStatusBeforePlug = soundInStatus;
                soundInStatus = SOUND_IN_HEADPHONE;
                break;

            case HEADPHONE_BT_CONNECT:
                soundInStatusBeforePlug = soundInStatus;
                soundInStatus = SOUND_IN_BT;
                break;

            case HEADPHONE_BT_DISCONNECT:
            case HEADPHONE_PLUG_OUT:
            default:
                soundInStatus = soundInStatusBeforePlug;
                break;
        }

        speakerOnOff();
    }


    private void speakerOnOff() {

        Timber.e("speakerOnOff:" + soundInStatus);

        if (audioManager == null) {
            return;
        }

        switch (soundInStatus) {
            case SOUND_IN_PHONE:
                // audioManager.setMode(AudioManager.MODE_NORMAL);//reset for BT connected devices
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                audioManager.setSpeakerphoneOn(false);
                soundInStatusBeforePlug = soundInStatus;
                fbLoudSpeaker.setImageDrawable(TeleFarmerApp.getAppContext().getDrawable(R.drawable._volume_off));

                break;

            case SOUND_IN_HEADPHONE:
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                audioManager.stopBluetoothSco();
                audioManager.setBluetoothScoOn(false);

                audioManager.setSpeakerphoneOn(false);
                fbLoudSpeaker.setImageDrawable(TeleFarmerApp.getAppContext().getDrawable(R.drawable._volume_off));

                break;

            case SOUND_IN_BT:

                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);

                fbLoudSpeaker.setImageDrawable(TeleFarmerApp.getAppContext().getDrawable(R.drawable._volume_off));

                break;

            case SOUND_IN_LOUDSPEAKER:
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);

                audioManager.stopBluetoothSco();
                audioManager.setBluetoothScoOn(false);

                audioManager.setSpeakerphoneOn(true);
                soundInStatusBeforePlug = soundInStatus;

                fbLoudSpeaker.setImageDrawable(TeleFarmerApp.getAppContext().getDrawable(R.drawable._volume_up));

                break;
        }

    }

    //==============================================
    private void detectedHeadPhonePlug() {

        headphonePlugStatus = HEADPHONE_PLUG_OUT;

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                final String action = intent.getAction();

                if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
                    int pluginState = intent.getIntExtra("state", -1);

                    switch (pluginState) {

                        case 1:
                            headphonePlugStatus = HEADPHONE_PLUG_IN;
                            Timber.d("Detected: Microphone Plugin!");
                            break;

                        case 0:
                            headphonePlugStatus = HEADPHONE_PLUG_OUT;
                            Timber.d("Detected: Not Microphone Plugin!");
                            break;

                        default:
                            headphonePlugStatus = HEADPHONE_PLUG_OUT;
                            Timber.d("Detected: Not Microphone Plugin !");
                            break;
                    }

                    pluginStateChange();
                }
            }
        };

        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        TeleFarmerApp.getAppContext().registerReceiver(broadcastReceiver, receiverFilter);
    }


    void deee(Intent intent) {
        Timber.d("action:try BT" );

        try {
            Bundle extras = intent.getExtras();
            if (extras != null) { //Do something

                String action = intent.getAction();
                Timber.d("action:" + action);
                int state;
                if (action.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                    state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_DISCONNECTED);
                    Timber.d("\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$

                    if (state == BluetoothHeadset.STATE_CONNECTED) {
                        // setModeBluetooth();
                        headphoneBTConnectStatus = HEADPHONE_BT_CONNECT;
                    } else if (state == BluetoothHeadset.STATE_DISCONNECTED) {
                        // Calling stopVoiceRecognition always returns false here
                        // as it should since the headset is no longer connected.
                        // setModeNormal();
                        headphoneBTConnectStatus = HEADPHONE_BT_DISCONNECT;

                        Timber.d("Headset disconnected"); //$NON-NLS-1$
                    }

                } else // audio
                {
                    state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_AUDIO_DISCONNECTED);
                    Timber.d("\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$
                    if (state == BluetoothHeadset.STATE_AUDIO_CONNECTED) {
                        Timber.d("\nHeadset audio connected");  //$NON-NLS-1$
                        // setModeBluetooth();
                        headphoneBTConnectStatus = HEADPHONE_BT_CONNECT;

                    } else if (state == BluetoothHeadset.STATE_AUDIO_DISCONNECTED) {
                        //  setModeNormal();
                        headphoneBTConnectStatus = HEADPHONE_BT_DISCONNECT;

                        Timber.d("Headset audio disconnected"); //$NON-NLS-1$
                    }
                }
                pluginStateChange();

            }
        } catch (Exception e) {
            Timber.e("Exception " + e.toString());
            //ApiManager.sendApiLogErrorCodeScope(e);

        }
    }


 /*   public static class BTReceiver extends BroadcastReceiver {
        int state = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            Timber.d("Received: Bluetooth");
            try {
                Bundle extras = intent.getExtras();
                if (extras != null) { //Do something

                    String action = intent.getAction();
                    Timber.d("action:" + action);
                    int state;
                    if (action.equals(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED)) {
                        state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_DISCONNECTED);
                        Timber.d("\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$

                        if (state == BluetoothHeadset.STATE_CONNECTED) {
                            setModeBluetooth();
                        } else if (state == BluetoothHeadset.STATE_DISCONNECTED) {
                            // Calling stopVoiceRecognition always returns false here
                            // as it should since the headset is no longer connected.
                            setModeNormal();

                            Timber.d("Headset disconnected"); //$NON-NLS-1$
                        }

                    } else // audio
                    {
                        state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, BluetoothHeadset.STATE_AUDIO_DISCONNECTED);
                        Timber.d("\nAction = " + action + "\nState = " + state); //$NON-NLS-1$ //$NON-NLS-2$
                        if (state == BluetoothHeadset.STATE_AUDIO_CONNECTED) {
                            Timber.d("\nHeadset audio connected");  //$NON-NLS-1$
                            setModeBluetooth();
                        } else if (state == BluetoothHeadset.STATE_AUDIO_DISCONNECTED) {
                            setModeNormal();
                            Timber.d("Headset audio disconnected"); //$NON-NLS-1$
                        }
                    }
                }
            } catch (Exception e) {
                Timber.e("Exception " + e.toString());
            }
        }
    }



https://stackoverflow.com/questions/47057889/how-to-switch-audio-output-from-phone-phone-speaker-earphones-or-bluetooth-dev
    Got it working:
          'if(false) {
            //For BT
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.startBluetoothSco();
            mAudioManager.setBluetoothScoOn(true);
        } else if(true) {
            //For phone ear piece
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            mAudioManager.stopBluetoothSco();
            mAudioManager.setBluetoothScoOn(false);
            mAudioManager.setSpeakerphoneOn(false);
        } else {
            //For phone speaker(loudspeaker)
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager.stopBluetoothSco();
            mAudioManager.setBluetoothScoOn(false);
            mAudioManager.setSpeakerphoneOn(true);
        }
*/
}
