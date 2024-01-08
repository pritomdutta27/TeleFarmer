package bio.medico.patient.callingWebrtc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import bio.medico.patient.callingWebrtc.databinding.ActivityCallKotlinBinding
import bio.medico.patient.common.AppKey
import bio.medico.patient.common.AppKeyLog
import bio.medico.patient.common.UiNavigation
import bio.medico.patient.model.apiResponse.CommonResponse
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor.Doctor
import bio.medico.patient.model.apiResponse.ResponseSubInfo
import bio.medico.patient.model.socket.PreOfferAnswerSocket
import bio.medico.patient.model.socket.SignalingOfferSocket
import bio.medico.patient.socketUtils.SocketKey
import bio.medico.patient.socketUtils.SocketKey.receiverDeviceId
import bio.medico.patient.socketUtils.WrtcSignalManager
import bio.medico.patient.socketUtils.WrtcSignalManager.ICallUiListener
import com.skh.hkhr.util.IntentUtil
import com.skh.hkhr.util.JsonUtil
import com.skh.hkhr.util.NetUtil
import com.skh.hkhr.util.log.ToastUtil
import com.skh.hkhr.util.thread.AppHandler
import com.skh.hkhr.util.view.LoadingUtil
import com.skh.hkhr.util.view.OnSingleClickListener
import com.theroyalsoft.mydoc.apputil.TimeUtil
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.RendererCommon
import org.webrtc.RtpReceiver
import timber.log.Timber

@AndroidEntryPoint
class CallActivityKotlin : AppCompatActivity() {

    private val viewModel: CallActivityViewModel by viewModels()

    private var callManager: CallManager? = null

    private var isCallRejectedFORCE = false
    private var isCallReceived = false
    private var isCallNotFound = false
    private var isFinish = false
    private var isDoctorMakeAudioCAll = false
    private var isRinging = false
    private var doctorNotFound = false


    private var releaseDoctorID = ""


    private val callAudioManager by lazy {
        CallAudioManager(
            isVideoCall,
            activity,
            binding.fbLoudSpeaker
        )
    }


//    private val freeCallManager by lazy {
//        FreeCallManager(this, freeCallInfo) {
//            closeFreeCallTimer()
//        }
//    }

    private val doctorManager by lazy { DoctorManager() }


    private var doctorModel: Doctor? = null
    private val doctorInfoMessage: String
        get() = if (doctorModel != null) {
            " DoctorName: " + doctorModel!!.name + ", DoctorId: " + doctorModel!!.uuid
        } else ""

    //==================================================================================
    private lateinit var activity: Activity
    private lateinit var binding: ActivityCallKotlinBinding
    private val uiName = AppKeyLog.UI_CALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this

        callManager = CallManager.getInstance()
        callManager?.initUi(mPeerConnectionObserver)


        binding.llDrag.isVisible = false


        callUISetup()
        WrtcSignalManager.setSignalEventListener(mICallUiListener)

        //=======================================================================
        start()
        doctorApiCall
        callAudioManager.checkAudioFocus()

        //=======Search Doctor (with Solar UI)==============
        showSolarUI()

        callAudioManager.initPlayerWelcomeTone()

