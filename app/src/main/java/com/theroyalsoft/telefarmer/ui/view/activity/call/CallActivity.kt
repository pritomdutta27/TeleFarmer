package com.theroyalsoft.telefarmer.ui.view.activity.call

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Path
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import bio.medico.patient.callingWebrtc.databinding.ActivityCallKotlinBinding
import com.skh.hkhr.util.IntentUtil
import com.skh.hkhr.util.NetUtil
import com.skh.hkhr.util.thread.AppHandler
import com.skh.hkhr.util.view.OnSingleClickListener
import com.skh.hkhr.util.view.ViewTextUtil
import com.theroyalsoft.telefarmer.extensions.typeWrite
import com.theroyalsoft.telefarmer.helper.AppKey
import com.theroyalsoft.telefarmer.model.ResponseSingleDoctor
import com.theroyalsoft.telefarmer.model.SignalingOfferSocket
import com.theroyalsoft.mydoc.apputil.ImageLode
import com.theroyalsoft.mydoc.apputil.baseUI.ViewPathAnimator
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.TeleFarmerApp
import com.theroyalsoft.telefarmer.utils.AppAudioManager
import com.theroyalsoft.telefarmer.utils.CallManager
import com.theroyalsoft.telefarmer.utils.PreOfferAnswerSocket
import com.theroyalsoft.telefarmer.utils.RTCSignalManager
import com.theroyalsoft.telefarmer.utils.SocketKey
import com.theroyalsoft.telefarmer.utils.UtilWerRtc
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceConnectionState
import org.webrtc.PeerConnection.IceGatheringState
import org.webrtc.PeerConnection.SignalingState
import org.webrtc.RendererCommon
import org.webrtc.RtpReceiver
import org.webrtc.SurfaceViewRenderer
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class CallActivity() : AppCompatActivity() {
    //============================================

    private lateinit var binding: ActivityCallKotlinBinding
    private val viewModel: CallViewModel by viewModels()
    //============================================
    private var mLocalSurfaceView: SurfaceViewRenderer? = null
    private var mRemoteSurfaceView: SurfaceViewRenderer? = null
    private var activity: Activity? = null
    private var callManager: CallManager? = null
    private var welcomeToneMediaPlayer: MediaPlayer? = null
    private var isCallRejectedFORCE = false
    private var isCallReceived = false
    private var isCallNotFound = false
    private var isFinish = false
    private var isDoctorMakeAudioCAll = false
    private var doctorModel: ResponseSingleDoctor.Doctor? = null
    private var releaseDoctorID = ""
    private var mAudioManager: AudioManager? = null
    private var appAudioManager: AppAudioManager? = null
    private val isVideoCall: Boolean
        private get() = IntentUtil.getIntentBooleanValue(AppKey.INTENT_VIDEO_CALL, intent)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activity = this

//        binding.ll.tvSearchDoctor.typeWrite(this, "Calling........",200L)
//        callManager = CallManager()
//        callManager?.initUi(mPeerConnectionObserver)
//
//        //=======================================================================
//        mLocalSurfaceView = findViewById(R.id.LocalSurfaceView)
//        mRemoteSurfaceView = findViewById(R.id.RemoteSurfaceView)
////        ViewTextUtil.setVisibility(binding.llDrag, View.GONE)
//        appAudioManager = AppAudioManager(isVideoCall, this, binding.fbLoudSpeaker)
//        callUISetup()
//        RTCSignalManager.setSignalEventListener(mICallUiListener)
//
////        //=======================================================================
        start()
//        doctorApiCall
        getResponseLogin()
        //=======Search Doctor (with Solar UI)==============
        initSolarUI()
        initPlayerWelcomeTone()
        //==================================================
        /*

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.onA

        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        //resume music player
        am.abandonAudioFocus(null);
*///checkAudioFocus()
//        FirebaseAnalyticsManager.logEventWithNumber(
//            AppKey.EVENT_CALL_SCREEN_VIEW,
//            if (isVideoCall) AppKey.VIDEO_CALL else AppKey.AUDIO_CALL
//        )
//        ApiManager.sendApiLogAppOpen(AppKeyLog.UI_CALL)
        checkAudioFocus()
    }

    private fun checkAudioFocus() {
        if (mAudioManager == null) {
            mAudioManager = this.getSystemService(AUDIO_SERVICE) as AudioManager
        }
        if (mAudioManager!!.isMusicActive) {
            val intent = Intent("com.android.music.musicservicecommand")
            intent.putExtra("command", "pause")
            TeleFarmerApp.appContext?.sendBroadcast(intent)
            val result = mAudioManager!!.requestAudioFocus(
                { i -> Timber.e("udioFocusChange:$i") },
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }
    }

    private fun callUISetup() {
        if (isVideoCall) {
            binding.apply {
                ViewTextUtil.setVisibility(fbVideoOn, View.VISIBLE)
                ViewTextUtil.setVisibility(fbCameraRotate, View.VISIBLE)
                imgCallIcon.setImageResource(R.drawable.photo_camera_)
                ViewTextUtil.setVisibility(rlAudioLayout, View.GONE)
            }

            binding.fbCameraRotate.setOnClickListener(object : OnSingleClickListener() {
                override fun onSingleClick(view: View?) {
                    CallManager.switchCamera()
                }
            })
            binding.tvDoctorName.setTextColor(getColor(R.color.white_color))
            binding.tvCallDuration.setTextColor(getColor(R.color.white_color))
        } else {
            binding.tvDoctorName.setTextColor(getColor(R.color.black))
            binding.tvCallDuration.setTextColor(getColor(R.color.black))
        }
    }

    //===========================player setup=============================
    private fun initPlayerWelcomeTone() {
        welcomeToneMediaPlayer = MediaPlayer.create(this, R.raw.caller_tone)
        welcomeToneMediaPlayer!!.isLooping = true
        welcomeToneMediaPlayer!!.start()
    }

    private fun releasePlayerWelcomeTone() {
        if (welcomeToneMediaPlayer != null) {
            welcomeToneMediaPlayer!!.release()
        }
    }

    //====================end player setup================
    //==========================================call duration=======================================
    private val timerHandler: Handler? = Handler()
    private var startTime: Long = 0
    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            val callDuration = System.currentTimeMillis() - startTime
            val minutes: String = AppKey.getMinute(callDuration)
            val seconds: String = AppKey.getSecond(callDuration)
            binding.tvCallDuration.text = "$minutes:$seconds"
            timerHandler!!.postDelayed(this, 1000)
        }
    }

    //=================================================================================
    var isRinging = false

    //    private void startPulse() {
    //        runnable.run();
    //    }
    //
    //    private void stopPulse() {
    //        handlerAnimation.removeCallbacks(runnable);
    //    }
    private fun initSolarUI() {

//        startPulse();

//        float aaa = 120;
//        float radiusRatio1 = aaa * 1;
//        float radiusRatio2 = aaa * 2;
//        float radiusRatio3 = aaa * 3;
//        float radiusRatio4 = aaa * 4;
//
//        int delay1 = 1;
//        int delay2 = 0;
//        int delay3 = 1;
//        int delay4 = 0;
//
//        drawPath(imgVDoctor2, radiusRatio1, delay1, -1, -2);
//        drawPath(imgVDoctor3, radiusRatio2, delay2, 3, 1);
//        drawPath(imgVDoctor4, radiusRatio3, delay3, 2, 3);
//        drawPath(imgVDoctor5, radiusRatio4, delay4, 2, 1);
//
//        llRoundDashView.setShapeRadiusRatio(radiusRatio1, radiusRatio2, radiusRatio3, radiusRatio4);
//
//
//        binding.ll.llCancel.setOnClickListener {
////            ApiManager.getDoctorCancel() //get doctor apiCall cancel
//            forceCloseUi(isRinging)
//            //                stopPulse();
//            closeCallUi()
////            ApiManager.sendApiLog(
////                AppKeyLog.UI_HOME,
////                AppKeyLog.CLOSE_CALL_UI,
////                AppKeyLog.NA,
////                AppKeyLog.NA,
////                AppKeyLog.NA
////            )
////            FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_HANGUP_CLICK)
//        }


        /*ImageLode.lodeImage(imgVDoctor1, url);
         ImageLode.lodeImage(imgVDoctor2, url);
         ImageLode.lodeImage(imgVDoctor3, url);
         ImageLode.lodeImage(imgVDoctor4, url);
         ImageLode.lodeImage(imgVDoctor5, url);*/

        //setImage(imgVDoctor1, R.drawable.ic_male_doctor);
//        setImage(imgVDoctor2, R.drawable.ic_female_doctor);
//        setImage(imgVDoctor3, R.drawable.ic_male_doctor);
//        setImage(imgVDoctor4, R.drawable.ic_female_doctor);
//        setImage(imgVDoctor5, R.drawable.ic_female_doctor);
    }

    private fun setImage(imgVDoctor: ImageView, image: Int) {
        imgVDoctor.setImageResource(image)
    }

    private fun forceCloseUi(isForceClose: Boolean) {
        try {
            releasePlayerWelcomeTone()
            if (isForceClose) {
                if (callManager != null) {
                    callManager?.doForceStopCall()
                }
            }
            if (doctorModel != null && !isCallReceived) {
                Timber.d("Api == callPostDoctorManage")
                callPostDoctorManage(doctorModel?.uuid!!, doctorModel?.name!!)
                doctorModel = null
            }
        } catch (e: Exception) {
            Timber.e("forceCloseUi Error: $e")
            //ApiManager.sendApiLogErrorCodeScope(e)
        }
    }

    //===========================doctor finding=============================
    var callCount = 0
    private val doctorApiCall: Unit
        private get() {
            callCount++
            SocketKey.setReceiverDeviceId("")
            if (callManager != null) callManager?.resetOffer("")
            if (isCallReceived) {
                Timber.e("*******Already in a calle**********************")
                return
            }
            if (isFinish) {
                Timber.e("*******Already ui Close**********************")
                return
            }
            Timber.e("*******getDoctorApiCall**********************")
            // ApiManager.getDoctor(iApiResponseToken)
        }
    private val isFirstTime = true

