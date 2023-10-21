package bio.medico.patient.callingWebrtc

import android.app.Activity
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import bio.medico.patient.common.AppKey
import com.farmer.primary.network.dataSource.local.LocalData
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.theroyalsoft.mydoc.apputil.AppUtilConfig
import timber.log.Timber


/**
Created by Samiran Kumar on 26,September,2023
 **/
class CallAudioManager(
    private val isVideoCall: Boolean,
    val activity: Activity,
    private val fbLoudSpeaker: FloatingActionButton
) {

    private lateinit var welcomeToneMediaPlayer: MediaPlayer

    private val appAudioManager by lazy {
        AppAudioManager(isVideoCall, activity, fbLoudSpeaker)
    }

    private val mAudioManager by lazy {
        activity.getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
    }

    //===========================player setup=============================
    fun initPlayerWelcomeTone() {
        welcomeToneMediaPlayer = if (LocalData.getLanguage() == AppKey.LANGUAGE_BN) {
            MediaPlayer.create(activity, bio.medico.patient.assets.R.raw.caller_tone)
        } else {
            MediaPlayer.create(activity, bio.medico.patient.assets.R.raw.caller_tone)
        }
        welcomeToneMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        welcomeToneMediaPlayer.isLooping = true

        mAudioManager!!.requestAudioFocus(
            null,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
        )

        welcomeToneMediaPlayer.start()
    }

    fun releasePlayerWelcomeTone() {
        if (welcomeToneMediaPlayer != null) {
            welcomeToneMediaPlayer.release()
        }
    }


     fun checkAudioFocus() {
        /*  if (mAudioManager) {
              mAudioManager = this.getSystemService(AUDIO_SERVICE) as AudioManager
          }*/
        if (mAudioManager!!.isMusicActive) {
            val intent = Intent("com.android.music.musicservicecommand")
            intent.putExtra("command", "pause")
            AppUtilConfig.getAppContext().sendBroadcast(intent)
            val result = mAudioManager!!.requestAudioFocus(
                { i -> Timber.e("udioFocusChange:%s", i) },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }
    }

    fun setLoudSpeakerOnOff() {

        appAudioManager!!.setLoudSpeakerOnOff()
    }
}