        getResponse()
        ifApiGetError()
        //==================================================
        /*

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.onA

        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        //resume music player
        am.abandonAudioFocus(null);
*/
//        logEventWithNumber(
//            AppKey.EVENT_CALL_SCREEN_VIEW,
//            if (isVideoCall) AppKey.VIDEO_CALL else AppKey.AUDIO_CALL
//        )
        //sendUiOpen(uiName)
    }

    private fun showSolarUI() {
        SolarUi(binding.llSolarUi, uiName) {
            //ApiManager.getDoctorCancel() //get doctor apiCall cancel
            val data = Intent()
            setResult(AppKey.RESULT_CODE_BUY_PACK, data)
            forceCloseUi(isRinging)
            closeCallUi()
        }
    }


    private fun callUISetup() {
        binding.apply {
            if (isVideoCall) {
                fbVideoOn.isVisible = true
                fbCameraRotate.isVisible = true

                imgCallIcon.setImageResource(bio.medico.patient.assets.R.drawable.camera)
                rlAudioLayout.isVisible = false

                fbCameraRotate.setOnClickListener(object : OnSingleClickListener() {
                    override fun onSingleClick(view: View) {
                        CallManager.switchCamera()
                    }
                })
            }
        }
    }

    private fun exceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
    }


    //====================end player setup================
    //==========================================call duration=======================================
    /*   private val callDurationCalculateJob by lazy {
           CoroutineScope(Dispatchers.Main + exceptionHandler())
               .launch {

                   val callDuration = System.currentTimeMillis() - startTime
                   val minutes = TimeUtil.getMinute(callDuration)
                   val seconds = TimeUtil.getSecond(callDuration)
                   val time = "$minutes:$seconds"
                   Timber.d("$time")
                   binding.tvCallDuration.text = time

                   delay(1000)

                   callDurationCalculateJobStart()
               }
       }*/

    /* private fun callDurationCalculateJobStart() {
         callDurationCalculateJob.start()
     }*/


    private var startTime: Long = 0

    private val timerHandler: Handler? = Handler()
    private val timerRunnable: Runnable = object : Runnable {
        override fun run() {
            val callDuration = System.currentTimeMillis() - startTime
            val minutes = TimeUtil.getMinute(callDuration)
            val seconds = TimeUtil.getSecond(callDuration)
            binding.tvCallDuration!!.text = "$minutes:$seconds"
            timerHandler!!.postDelayed(this, 1000)
        }
    }

    //=================================================================================


    private fun forceCloseUi(isForceClose: Boolean) {
        try {
            callAudioManager.releasePlayerWelcomeTone()

            if (isForceClose) {
                if (callManager != null) {
                    callManager!!.doForceStopCall()
                }
            }
            if (doctorModel != null && !isCallReceived) {
                Timber.d("Api == callPostDoctorManage")
                callPostDoctorManage(doctorModel!!.uuid, doctorModel!!.name)
                doctorModel = null
            }
        } catch (e: Exception) {
            Timber.e("forceCloseUi Error: $e")
            //sendApiLogErrorCodeScope(e)
        }
    }

    //===========================doctor finding=============================
    var callCount = 0
    private val doctorApiCall: Unit
        private get() {

            callCount++

            receiverDeviceId = ""

            if (callManager != null) callManager?.resetOffer("")

            if (isCallReceived) {
                Timber.e("*******Already in a call**********************")
                return
            }

            if (isFinish) {
                Timber.e("*******Already ui Close**********************")
                return
            }

            Timber.e("*******getDoctorApiCall**********************")
           // ApiManager.getDoctor(iApiResponseToken, false)
            viewModel.fetchAvailableDoctor()
        }