//    private val iApiResponseToken: ApiManager.IApiResponse = object : IApiResponse() {
//        fun <T> onSuccess(model: T) {
//            try {
//                val responseDoctor: ResponseSingleDoctor = model as ResponseSingleDoctor
//                doctorNotFound = false
//                foundDoctorCall(responseDoctor)
////                ApiManager.sendApiLog(
////                    AppKeyLog.UI_CALL,
////                    AppKeyLog.FOUND_DOCTOR,
////                    AppKeyLog.ENDPOINT_TYPE_API,
////                    AppUrl.URL_CALL_SINGLE_DOCTOR,
////                    (("doctorName:" + responseDoctor.getDoctor().getName()).toString() + ", " +
////                            "mobile:" + responseDoctor.getDoctor()
////                        .getPhoneNumber()).toString() + ", " +
////                            "doctorId:" + responseDoctor.getDoctor().getUuid()
////                )
//            } catch (exception: Exception) {
//                closeHandlerRingingTime()
//                Timber.e("Error:$exception")
//               // ApiManager.sendApiLogErrorCodeScope(exception)
//            }
//        }
//
//        fun onFailed(message: String?) {
//            doctorNotFound = true
//           // LoadingUtil.hide()
//            Timber.e("===onFailed getDoctor===")
////            ApiManager.sendApiLog(
////                AppKeyLog.UI_CALL,
////                AppKeyLog.NOT_FOUND_DOCTOR,
////                AppKeyLog.ENDPOINT_TYPE_API,
////                AppUrl.URL_CALL_SINGLE_DOCTOR,
////                "Doctor Not found."
////            )
           // notFoundDoctorCall()
