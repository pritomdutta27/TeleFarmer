package com.theroyalsoft.telefarmer.ui.view.activity.call

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.farmer.primary.network.model.doctor.Doctor
import com.farmer.primary.network.model.profile.ProfileModel
import com.farmer.primary.network.model.sockets.SocketPassData
import com.farmer.primary.network.model.sockets.SocketPreOffer
import com.farmer.primary.network.utils.NullRemoveUtil
import com.theroyalsoft.telefarmer.R
import com.theroyalsoft.telefarmer.databinding.ActivityCallTestBinding
import com.theroyalsoft.telefarmer.extensions.convertToHumanTime
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.helper.AppKey
import com.theroyalsoft.telefarmer.model.PreOfferSocket
import com.theroyalsoft.telefarmer.model.ProxyVideoSink
import com.theroyalsoft.telefarmer.model.SignalingOfferSocket
import com.theroyalsoft.telefarmer.sockethelper.SocketHandler
import com.theroyalsoft.telefarmer.utils.IceCandidateSocket
import com.theroyalsoft.telefarmer.utils.JsonUtil
import com.theroyalsoft.telefarmer.utils.PreOfferAnswerSocket
import com.theroyalsoft.telefarmer.utils.RTCSignalManager
import com.theroyalsoft.telefarmer.utils.SocketKey
import com.theroyalsoft.telefarmer.utils.SocketKeyChat
import com.theroyalsoft.telefarmer.utils.isGone
import com.theroyalsoft.telefarmer.utils.isVisible
import com.theroyalsoft.telefarmer.webrtc.MyPeerObserver
import com.theroyalsoft.telefarmer.webrtc.WebRTCClient
import dagger.hilt.android.AndroidEntryPoint
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import io.socket.client.Socket
import io.socket.client.Socket.EVENT_CONNECT_ERROR
import io.socket.client.Socket.EVENT_DISCONNECT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.RtpReceiver
import org.webrtc.SessionDescription
import org.webrtc.VideoTrack
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class CallTestActivity : AppCompatActivity(), WebRTCClient.Listener,
    RTCSignalManager.ICallUiListener {

    private val viewModel: CallViewModel by viewModels()
    private lateinit var binding: ActivityCallTestBinding

    private lateinit var mSocket: Socket
    private var profileModel: ProfileModel? = null
    private lateinit var mHandler: Handler
    private lateinit var toneMediaPlayer: MediaPlayer
    private var RECEIVER_ID: String? = ""
    private var doctor: Doctor? = null
    private var isVideoCall: Boolean = true


    @Inject
    lateinit var mWebRTCClient: WebRTCClient

    @Inject
    lateinit var pref: DataStoreRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isVideoCall = intent.getBooleanExtra("isVideoCall", true)

        mHandler = Handler(Looper.getMainLooper())
        socketConnection()

        initPlayer()
        initView()
        event()

        getResponseLogin()
        ifApiGetError()
    }

    private fun initView() {
        RTCSignalManager.setSignalEventListener(this)
        binding.apply {
            if (!isVideoCall) {
                toggleCameraButton.isGone()
                screenShareButton.isGone()
                switchCameraButton.isGone()
            }
            mWebRTCClient.listener = this@CallTestActivity
            lifecycleScope.launch(Dispatchers.Main) {
                mWebRTCClient.initLocalSurfaceView(localView, isVideoCall)
                mWebRTCClient.initRemoteSurfaceView(remoteView)
                mWebRTCClient.toggleAudio(false)
//                mWebRTCClient.toggleVideo(false)

            }
        }
    }


    private fun event() {
//        binding.ll.llCancel.setOnClickListener {
//            doForceStopCall()
//            mWebRTCClient.closeConnection()
//            finish()
//        }

//        binding.endCallButton.setOnClickListener {
//            doForceStopCall()
//            mWebRTCClient.closeConnection()
//            finish()
//        }
    }

    override fun onResume() {
        super.onResume()
        unlockScreen()
    }

    private fun unlockScreen() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
    }

    override fun onBackPressed() {}

    private fun socketConnection() {


        val socketBaseUrl = viewModel.getMetaData()?.socketBaseUrl
        profileModel = viewModel.getProfile()
        if (socketBaseUrl!!.isEmpty() && profileModel == null)
            return

        SocketHandler.setSocket(socketBaseUrl,profileModel?.uuid ?: "")
        SocketHandler.establishConnection()
        mSocket = SocketHandler.getSocket()

        mSocket.on(Socket.EVENT_CONNECT) { arg ->
            Timber.e("Socket: connect")
            Timber.e("Socket ID:" + mSocket.id())

            val socketId: String = NullRemoveUtil.getNotNull(mSocket.id())

            if (socketId.isEmpty()) {
                Timber.e("Socket Id Not found!")
                return@on
            }

            val userInfoSocket = SocketPassData(
                uuid = profileModel?.uuid ?: "",
                socketId = socketId,
                userName = profileModel?.name ?: "",
                userMobile = profileModel?.phoneNumber ?: "",
                userType = AppKey.USER_PATIENT,
                dateAndTime = AppKey.getTime1()
            )
            //mHandler.post { binding.ll.tvSearchDoctor.text = "Ringing..." }
            val userInfoSocketJson = JsonUtil.getJsonStringFromObject(userInfoSocket)
            SocketHandler.sendData(SocketKeyChat.LISTENER_USER_INFO, userInfoSocketJson)
            viewModel.fetchAvailableDoctor()

        }.on(EVENT_CONNECT_ERROR) { args ->
            Timber.e("SOCKET: ERROR disconnect");
            // ApiManager.sendApiLogEndpointSocket("SOCKET_CONNECTION", EVENT_CONNECT_ERROR, "SOCKET: ERROR disconnect");
            SocketHandler.closeConnection()
        }.on(EVENT_DISCONNECT) {
            //ApiManager.sendApiLogEndpointSocket("SOCKET_CONNECTION", EVENT_DISCONNECT, "SOCKET: disconnect");
            Timber.e("SOCKET: disconnect");
            SocketHandler.closeConnection()
        }.on(SocketKey.LISTENER_PRE_OFFER_ANSWER) { arg ->
            //ApiManager.sendApiLogEndpointSocket("SOCKET_CONNECTION", EVENT_DISCONNECT, "SOCKET: disconnect");

            RTCSignalManager.socketIncomingData.onSocketResponseData(SocketKey.LISTENER_PRE_OFFER_ANSWER, arg[0].toString())
            //onPreOfferManage(socketDataResponse)
            //Timber.e("SOCKET: disconnect ${socketDataResponse.preOfferAnswer}")


        }.on(SocketKey.LISTENER_DATA_WEBRTC_SIGNALING) { arg ->
            RTCSignalManager.socketIncomingData.onSocketResponseData(SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, arg[0].toString())

           // Timber.e("signalingOfferSocket: ${signalingOfferSocket.offer}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SocketHandler.closeConnection()
        releasePlayer()
        RTCSignalManager.removeSignalEventListener()
    }

    //===========================player setup=============================
    private fun initPlayer() {
        toneMediaPlayer = MediaPlayer.create(this, R.raw.caller_tone)
        toneMediaPlayer.isLooping = true
        toneMediaPlayer.start()
    }

    private fun releasePlayer() {
        if (toneMediaPlayer != null) {
            toneMediaPlayer.release()
        }
    }

    override fun onBroadcastReceived(message: String?) {
        TODO("Not yet implemented")
    }

    override fun onPreOfferAnswer(preOfferAnswerSocket: PreOfferAnswerSocket?) {
        when (preOfferAnswerSocket?.preOfferAnswer) {
            SocketKey.KEY_TYPE_CALLEE_NOT_FOUND -> {
                Timber.e(" CALLEE_NOT_FOUND: isCallNotFound")
                mHandler.postDelayed({
                    viewModel.fetchAvailableDoctor()
                }, AppKey.TIMER_NOT_FOUND_DOCTOR_API)
            }

            SocketKey.KEY_TYPE_CALLEE_NO_ANSWER -> {
                Timber.e("======reTryFoundDoctor====>> CALLEE_NO_ANSWER")
                mHandler.postDelayed({
                    viewModel.fetchAvailableDoctor()
                }, AppKey.TIMER_NOT_FOUND_DOCTOR_API)
            }

            SocketKey.KEY_CALL_REJECTED_FORCE -> {
                Timber.e("isCallRejectedFORCE:: isCallRejectedFORCE")
                Timber.e("======reTryFoundDoctor====>> CALL_REJECTED_FORCE")
                mHandler.postDelayed({
                    viewModel.fetchAvailableDoctor()
                }, AppKey.TIMER_NOT_FOUND_DOCTOR_API)
            }

            SocketKey.KEY_TYPE_CALL_REJECTED -> {
                Timber.e("======reTryFoundDoctor====>> CALL_REJECTE")
                mHandler.postDelayed({
                    viewModel.fetchAvailableDoctor()
                }, AppKey.TIMER_RECALL_DOCTOR_API)

            }

            SocketKey.KEY_TYPE_CALL_ENDED -> {
//                    ApiManager.sendApiLogEndpointSocket(
//                        AppKeyLog.RECEIVE_SOCKET,
//                        SocketKey.KEY_TYPE_CALL_ENDED,
//                        "Doctor Call End. " + doctorInfoMessage
//                    )
                runOnUiThread {
                    doEndCall()
                }
            }

            SocketKey.KEY_TYPE_CALL_ACCEPTED -> {
                runOnUiThread {
                    binding.apply {
                        llContainer.isGone()
                        clVideoContains.isVisible()
                        callTitleTv.text = "In call with ${doctor?.name ?: ""}"
                        CoroutineScope(Dispatchers.IO).launch {
                            for (i in 0..3600) {
                                delay(1000)
                                withContext(Dispatchers.Main) {
                                    //convert this int to human readable time
                                    callTimerTv.text = i.convertToHumanTime()
                                }
                            }
                        }
                    }
                    releasePlayer()
                }
            }
        }
    }


    override fun onWebRtcSignaling(signalingOfferSocket: SignalingOfferSocket) {
        when (signalingOfferSocket.type) {

            SocketKey.KEY_TYPE_OFFER -> {
                Timber.e("onWebRtcSignaling: KEY_TYPE_OFFER:" + signalingOfferSocket.offer)

                mWebRTCClient.onRemoteSessionReceived(
                    SessionDescription(SessionDescription.Type.OFFER, signalingOfferSocket.offer)
                )
                mWebRTCClient.answer(doctor?.uuid ?: "", profileModel?.uuid ?: "")
            }

            SocketKey.KEY_TYPE_CANDIDATE -> {
//                Timber.e("Receive Remote Candidate ...")
//                Timber.e("signalingOfferSocket.getCandidate() :: " + signalingOfferSocket.candidate)

                val iceCandidate: IceCandidateSocket? = JsonUtil.getModelFromStringJson(
                    signalingOfferSocket.candidate,
                    IceCandidateSocket::class.java
                )
                if (iceCandidate == null) {
                    Timber.e("iceCandidate==null")
                    return
                }
                val remoteIceCandidate: IceCandidate? = IceCandidate(
                    iceCandidate.sdpMid,
                    iceCandidate.sdpmLineIndex,
                    iceCandidate.sdp
                )
                remoteIceCandidate?.let {
                    Timber.e("Receive Remote Candidate ...")
                    Timber.e("signalingOfferSocket.getCandidate() :: " + it)
                    mWebRTCClient.addIceCandidateToPeer(it)
                }
            }

            else -> {
                Timber.e("onWebRtcSignaling: default:" + signalingOfferSocket.type)
            }
        }
    }

    // TODO: Pre-offers
    private fun preOfferDoctor(doctor: Doctor?) {

        if (mSocket == null && doctor == null) {
            return
        }
        this.doctor = doctor
        val preOfferSocket = PreOfferSocket(
            profileModel?.name,
            profileModel?.image,
            AppKey.getTime1(),
            if (isVideoCall) SocketKey.KEY_TYPE_VIDEO_PERSONAL_CODE else SocketKey.KEY_TYPE_AUDIO_PERSONAL_CODE,
            profileModel?.uuid,
            doctor?.uuid
        )
        RECEIVER_ID = doctor?.uuid
        val userInfoSocketJson = JsonUtil.getJsonStringFromObject(preOfferSocket)
        Timber.e("userInfoSocket: $userInfoSocketJson")
        SocketHandler.sendData(SocketKey.LISTENER_PRE_OFFER, userInfoSocketJson)

        viewModel.getMetaData()?.let { metaData ->
            mWebRTCClient.initializeWebrtcClient(
                profileModel?.uuid ?: "",
                metaData,
                object : MyPeerObserver() {

//                override fun onAddStream(p0: MediaStream?) {
//                    super.onAddStream(p0)
//                    try {
//                        p0?.videoTracks?.get(0)?.addSink(binding.remoteView)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                }

                    override fun onAddTrack(p0: RtpReceiver?, p1: Array<out MediaStream>?) {
                        p0?.track()?.let { track ->
                            if (track is VideoTrack) {
                                Timber.e("onAddVideoTrack")
                                val videoSink = ProxyVideoSink()
                                videoSink.setTarget(binding.remoteView)
                                track.setEnabled(true)
                                track.addSink(videoSink)
                            }
                        }
                    }


                    override fun onIceCandidate(p0: IceCandidate?) {
                        super.onIceCandidate(p0)
                        p0?.let {
                            val payload = IceCandidateSocket(
                                p0.sdp,
                                p0.sdpMLineIndex,
                                p0.sdpMid
                            )
                            val jsonStringFromObject = JsonUtil.getJsonStringFromObject(p0)

                            val signalingOfferSocket = SignalingOfferSocket(
                                SocketKey.KEY_TYPE_CANDIDATE,
                                "",
                                "",
                                jsonStringFromObject,
                                profileModel?.uuid,
                                doctor?.uuid
                            )
                            val signalingoffersocketpayloadjosn =
                                JsonUtil.getJsonStringFromObject(signalingOfferSocket)
                             mWebRTCClient.sendIceCandidate(signalingoffersocketpayloadjosn,p0)
                            Timber.e("sendMessage iceCandidate::$signalingoffersocketpayloadjosn")
//                            SocketHandler.sendData(
//                                SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
//                                signalingoffersocketpayloadjosn
//                            )
                        }
                    }

                    override fun onConnectionChange(newState: PeerConnection.PeerConnectionState?) {
                        super.onConnectionChange(newState)
                        if (newState == PeerConnection.PeerConnectionState.CONNECTED) {
                            Timber.e("ConnectionChange Connect")
                            // 1. change my status to in call
                            //                    changeMyStatus(UserStatus.IN_CALL)
                            //                    // 2. clear latest event inside my user section in firebase database
                            //                    firebaseClient.clearLatestEvent()
                        } else {
                            Timber.e("ConnectionChange Not Connect")
                        }
                    }
                })
        }
    }


    fun doEndCall() {
        val preOfferAnswerSocket = PreOfferAnswerSocket(
            SocketKey.KEY_TYPE_CALL_ENDED,
            profileModel?.uuid,
            SocketKey.RECEIVER_ID
        )
        val json: String = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket)
        SocketHandler.sendData(SocketKey.LISTENER_PRE_OFFER_ANSWER, json)
        mHandler.postDelayed({
            mWebRTCClient.closeConnection()
            finish()
        }, 400)
    }

    fun doForceStopCall() {
        val preOfferAnswerSocket = PreOfferAnswerSocket(
            SocketKey.KEY_TYPE_CALL_HANGED_UP,
            profileModel?.uuid,
            RECEIVER_ID
        )
        val json: String = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket)
        SocketHandler.sendData(SocketKey.LISTENER_USER_HANGUP, json)
        mHandler.postDelayed({
            finish()
        }, 400)
    }

    //TODO: Api
    private fun getResponseLogin() {
        lifecycleScope.launch {
            viewModel._doctorStateFlow.collect { data ->
                withContext(Dispatchers.Main) {
                    preOfferDoctor(data)
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
                            mHandler.postDelayed({
                                viewModel.fetchAvailableDoctor()
                            }, AppKey.TIMER_RECALL_DOCTOR_API)
                        } else {
                            this@CallTestActivity.showToast(errorStr)
                        }
                    }
                }
            }
        }
    }

    override fun onTransferEventToSocket(data: String) {
        SocketHandler.sendData(
            SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
            data
        )
    }

}