//    private val iApiResponseToken: IApiResponse = object : IApiResponse {
//        override fun <T> onSuccess(model: T) {
//            try {
//                val responseDoctor = model as ResponseSingleDoctor
//
//                doctorNotFound = false
//
//                foundDoctorCall(responseDoctor)
//
////                sendApiLog(
////                    uiName,
////                    AppKeyLog.FOUND_DOCTOR,
////                    AppKeyLog.ENDPOINT_TYPE_API,
////                    AppUrl.URL_CALL_SINGLE_DOCTOR,
////                    "doctorName:" + responseDoctor.doctor.name + ", " +
////                            "mobile:" + responseDoctor.doctor.phoneNumber + ", " +
////                            "doctorId:" + responseDoctor.doctor.uuid
////                )
//
//            } catch (exception: Exception) {
//                doctorManager.closeHandlerRingingTime()
//                Timber.e("Error:$exception")
////                sendApiLogErrorCodeScope(exception)
//            }
//        }
//
//        override fun onFailed(message: String) {
//            doctorNotFound = true
//            LoadingUtil.hide()
//            Timber.e("===onFailed getDoctor===")
//
////            sendApiLog(
////                uiName,
////                AppKeyLog.NOT_FOUND_DOCTOR,
////                AppKeyLog.ENDPOINT_TYPE_API,
////                AppUrl.URL_CALL_SINGLE_DOCTOR,
////                "Doctor Not found."
////            )
//
//            notFoundDoctorCall()
//        }
//    }

    private fun getResponse() {
        lifecycleScope.launch {
            viewModel._doctorStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    doctorNotFound = false
                    foundDoctorCall(data)
                }
            }
        }
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    //Log.e("networkResult", "onFailure: "+errorStr)
                    if (!errorStr.isNullOrEmpty()) {
                        if (errorStr == "422") {
                            notFoundDoctorCall()
                        } else {
                            Toast.makeText(this@CallActivityKotlin, errorStr, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    //=====================================================================
    private fun notFoundDoctorCall() {
        try {
            if (releaseDoctorID.isNotEmpty() && !isCallRejectedFORCE) {
                Timber.e("Api == callPostDoctorManage")
                callPostDoctorManage(releaseDoctorID, doctorModel!!.name)
            }

            isCallRejectedFORCE = false
            releaseDoctorID = ""
            doctorModel = null

            //releaseDoctorID = doctorModel.getUuid();
            doctorManager.handlerRecallDoctorTime {
                reTryFoundDoctor()
            }


        } catch (exception: Exception) {
            Timber.e("Error:$exception")
           // sendApiLogErrorCodeScope(exception)
        }
    }

    private fun foundDoctorCall(responseDoctor: ResponseSingleDoctor?) {
        if (responseDoctor == null) {
            Timber.e("Error: responseDoctorList null")
            //closeCallUi();
            return
        }
        Timber.e("responseDoctor found")
        doctorModel = responseDoctor.doctor

        doctorModel?.let {

            SocketKey.RECEIVER_ID = it.getUuid()
            setDoctorName(it.getName(), it.getIsPushCall())
            callOffer()
            if (releaseDoctorID.isEmpty()) {
                releaseDoctorID = it.getUuid()
                return
            }
            Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
            if (isCallRejectedFORCE) {
                isCallRejectedFORCE = false
            } else {
                Timber.e("Api == callPostDoctorManage")
                callPostDoctorManage(releaseDoctorID, it.getName())
            }
            releaseDoctorID = it.getUuid()
        }
    }


    private fun setDoctorName(name: String, isPushCall: Boolean) {
        runOnUiThread {
            binding.llSolarUi.apply {
                //tvSelectedDoctor!!.text = name
                if (!isPushCall) {
                    tvSearchDoctor!!.text = AppKey.Calling_to + name
                } else {
                    val messageDoctorNotFound =
                        "All channels are busy now,\nplease wait or try again after some time."
                    if (doctorNotFound) {
                        tvSearchDoctor!!.text = messageDoctorNotFound
                    } else {
                        tvSearchDoctor!!.text = AppKey.Searching_doctor
                    }
                }
            }
            binding.tvDoctorName!!.text = name

        }
    }

    //===========================doctor random selection=============================


    fun reTryFoundDoctor() {
        if (!NetworkUtils.isConnected()) {
            ToastUtil.showToastMessage(AppKey.ERROR_INTERNET_CONNECTION)
        }

        doctorManager.closeHandlerRecallDoctorTime()
        doctorManager.closeHandlerPresencesCheckTime()
        doctorManager.closeHandlerRingingTime()
        doctorManager.closeHandlerConnectingTime()

        if (!isCallReceived) {
            setDoctorName("", true)
        }
        Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
        doctorApiCall
    }


    private fun closeHandlerCallDurationStop() {
        Timber.i("callDurationCalculate Stop")
        //  callDurationCalculateJob.cancel()

        if (timerHandler != null) {
            Timber.i("handlerConnectingTime closeHandler")
            timerHandler.removeCallbacksAndMessages(null)
        }
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
//        sendApiLog(
//            uiName,
//            AppKeyLog.DOCTOR_STATUS_UPDATE,
//            AppKeyLog.ENDPOINT_TYPE_API,
//            AppUrl.URL_STATUS_UPDATE,
//            "Update Doctor status online. DoctorName: $doctorName, DoctorId: $releaseDoctorID"
//        )


        viewModel.updateDoctorStatus(releaseDoctorID, "online")
//        ApiManager.statusUpdate(releaseDoctorID, "online", object : IApiResponse {
//            override fun <T> onSuccess(model: T) {
//                try {
//                    val commonResponse = model as CommonResponse
//                    if (commonResponse.isSuccess) {
//
//                        /*XmppKey.USER_RECEIVER_ID = XmppKey.getReceiverId(doctorID);
//                        tvSelectedDoctor.setText(doctorName);
//                        tvDoctorName.setText(doctorName);
//                        callOffer();*/
//                    }
//                } catch (exception: Exception) {
//                    Timber.e("Error:$exception")
//                    //sendApiLogErrorCodeScope(exception)
//                }
//            }
//
//            override fun onFailed(message: String) {}
//        })
    }

    //===========================ui init============================
    private fun uiInit() {
        binding.LocalSurfaceView.apply {
            init(UtilWerRtc.getEglBase().eglBaseContext, null)
            setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
            setMirror(true)
            setEnableHardwareScaler(false /* enabled */)
        }
        binding.RemoteSurfaceView.apply {
            init(UtilWerRtc.getEglBase().eglBaseContext, null)
            setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL)
            setMirror(true)
            setEnableHardwareScaler(true /* enabled */)
            //mRemoteSurfaceView.setZOrderMediaOverlay(true);
        }

        //  mRemoteSurfaceView.setOnTouchListener(onTouchListener);

        //  llDrag.setOnDragListener(onDragListener);

        binding.activityHangUpButton.setOnClickListener(View.OnClickListener {
            if (callManager == null) {
                return@OnClickListener
            }
//            logEventWithNumber(
//                AppKey.EVENT_CALL_END_CLICK,
//                JsonUtil.getJsonStringFromObject(doctorModel)
//            )
            callManager!!.doEndCall()
            Timber.e("===closeCallUi===")
            closeCallUi()
        })


        binding.fbVideoOn.setOnClickListener { setCameraOnOff() }
        binding.fbMute.setOnClickListener { setMicOnOff() }

        //========================================================
        callManager!!.setProxyVideoSink(binding.LocalSurfaceView)
        callManager!!.createVideoTrack(isVideoCall)
        //========================================================


//        setLoudSpeakerOnOff();
        binding.fbLoudSpeaker.setOnClickListener {
            callAudioManager.setLoudSpeakerOnOff()
        }
    }

    private fun callOffer() {
        if (callManager == null) {
            Timber.e("callManager == null")
            return
        }
        isRinging = true
        Timber.e("==========Doctor FOUND =================================================")
        Timber.e("NAME : " + doctorModel!!.name + " | UUID : " + doctorModel!!.uuid)
        Timber.e("=======================================================================")
        val callType =
            if (isVideoCall) SocketKey.KEY_TYPE_VIDEO_PERSONAL_CODE else SocketKey.KEY_TYPE_AUDIO_PERSONAL_CODE
        callManager!!.callOffer(doctorModel!!.uuid, callType, false)

        doctorManager.startPresencesCheckTimer {
            reTryFoundDoctor()
        }
    }

    //==============================================================================================
//    @AfterPermissionGranted(CallKey.RC_CALL)
    private fun start() {
//        val perms = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
//        if (EasyPermissions.hasPermissions(this, *perms)) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            uiInit()
//        } else {
//            EasyPermissions.requestPermissions(
//                this,
//                "Need some permissions",
//                CallKey.RC_CALL,
//                *perms
//            )
//        }
    }


    private fun closeFreeCallTimer() {

        Timber.i("closeFreeCallTimer")
        if (callManager == null) {
            return
        }

//        logEventWithNumber(
//            AppKey.EVENT_CALL_END_CLICK,
//            JsonUtil.getJsonStringFromObject(doctorModel)
//        )

        callManager!!.doEndCall()


        /* // create bitmap screen capture
         // View v1 = activity.getWindow().getDecorView().getRootView();
         binding.llMain!!.isDrawingCacheEnabled = true
         val bitmap = Bitmap.createBitmap(binding.llMain!!.drawingCache)
         binding.llMain!!.isDrawingCacheEnabled = false*/

//        sendApiLog(
//            uiName,
//            AppKeyLog.FORCE_FREE_CALL_END,
//            AppKeyLog.NA,
//            AppKeyLog.NA,
//            "Force call stop and send CALL_END"
//        )

        //=====================================================================================

        Timber.e("Free call warning show and call end")
//        freeCallManager.showFreeCallWarningDialog {
//            Timber.e("===Call->goBuyPack===")
//            val data = Intent()
//            data.putExtra(AppKey.INTENT_UI, AppKey.UI_BUY_PACK)
//            setResult(AppKey.RESULT_CODE_BUY_PACK, data)
//
//            //-------------------------------------------
//            WrtcSignalManager.removeSignalEventListener()
//            isFinish = true
//
//            finish()
//            Timber.e("===finish===")
//        }
        //=====================================================================================

        //==============================
        doctorManager.closeHandlerRecallDoctorTime()
        doctorManager.closeHandlerConnectingTime()
        doctorManager.closeHandlerPresencesCheckTime()
        doctorManager.closeHandlerRingingTime()
        closeHandlerCallDurationStop()

        binding.LocalSurfaceView.release()
        binding.RemoteSurfaceView.release()

        if (callManager != null) {
            callManager!!.socketDisConnect()
            callManager!!.onDestroy()
            callManager = null
        }
    }

    //=======================================================================================
    //=======================================================================================
    private val mPeerConnectionObserver: PeerConnection.Observer =
        object : PeerConnection.Observer {
            override fun onSignalingChange(signalingState: PeerConnection.SignalingState) {
                Timber.e("onSignalingChange: $signalingState")
            }

            override fun onIceConnectionChange(iceConnectionState: PeerConnection.IceConnectionState) {
                Timber.e("onIceConnectionChange: $iceConnectionState")
//                sendSocketLog(
//                    uiName,
//                    iceConnectionState.name,
//                    AppKeyLog.WEBRTC,
//                    "Web-Rtc status. " + doctorInfoMessage
//                )
                if (iceConnectionState.name == "CONNECTED") {
                    binding.rlSolarView.isVisible = false

                    callAudioManager.releasePlayerWelcomeTone()

                    callConnected(false)

//                    if (!isFreeCall) {
//                        return
//                    }
//
//                    freeCallManager.checkAvailable()


                    /*   if (isVideoCall()) {
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

            override fun onIceGatheringChange(iceGatheringState: PeerConnection.IceGatheringState) {
                Timber.e("onIceGatheringChange: $iceGatheringState")
            }

            override fun onIceCandidate(iceCandidate: IceCandidate) {
                Timber.e("onIceCandidate: $iceCandidate")
                callManager!!.onIceCandidate(iceCandidate)
            }

            override fun onIceCandidatesRemoved(iceCandidates: Array<IceCandidate>) {
                callManager!!.removeIceCandidates(iceCandidates)
            }

            override fun onAddStream(mediaStream: MediaStream) {
                Timber.e("onAddStream: " + mediaStream.videoTracks.size)
                /*   if(mediaStream.audioTracks.size() > 0) {
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
                callManager!!.onAddTrack(rtpReceiver, mediaStreams, binding.RemoteSurfaceView)
            }
        }

    private fun reConnection() {
        Timber.e("reConnection:...")
        if (NetUtil.isNetworkAvailable()) {
            if (callManager != null) callManager!!.reTryIceConnection()
        } else {
            AppHandler.getUiHandlerNew()
                .postDelayed({ if (callManager != null) callManager!!.reTryIceConnection() }, 2000)
        }
    }

    //==================================================================================================
    //===============================================================================================================
    private val mICallUiListener: ICallUiListener = object : ICallUiListener {
        /*   @Override
        public void onBroadcastReceived(String message) {

            Timber.e("onBroadcastReceived: " + message);

        }*/
        override fun onPreOfferAnswer(preOfferAnswerSocket: PreOfferAnswerSocket) {
            when (preOfferAnswerSocket.getPreOfferAnswer()) {
                SocketKey.KEY_TYPE_CALLEE_NOT_FOUND -> {
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALLEE_NOT_FOUND,
//                        "Doctor not connected with Socket."
//                    )
                    isCallNotFound = true
                    Timber.e(" CALLEE_NOT_FOUND:$isCallNotFound")

                    doctorManager.handlerCalleeNotFound {
                        reTryFoundDoctor()
                    }

                }

                SocketKey.KEY_TYPE_CALLEE_NO_ANSWER -> {
                    Timber.e("======reTryFoundDoctor====>> CALLEE_NO_ANSWER")
//                    logEventWithNumber(
//                        AppKey.EVENT_CALL_NO_ANSWER,
//                        doctorModel!!.name,
//                        doctorModel!!.phoneNumber
//                    )
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALLEE_NO_ANSWER,
//                        "Doctor Not Receive. $doctorInfoMessage"
//                    )
                    reTryFoundDoctor()
                }

                SocketKey.KEY_CALL_REJECTED_FORCE -> {
                    isCallRejectedFORCE = true
                    Timber.e("isCallRejectedFORCE:: $isCallRejectedFORCE")
                    Timber.e("======reTryFoundDoctor====>> CALL_REJECTED_FORCE")

                    reTryFoundDoctor()

//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_CALL_REJECTED_FORCE,
//                        "Doctor Go offline or Busy.  $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_CALL_ENDED -> {
                    closeCallUi()
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_ENDED,
//                        "Doctor Call End.  $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_CALL_ACCEPTED -> {
                    Timber.e("=========KEY_TYPE_CALL_ACCEPTED========")
                    doctorManager.closeHandlerPresencesCheckTime()
                    doctorManager.closeHandlerRingingTime()
                    //callReceived();
                    isCallReceived = true
                    callAudioManager.releasePlayerWelcomeTone()
                    runOnUiThread { binding.rlSolarView.isVisible = false }

                    callConnected(true)

                    binding.rrAppbar.visibility = View.VISIBLE
                    binding.rrAppbar.isVisible = true
                    binding.llCallEnd.isVisible = true

//                    val url = LocalData.getImgBaseUrl() + doctorModel!!.image
//                    Timber.e("doctor image::: $url")

//                    ImageLode.lodeImage(
//                        binding.imgProfile,
//                        url,
//                        bio.medico.patient.assets.R.drawable.ic_doctor_placeholder
//                    )

//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_ACCEPTED,
//                        "Doctor Call Receive. $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_CALL_REJECTED -> {
                    isRinging = false
                    Timber.e("======reTryFoundDoctor====>> CALL_REJECT")
                    reTryFoundDoctor()
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_REJECTED,
//                        "Doctor Call Reject. $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_VIDEO_OFF -> {
                    Timber.e("======Doctor VIDEO_OFF====>> ")
                    isDoctorMakeAudioCAll = true
                    binding.rlAudioLayout.isVisible = true
                    setCameraOnOff(true)
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_VIDEO_OFF,
//                        "Users Video Off by Doctor.  $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_VIDEO_ON -> {
                    Timber.e("======Doctor VIDEO_ON====>> ")
                    isDoctorMakeAudioCAll = false
                    setCameraOnOff(false)
                    binding.rlAudioLayout.isVisible = false
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_VIDEO_ON,
//                        "Users Video On by Doctor..  $doctorInfoMessage"
//                    )
                }

                else -> {
                    Timber.e("onPreOfferAnswer: default:" + preOfferAnswerSocket.getPreOfferAnswer())
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        preOfferAnswerSocket.getPreOfferAnswer(),
//                        " Socket key not found. $doctorInfoMessage"
//                    )
                }
            }
        }

        override fun onWebRtcSignaling(signalingOfferSocket: SignalingOfferSocket) {
            when (signalingOfferSocket.type) {
                SocketKey.KEY_TYPE_OFFER -> {
                    callManager!!.onRemoteOfferReceivedSocket(
                        signalingOfferSocket,
                        doctorModel!!.uuid
                    )
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_OFFER,
//                        "Receive Call Offer. $doctorInfoMessage"
//                    )
                }

                SocketKey.KEY_TYPE_CANDIDATE -> {
                    callManager!!.onRemoteCandidateReceivedSocket(signalingOfferSocket)
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        AppKeyLog.KEY_TYPE_CANDIDATE,
//                        "Receive CANDIDATE. $doctorInfoMessage"
//                    )
                }

                else -> {
                    Timber.e("onWebRtcSignaling: default:" + signalingOfferSocket.type)
//                    sendSocketLog(
//                        uiName,
//                        AppKeyLog.RECEIVE_SOCKET,
//                        signalingOfferSocket.type,
//                        "Receive socket data but Socket key not found. $doctorInfoMessage"
//                    )
                }
            }
        }
    }

    private fun callConnected(isNotIceConnect: Boolean) {
//        logEventWithNumber(
//            AppKey.EVENT_CALL_CONNECTED,
//            JsonUtil.getJsonStringFromObject(doctorModel)
//        )

        doctorManager.closeHandlerConnectingTime()

        runBlocking(Dispatchers.Main) {
            binding.llDrag.isVisible = true
            binding.llCallEnd.isVisible = true
        }

        if (isNotIceConnect) {
            startTime = System.currentTimeMillis()
            //callDurationCalculateJobStart()
             timerHandler!!.postDelayed(timerRunnable, 0)

            //freeCallManager.startFreeCallTimer()
        }
    }

    //=================================================================================
    //==------------------------------------------------------------------------
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onResume() {
        WrtcSignalManager.setSignalEventListener(mICallUiListener)
        Timber.d("onResume")
        if (callManager != null) {
            AppHandler.getUiHandlerNew().postDelayed({ callManager!!.startCapture() }, 100)
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (callManager != null) {
            callManager!!.stopCapture()
        }
    }

    override fun onDestroy() {
        forceCloseUi(true)
        closeAllHandler()
        super.onDestroy()
    }

    override fun onBackPressed() {}
    private fun closeCallUi() {
        doctorManager.closeHandlerRecallDoctorTime()
        doctorManager.closeHandlerConnectingTime()
        doctorManager.closeHandlerPresencesCheckTime()
        doctorManager.closeHandlerRingingTime()

        binding.LocalSurfaceView!!.release()
        binding.RemoteSurfaceView!!.release()
        if (callManager != null) {
            callManager!!.socketDisConnect()
            callManager!!.onDestroy()
        }
        callManager = null
        WrtcSignalManager.removeSignalEventListener()
        closeHandlerCallDurationStop()
        isFinish = true
        finish()
        Timber.e("===finish===")
    }

    private fun closeAllHandler() {
        doctorManager.closeHandlerCalleeNotFound()
        doctorManager.closeHandlerRecallDoctorTime()
        doctorManager.closeHandlerConnectingTime()
        doctorManager.closeHandlerPresencesCheckTime()
        doctorManager.closeHandlerRingingTime()
    }

    //===============================================================================================================
    private fun setMicOnOff() {
        if (callManager!!.isMicrophoneOn) {
            callManager!!.setMicrophoneOnOff(false)
            //binding.fbMute.setImageDrawable(AppRes.getDrawable(bio.medico.patient.assets.R.drawable.ic_mic_off, this))
        } else {
            callManager!!.setMicrophoneOnOff(true)
           // binding.fbMute.setImageDrawable(AppRes.getDrawable(bio.medico.patient.assets.R.drawable.ic_mic_on, this))
        }
    }

    private fun setCameraOnOff() {
        if (isDoctorMakeAudioCAll) {
            Timber.e("isDoctorMakeAudioCAll:$isDoctorMakeAudioCAll")
            return
        }
        if (callManager == null) {
            Timber.d("callManager == null")
            return
        }
        if (callManager!!.isCameraOn) {
            callManager!!.setCameraOnOff(false)
           // binding.fbVideoOn!!.setImageDrawable(AppRes.getDrawable(bio.medico.patient.assets.R.drawable.ic_video_camera_off, this))
        } else {
            callManager!!.setCameraOnOff(true)
           // binding.fbVideoOn!!.setImageDrawable(AppRes.getDrawable(bio.medico.patient.assets.R.drawable.ic_video_camera_on, this))
        }
    }

    private fun setCameraOnOff(isOnOff: Boolean) {
        if (isOnOff) {
            callManager!!.setCameraOnOff(false)
           // binding.fbVideoOn!!.setImageDrawable(AppRes.getDrawable(bio.medico.patient.assets.R.drawable.ic_video_camera_off, this))
        } else {
            setCameraOnOff()
        }
    }


    /*  private boolean isOn = false;

private void setLoudSpeakerOnOff() {
    isOn = !isOn;

    if (mAudioManager == null) {
        mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    if (isOn) {
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
        binding.fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOn);
    } else {
        //Seems that this back and forth somehow resets the audio channel
        mAudioManager.setMode(AudioManager.MODE_NORMAL);
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);

        binding.fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOff);
    }

    Timber.e("setLoudSpeakerOnOff:: " + isOn);

    mAudioManager.setSpeakerphoneOn(isOn);

}

*/


    //==================================================================================
    private val isVideoCall: Boolean
        private get() = IntentUtil.getIntentBooleanValue(AppKey.INTENT_VIDEO_CALL, intent)
//    private val isFreeCall: Boolean
//        private get() = IntentUtil.getIntentBooleanValue(AppKey.INTENT_FREE_CALL, intent)
//    private val freeCallInfo: ResponseSubInfo.FreeCallInfo
//        private get() {
//            val freeCallInfoJson = IntentUtil.getIntentValue(AppKey.INTENT_FREE_CALL_INFO, intent)
//            return JsonUtil.getModelFromStringJson(
//                freeCallInfoJson,
//                ResponseSubInfo.FreeCallInfo::class.java
//            )
//        }

    companion object {


        fun goCallActivity(
            activity: Activity,
            isVideoCall: Boolean,
            isFreeCall: Boolean,
            freeCallInfo: ResponseSubInfo.FreeCallInfo?
        ) {
            if (!NetworkUtils.isConnected()) {
                Timber.e("No Internet Connection!")
                return
            }


            /*PeerConnectionFactory.InitializationOptions.builder(AppUtilConfig.getAppContext())
        .setFieldTrials("WebRTC-H264HighProfile/Enabled/");


PeerConnectionFactory.initialize();
        PeerConnectionFactory.initialize(options);*/

            Timber.e("isVideoCall:$isVideoCall")
            UiNavigation.isCallButtonClicked = true
            val intent = Intent(activity, CallActivityKotlin::class.java)
            intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall)
            intent.putExtra(AppKey.INTENT_FREE_CALL, isFreeCall)
            intent.putExtra(
                AppKey.INTENT_FREE_CALL_INFO,
                JsonUtil.getJsonStringFromObject(freeCallInfo)
            )
            //activity.startActivity(intent);
            activity.startActivityForResult(intent, AppKey.RESULT_CODE_BUY_PACK)
        }

        fun goCallActivity(activity: Activity, isVideoCall: Boolean) {
            if (!NetworkUtils.isConnected()) {
                Timber.e("No Internet Connection!")
                return
            }
            Timber.e("isVideoCall:$isVideoCall")
            UiNavigation.isCallButtonClicked = true
            val intent = Intent(activity, CallActivityKotlin::class.java)
            intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall)
            activity.startActivity(intent)
        }
    }
}