//        }
//    }

    //=====================================================================
    private fun notFoundDoctorCall() {
        try {
            if (!releaseDoctorID.isEmpty() && !isCallRejectedFORCE) {
                Timber.e("Api == callPostDoctorManage")
                callPostDoctorManage(
                    releaseDoctorID,
                    doctorModel?.name!!
                )
            }
            isCallRejectedFORCE = false
            releaseDoctorID = ""
            doctorModel = null

            //releaseDoctorID = doctorModel.getUuid();
            handlerRecallDoctorTime.postDelayed({
                Timber.e("======reTryFoundDoctor====>> RecallDoctorTime End")
                reTryFoundDoctor()
            }, AppKey.TIMER_RECALL_DOCTOR_API)
        } catch (exception: Exception) {
            Timber.e("Error:$exception")
            //ApiManager.sendApiLogErrorCodeScope(exception)
        }
    }

    private fun getResponseLogin() {
        lifecycleScope.launch {
            viewModel._doctorStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    val model = ResponseSingleDoctor().apply {
                        doctorModel.apply {
                            data.copy()
                        }
                    }
                    foundDoctorCall(model)
                }
            }
        }
    }

    private fun foundDoctorCall(responseDoctor: ResponseSingleDoctor?) {
        if (responseDoctor == null) {
            Timber.e("Error: responseDoctorList null")
            //closeCallUi();
            return
        }
        Timber.e("responseDoctor found")
        doctorModel = responseDoctor.getDoctor()
        SocketKey.RECEIVER_ID = doctorModel?.uuid!!
        setDoctorName(doctorModel?.name!!, doctorModel?.isPushCall!!)
        callOffer()
        if (releaseDoctorID.isEmpty()) {
            releaseDoctorID = doctorModel?.uuid!!
            return
        }
        Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
        if (isCallRejectedFORCE) {
            isCallRejectedFORCE = false
        } else {
            Timber.e("Api == callPostDoctorManage")
            callPostDoctorManage(releaseDoctorID, doctorModel?.name!!)
        }
        releaseDoctorID = doctorModel?.uuid!!
    }

    var doctorNotFound = false
    private fun setDoctorName(name: String, isPushCall: Boolean) {
        runOnUiThread {
           // binding.ll.tvSelectedDoctor.text = name
           /* if (!isPushCall) {
                binding.ll.tvSearchDoctor.text = AppKey.Calling_to + name
            } else {
                val messageDoctorNotFound =
                    "Doctor are busy now,\nplease wait or try again after some time."
                if (doctorNotFound) {
                    binding.ll.tvSearchDoctor.text = messageDoctorNotFound
                    binding.ll.tvSearchDoctor.setCompoundDrawables(null, null, null, null)
                } else {
                    binding.ll.tvSearchDoctor.text = AppKey.Searching_doctor
                    binding.ll.tvSearchDoctor.setCompoundDrawables(
                        getDrawable(R.drawable.ic_mobile_call), null, null, null
                    )
                }
            }*/
            binding.tvDoctorName.text = name
        }
    }

    //===========================doctor random selection=============================
    var handlerCalleeNotFound = Handler()
    var handlerRecallDoctorTime = Handler()
    var handlerRingingTime = Handler()
    var handlerPresencesCheckTime = Handler()
    var handlerConnectingTime = Handler()
    fun reTryFoundDoctor() {
        if (!NetworkUtils.isConnected()) {
            Toast.makeText(this@CallActivity, AppKey.ERROR_INTERNET_CONNECTION, Toast.LENGTH_SHORT)
                .toString()
        }
        closeHandlerRecallDoctorTime()
        closeHandlerPresencesCheckTime()
        closeHandlerRingingTime()
        closeHandlerConnectingTime()
        if (!isCallReceived) {
            setDoctorName("", true)
        }
        Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
        doctorApiCall
    }

    fun closeHandlerCalleeNotFound() {
        Timber.i("handlerCalleeNotFound closeHandler")
        handlerCalleeNotFound.removeCallbacksAndMessages(null)
    }

    fun closeHandlerRecallDoctorTime() {
        Timber.i("handlerRecallDoctorTime closeHandler")
        handlerRecallDoctorTime.removeCallbacksAndMessages(null)
    }

    fun closeHandlerRingingTime() {
        Timber.i("noAnswerCallHandler closeHandler")
        handlerRingingTime.removeCallbacksAndMessages(null)
    }

    fun closeHandlerConnectingTime() {
        Timber.i("handlerConnectingTime closeHandler")
        handlerConnectingTime.removeCallbacksAndMessages(null)
    }

    private fun startPresencesCheckTimmer() {
        Timber.i("Start handlerPresencesCheckTime Handler")
        closeHandlerPresencesCheckTime()
        handlerPresencesCheckTime.postDelayed({
            Timber.i("Run handlerPresencesCheckTime Handler")
            Timber.e("======reTryFoundDoctor====>> PresencesCheckTime out")
            reTryFoundDoctor()
        }, AppKey.TIMER_PRESENCES_CHECK_TIME)
    }

    fun closeHandlerPresencesCheckTime() {
        Timber.i("handlerPresencesCheckTime closeHandler")
        handlerPresencesCheckTime.removeCallbacksAndMessages(null)
    }

    //api call for manage doctor
    private fun callPostDoctorManage(releaseDoctorID: String, doctorName: String) {
        if (isCallNotFound) {
            Timber.e("isCallNotFound")
            isCallNotFound = false
            return
        }
        Timber.e("---------------------------------")
        Timber.e("Found Doctor :$doctorName")
        Timber.e("---------------------------------")
//        ApiManager.sendApiLog(
//            AppKeyLog.UI_CALL,
//            AppKeyLog.DOCTOR_STATUS_UPDATE,
//            AppKeyLog.ENDPOINT_TYPE_API,
//            AppUrl.URL_STATUS_UPDATE,
//            "Update Doctor status online. DoctorName: $doctorName, DoctorId: $releaseDoctorID"
//        )
//        ApiManager.statusUpdate(releaseDoctorID, "online", object : IApiResponse() {
//            fun <T> onSuccess(model: T) {
//                try {
//                    val commonResponse: CommonResponse = model as CommonResponse
//                    if (commonResponse.isSuccess) {
//
//                        /*XmppKey.USER_RECEIVER_ID = XmppKey.getReceiverId(doctorID);
//                        tvSelectedDoctor.setText(doctorName);
//                        tvDoctorName.setText(doctorName);
//                        callOffer();*/
//                    }
//                } catch (exception: Exception) {
//                    Timber.e("Error:$exception")
//                    //ApiManager.sendApiLogErrorCodeScope(exception)
//                }
//            }
//
//            fun onFailed(message: String?) {}
//        })
    }

    //===========================ui init============================
    private fun uiInit() {
        mLocalSurfaceView!!.init(UtilWerRtc.getEglBase().eglBaseContext, null)
        mLocalSurfaceView!!.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
        mLocalSurfaceView!!.setMirror(true)
        mLocalSurfaceView!!.setEnableHardwareScaler(false /* enabled */)
        mRemoteSurfaceView!!.init(UtilWerRtc.getEglBase().eglBaseContext, null)
        mRemoteSurfaceView!!.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
        mRemoteSurfaceView!!.setMirror(true)
        mRemoteSurfaceView!!.setEnableHardwareScaler(true /* enabled */)
        //mRemoteSurfaceView.setZOrderMediaOverlay(true);


        //  mRemoteSurfaceView.setOnTouchListener(onTouchListener);

        //  llDrag.setOnDragListener(onDragListener);
        binding.activityHangUpButton.setOnClickListener(View.OnClickListener {
            if (callManager == null) {
                return@OnClickListener
            }
//            FirebaseAnalyticsManager.logEventWithNumber(
//                AppKey.EVENT_CALL_END_CLICK,
//                JsonUtil.getJsonStringFromObject(doctorModel)
//            )
            callManager?.doEndCall()
            Timber.e("===closeCallUi===")
            closeCallUi()
        })
        binding.fbVideoOn.setOnClickListener { setCameraOnOff() }
        binding.fbMute.setOnClickListener { setMicOnOff() }

        //========================================================
        callManager?.setProxyVideoSink(mLocalSurfaceView)
        callManager?.createVideoTrack(isVideoCall)
        //========================================================


        //setLoudSpeakerOnOff();
        binding.fbLoudSpeaker.setOnClickListener { appAudioManager?.setLoudSpeakerOnOff() }
    }

    private fun callOffer() {
        if (callManager == null) {
            Timber.e("callManager == null")
            return
        }
        isRinging = true
        Timber.e("==========Doctor FOUND =================================================")
        Timber.e(("NAME : " + doctorModel?.name).toString() + " | UUID : " + doctorModel!!?.uuid)
        Timber.e("=======================================================================")
        val callType: String =
            if (isVideoCall) SocketKey.KEY_TYPE_VIDEO_PERSONAL_CODE else SocketKey.KEY_TYPE_AUDIO_PERSONAL_CODE
        callManager?.callOffer(doctorModel?.uuid!!, callType)
        startPresencesCheckTimmer()
    }

    //==============================================================================================
    @AfterPermissionGranted(RC_CALL)
    fun start() {
        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
        if (EasyPermissions.hasPermissions(this@CallActivity, perms.toString())) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            uiInit()
        } else {
            //EasyPermissions.requestPermissions(activity, "Need some permissions", RC_CALL, perms)
        }
    }

    //=======================================================================================
    //=======================================================================================
    private val mPeerConnectionObserver: PeerConnection.Observer =
        object : PeerConnection.Observer {
            override fun onSignalingChange(signalingState: SignalingState) {
                Timber.e("onSignalingChange: $signalingState")
            }

            override fun onIceConnectionChange(iceConnectionState: IceConnectionState) {
                Timber.e("onIceConnectionChange: $iceConnectionState")
//                ApiManager.sendApiLogEndpointSocket(
//                    iceConnectionState.name,
//                    AppKeyLog.WEBRTC,
//                    "Web-Rtc status. " + doctorInfoMessage
//                )
                if ((iceConnectionState.name == "CONNECTED")) {
                    ViewTextUtil.setVisibility(binding.rlSolarView, View.GONE)
                    releasePlayerWelcomeTone()
                    callConnected(false)/*   if (isVideoCall()) {
                    setLoudSpeakerOnOff();
                } else {
                    isOn = true;
                    setLoudSpeakerOnOff();
                }*/
                } else {
                    reConnection()
                }
            }

            override fun onIceConnectionReceivingChange(b: Boolean) {
                Timber.e("onIceConnectionChange: $b")
            }

            override fun onIceGatheringChange(iceGatheringState: IceGatheringState) {
                Timber.e("onIceGatheringChange: $iceGatheringState")
            }

            override fun onIceCandidate(iceCandidate: IceCandidate) {
                Timber.e("onIceCandidate: $iceCandidate")
                callManager?.onIceCandidate(iceCandidate)
            }

            override fun onIceCandidatesRemoved(iceCandidates: Array<IceCandidate>) {
                callManager?.removeIceCandidates(iceCandidates)
            }

            override fun onAddStream(mediaStream: MediaStream) {
                Timber.e("onAddStream: " + mediaStream.videoTracks.size)/*   if(mediaStream.audioTracks.size() > 0) {
                remoteAudioTrack = stream.audioTracks.get(0);
            }*/
            }

            override fun onRemoveStream(mediaStream: MediaStream) {
                Timber.e("onRemoveStream")
            }

            override fun onDataChannel(dataChannel: DataChannel) {
                Timber.e("onDataChannel")
            }

            override fun onRenegotiationNeeded() {
                Timber.e("onRenegotiationNeeded")
            }

            override fun onAddTrack(rtpReceiver: RtpReceiver, mediaStreams: Array<MediaStream>) {
                callManager?.onAddTrack(rtpReceiver, mediaStreams, mRemoteSurfaceView)
            }
        }

    private fun reConnection() {
        Timber.e("reConnection:...")
        if (NetUtil.isNetworkAvailable()) {
            if (callManager != null) callManager?.reTryIceConnection()
        } else {
            AppHandler.getUiHandlerNew().postDelayed(
                Runnable { if (callManager != null) callManager?.reTryIceConnection() }, 2000
            )
        }
    }

    //==================================================================================================
    //===============================================================================================================
    private val mICallUiListener: RTCSignalManager.ICallUiListener = object :
        RTCSignalManager.ICallUiListener {
        override fun onBroadcastReceived(message: String) {
            Timber.e("onBroadcastReceived: $message")/*  try {

                if (message.contains(SocketKeyChat.KEY_TYPE_CALLEE_NOT_FOUND)) {

                } else if (message.contains(SocketKeyChat.KEY_TYPE_CALL_ACCEPTED)) {


                } else if (message.contains(SocketKeyChat.KEY_TYPE_CALLEE_NO_ANSWER)) {

                } else if (message.contains(SocketKeyChat.KEY_CALL_REJECTED_FORCE)) {


                } else if (message.contains(SocketKeyChat.KEY_TYPE_OFFER)) {


                } else if (message.contains(SocketKeyChat.KEY_TYPE_CALL_ENDED)) {

                }


            } catch (Exception e) {
                Timber.e("Error:" + e.toString());
            }*/
        }

        override fun onPreOfferAnswer(preOfferAnswerSocket: PreOfferAnswerSocket) {
            when (preOfferAnswerSocket.preOfferAnswer) {
                SocketKey.KEY_TYPE_CALLEE_NOT_FOUND -> {
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALLEE_NOT_FOUND,
//                        "Doctor not connected with Socket."
//                    )
                    isCallNotFound = true
                    Timber.e(" CALLEE_NOT_FOUND:$isCallNotFound")
                    handlerCalleeNotFound.postDelayed({
                        Timber.e("======reTryFoundDoctor====>> CALLEE_NOT_FOUND")
                        reTryFoundDoctor()
                    }, AppKey.TIMER_NOT_FOUND_DOCTOR_API)
                }

                SocketKey.KEY_TYPE_CALLEE_NO_ANSWER -> {
                    Timber.e("======reTryFoundDoctor====>> CALLEE_NO_ANSWER")
//                    FirebaseAnalyticsManager.logEventWithNumber(
//                        AppKey.EVENT_CALL_NO_ANSWER,
//                        doctorModel.getName(),
//                        doctorModel.getPhoneNumber()
//                    )
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALLEE_NO_ANSWER,
//                        "Doctor Not Receive. " + doctorInfoMessage
//                    )
                    reTryFoundDoctor()
                }

                SocketKey.KEY_CALL_REJECTED_FORCE -> {
                    isCallRejectedFORCE = true
                    Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
                    Timber.e("======reTryFoundDoctor====>> CALL_REJECTED_FORCE")
                    reTryFoundDoctor()
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_CALL_REJECTED_FORCE,
//                        "Doctor Go offline or Busy. " + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_CALL_ENDED -> {
                    closeCallUi()
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_ENDED,
//                        "Doctor Call End. " + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_CALL_ACCEPTED -> {
                    Timber.e("=========KEY_TYPE_CALL_ACCEPTED========")
                    closeHandlerPresencesCheckTime()
                    closeHandlerRingingTime()
                    //callReceived();
                    isCallReceived = true
                    releasePlayerWelcomeTone()
                    runOnUiThread {
                        ViewTextUtil.setVisibility(
                            binding.rlSolarView, View.GONE
                        )
                    }
                    callConnected(true)

//                    ViewTextUtil.setVisibility(rrAppbar, View.VISIBLE);
                    ViewTextUtil.setVisibility(binding.llCallEnd, View.VISIBLE)
                    val url: String = doctorModel?.image!!
                    Timber.e("doctor image::: $url")
                    ImageLode.lodeImage(binding.imgProfile, url, R.drawable.video_call)
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_ACCEPTED,
//                        "Doctor Call Receive. " + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_CALL_REJECTED -> {
                    isRinging = false
                    Timber.e("======reTryFoundDoctor====>> CALL_REJECTE")
                    reTryFoundDoctor()
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_REJECTED,
//                        "Doctor Call Reject." + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_VIDEO_OFF -> {
                    Timber.e("======Doctor VIDEO_OFF====>> ")
                    isDoctorMakeAudioCAll = true
                    ViewTextUtil.setVisibility(binding.rlAudioLayout, View.VISIBLE)
                    binding.tvDoctorName.setTextColor(getColor(R.color.black))
                    binding.tvCallDuration.setTextColor(getColor(R.color.black))
                    setCameraOnOff(true)
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_VIDEO_OFF,
//                        "Users Video Off by Doctor. " + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_VIDEO_ON -> {
                    Timber.e("======Doctor VIDEO_ON====>> ")
                    isDoctorMakeAudioCAll = false
                    setCameraOnOff(false)
                    ViewTextUtil.setVisibility(binding.rlAudioLayout, View.GONE)
                    binding.tvDoctorName.setTextColor(getColor(R.color.white_color))
                    binding.tvCallDuration.setTextColor(getColor(R.color.white_color))
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_VIDEO_ON,
//                        "Users Video On by Doctor.. " + doctorInfoMessage
//                    )
                }

                else -> {
                    Timber.e("onPreOfferAnswer: default:" + preOfferAnswerSocket.getPreOfferAnswer())
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        preOfferAnswerSocket.getPreOfferAnswer(),
//                        " Socket key not found. " + doctorInfoMessage
//                    )
                }
            }
        }

        override fun onWebRtcSignaling(signalingOfferSocket: SignalingOfferSocket) {
            when (signalingOfferSocket.getType()) {
                SocketKey.KEY_TYPE_OFFER -> {
                    callManager?.onRemoteOfferReceivedSocket(
                        signalingOfferSocket, doctorModel?.uuid!!
                    )
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_OFFER,
//                        "Receive Call Offer ." + doctorInfoMessage
//                    )
                }

                SocketKey.KEY_TYPE_CANDIDATE -> {
                    callManager?.onRemoteCandidateReceivedSocket(signalingOfferSocket)
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        AppKeyLog.KEY_TYPE_CANDIDATE,
//                        "Receive CANDIDATE. " + doctorInfoMessage
//                    )
                }

                else -> {
                    Timber.e("onWebRtcSignaling: default:" + signalingOfferSocket.getType())
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        signalingOfferSocket.getType(),
//                        "Receive socket data but Socket key not found. " + doctorInfoMessage
//                    )
                }
            }
        }
    }

    private fun callConnected(isNotIceConnect: Boolean) {
//        FirebaseAnalyticsManager.logEventWithNumber(
//            AppKey.EVENT_CALL_CONNECTED,
//            JsonUtil.getJsonStringFromObject(doctorModel)
//        )
        closeHandlerConnectingTime()
        runOnUiThread {
//            binding.apply {
//                ViewTextUtil.setVisibility(llDrag, View.VISIBLE)
//                ViewTextUtil.setVisibility(remoteBackground, View.VISIBLE)
//                ViewTextUtil.setVisibility(llCallEnd, View.VISIBLE)
//            }
        }
        if (isNotIceConnect) {
            startTime = System.currentTimeMillis()
            timerHandler!!.postDelayed(timerRunnable, 0)
        }
    }

    //=================================================================================
    //==------------------------------------------------------------------------
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onResume() {
        RTCSignalManager.setSignalEventListener(mICallUiListener)
        super.onResume()
        if (callManager != null) callManager?.startCapture()
    }

    override fun onPause() {
        super.onPause()
        if (callManager != null) callManager?.stopCapture()
    }

    override fun onDestroy() {
        forceCloseUi(true)
        closeAllHandler()
        super.onDestroy()
    }

    override fun onBackPressed() {}
    private fun closeCallUi() {
        closeHandlerRecallDoctorTime()
        closeHandlerConnectingTime()
        closeHandlerPresencesCheckTime()
        closeHandlerRingingTime()
        mLocalSurfaceView!!.release()
        mRemoteSurfaceView!!.release()
        if (callManager != null) {
            callManager?.socketDisConnect()
            callManager?.onDestroy()
        }
        callManager = null
        RTCSignalManager.removeSignalEventListener()
        timerHandler?.removeCallbacksAndMessages(null)
        isFinish = true
        finish()
        Timber.e("===finish===")
    }

    fun closeAllHandler() {
        closeHandlerCalleeNotFound()
        closeHandlerRecallDoctorTime()
        closeHandlerConnectingTime()
        closeHandlerPresencesCheckTime()
        closeHandlerRingingTime()
    }

    //===============================================================================================================
    private fun setMicOnOff() {
        if (callManager!!.isMicrophoneOn()) {
            callManager?.setMicrophoneOnOff(false)
            binding.fbMute.setImageDrawable(getDrawable(R.drawable.baseline_mic_24))
        } else {
            callManager?.setMicrophoneOnOff(true)
            binding.fbMute.setImageDrawable(getDrawable(R.drawable._mic_off))
        }
    }

    private fun setCameraOnOff() {
        if (isDoctorMakeAudioCAll) {
            Timber.e("isDoctorMakeAudioCAll:$isDoctorMakeAudioCAll")
            return
        }
        if (callManager!!.isCameraOn()) {
            callManager?.setCameraOnOff(false)
            binding.fbVideoOn.setImageDrawable(getDrawable(R.drawable._videocam))
        } else {
            callManager?.setCameraOnOff(true)
            binding.fbVideoOn.setImageDrawable(getDrawable(R.drawable._videocam_off))
        }
    }

    private fun setCameraOnOff(isOnOff: Boolean) {
        if (isOnOff) {
            callManager?.setCameraOnOff(false)
            binding.fbVideoOn.setImageDrawable(getDrawable(R.drawable._videocam_off))
        } else {
            setCameraOnOff()
        }
    }

    val doctorInfoMessage: String
        /*  private boolean isOn = false;

               private void setLoudSpeakerOnOff() {
                   isOn = !isOn;

                   if (mAudioManager == null) {
                       mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
                   }

                   if (isOn) {
                       mAudioManager.setMode(AudioManager.MODE_IN_CALL);
                       mAudioManager.setMode(AudioManager.MODE_NORMAL);
                       fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOn);
                   } else {
                       //Seems that this back and forth somehow resets the audio channel
                       mAudioManager.setMode(AudioManager.MODE_NORMAL);
                       mAudioManager.setMode(AudioManager.MODE_IN_CALL);

                       fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOff);
                   }

                   Timber.e("setLoudSpeakerOnOff:: " + isOn);

                   mAudioManager.setSpeakerphoneOn(isOn);

               }

           */
        get() {
            return if (doctorModel != null) {
                (" DoctorName: " + doctorModel?.name) + ", DoctorId: " + doctorModel?.uuid
            } else ""
        }

    companion object {
        const val RC_CALL = 111

        //    private Handler handlerAnimation = new Handler();
        //
        //    private Runnable runnable = new Runnable() {
        //        @Override
        //        public void run() {
        //            imgVDoctor2.animate().scaleX(3f).scaleY(3f).alpha(0f).setDuration(900)
        //                    .withEndAction(() -> {
        //                        imgVDoctor2.setScaleX(1f);
        //                        imgVDoctor2.setScaleY(1f);
        //                        imgVDoctor2.setAlpha(1f);
        //                    });
        //
        //            imgVDoctor3.animate().scaleX(3f).scaleY(3f).alpha(0f).setDuration(600)
        //                    .withEndAction(() -> {
        //                        imgVDoctor2.setScaleX(1f);
        //                        imgVDoctor2.setScaleY(1f);
        //                        imgVDoctor2.setAlpha(1f);
        //                    });
        //
        //            handlerAnimation.postDelayed(this, 1200);
        //        }
        //    };
        fun drawPath(imageView: ImageView?, radius: Float, delay: Int, x: Float, y: Float) {
            val path4 = Path()
            path4.addCircle(x, y, radius, Path.Direction.CW)
            ViewPathAnimator.animate(imageView, path4, delay, 3)
        }

        fun goCallActivity(activity: Activity, isVideoCall: Boolean) {
            Timber.e("isVideoCall:$isVideoCall")
            if (!NetworkUtils.isConnected()) {
                Timber.e("No Internet Connection!")
                return
            }

            //HomeFragment.isCallButtonClicked = true
            val intent = Intent(activity, CallActivity::class.java)
            intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall)
            activity.startActivity(intent)
        }
    }
}