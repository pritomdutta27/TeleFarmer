package com.theroyalsoft.telefarmer.utils

import android.content.Context
import com.theroyalsoft.telefarmer.helper.AppKey
import com.theroyalsoft.telefarmer.model.PreOfferSocket
import com.theroyalsoft.telefarmer.model.ProxyVideoSink
import com.theroyalsoft.telefarmer.model.SignalingOfferSocket
import com.theroyalsoft.telefarmer.model.SimpleSdpObserver
import com.theroyalsoft.telefarmer.TeleFarmerApp
import org.webrtc.AudioTrack
import org.webrtc.CameraVideoCapturer
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.RtpReceiver
import org.webrtc.SessionDescription
import org.webrtc.SurfaceTextureHelper
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoCapturer
import org.webrtc.VideoTrack
import timber.log.Timber

/**
 * Created by Pritom Dutta on 31/7/23.
 */
class CallManager {
    private var mPeerConnection: PeerConnection? = null
    private val mPeerConnectionFactory: PeerConnectionFactory
    private val mSurfaceTextureHelper: SurfaceTextureHelper
    private var mVideoTrack: VideoTrack? = null
    private var mAudioTrack: AudioTrack? = null
    private var videoSink: ProxyVideoSink? = null
    private var mPeerConnectionObserver: PeerConnection.Observer? = null
    private val context: Context
    var socketManager: SocketManager? = null
    private var isVideoCall = false
    fun initUi(mPeerConnectionObserver: PeerConnection.Observer?) {
        this.mPeerConnectionObserver = mPeerConnectionObserver
    }

    //===============================================================================================
    fun setCameraOnOff(isCameraOn: Boolean) {
        mVideoTrack!!.setEnabled(isCameraOn)
    }

    fun isCameraOn(): Boolean {
        return mVideoTrack!!.enabled()
    }

    fun setMicrophoneOnOff(isMicrophoneOn: Boolean) {
        mAudioTrack!!.setEnabled(isMicrophoneOn)
    }

    fun isMicrophoneOn(): Boolean {
        return mAudioTrack!!.enabled()
    }

    //===============================================================================================
    fun createPeerConnection1() {
        if (mPeerConnection == null) {
            mPeerConnection = UtilWerRtc.createPeerConnection(
                mVideoTrack,
                mAudioTrack,
                mPeerConnectionFactory,
                mPeerConnectionObserver
            )
        }
    }

    fun createVideoTrack(isVideoCall: Boolean) {
        this.isVideoCall = isVideoCall
        val videoSource = mPeerConnectionFactory.createVideoSource(false)
        mVideoCapturer = UtilWerRtc.createVideoCapturer(context)
        mVideoCapturer!!.initialize(mSurfaceTextureHelper, context, videoSource.capturerObserver)
        //===============================================================================================
        mVideoTrack =
            mPeerConnectionFactory.createVideoTrack(UtilWerRtc.VIDEO_TRACK_ID, videoSource)
        mVideoTrack?.setEnabled(isVideoCall)
        mVideoTrack?.addSink(videoSink)
        val audioConstraints = MediaConstraints()

        // add all existing audio filters to avoid having echos
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googEchoCancellation",
                "true"
            )
        )
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googEchoCancellation2",
                "true"
            )
        )
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googDAEchoCancellation",
                "true"
            )
        )
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googTypingNoiseDetection",
                "true"
            )
        )
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair("googAutoGainControl", "true"))
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googAutoGainControl2",
                "true"
            )
        )
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googNoiseSuppression",
                "true"
            )
        )
        audioConstraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "googNoiseSuppression2",
                "true"
            )
        )
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair("googAudioMirroring", "false"))
        audioConstraints.mandatory.add(MediaConstraints.KeyValuePair("googHighpassFilter", "true"))
        val audioSource = mPeerConnectionFactory.createAudioSource(audioConstraints)
        mAudioTrack =
            mPeerConnectionFactory.createAudioTrack(UtilWerRtc.AUDIO_TRACK_ID, audioSource)
        mAudioTrack?.setEnabled(true)
    }

    fun stopCapture() {
        try {
            if (mVideoCapturer != null) {
                mVideoCapturer!!.stopCapture()
            }
        } catch (e: InterruptedException) {
            //ApiManager.sendApiLogErrorCodeScope(e)
            Timber.e("Error:$e")
        }
    }

    fun startCapture() {
        if (mVideoCapturer == null) {
            Timber.e("mVideoCapturer == null")
            return
        }
        try {
            mVideoCapturer!!.startCapture(
                VIDEO_RESOLUTION_WIDTH,
                VIDEO_RESOLUTION_HEIGHT,
                VIDEO_FPS
            )
        } catch (e: Exception) {
            Timber.e("Error:$e")
            //ApiManager.sendApiLogErrorCodeScope(e)
        }
    }

    fun setProxyVideoSink(mLocalSurfaceView: SurfaceViewRenderer?) {
        videoSink = ProxyVideoSink()
        videoSink?.setTarget(mLocalSurfaceView)
    }

    //=====================================================================================================================
    fun answerCallSocket(doctorUuid: String) {
        Timber.i("Answer New Incoming Call...")
        createPeerConnection1() // if not create
        val constraints = MediaConstraints()
        constraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        constraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo",
                if (isVideoCall) "true" else "false"
            )
        )

        ///-------------------------------------------------------
        mPeerConnection!!.createAnswer(object : SimpleSdpObserver() {
            override fun onCreateSuccess(sessionDescription: SessionDescription) {
                Timber.i("Create answer success !")
                mPeerConnection!!.setLocalDescription(SimpleSdpObserver(), sessionDescription)

                Timber.e("SDP1:" + sessionDescription.description)
                val sdp = sdpModify(sessionDescription.description)
                Timber.e("SDP2:$sdp")
                val json = SignalingOfferSocket(
                    SocketKey.KEY_TYPE_ANSWER,
                    "",
                    sdp,
                    "",
                    LocalData.getUserUuid(),
                    doctorUuid
                )
                val signalingOfferSocketPayloadJson: String = JsonUtil.getJsonStringFromObject(json)
                Timber.e("-------------------------------------------------------")
                Timber.e("sessionDescription:" + JsonUtil.getJsonStringFromObject(sessionDescription))
                Timber.e("-------------------------------------------------------")
                socketManager?.sendData(
                    SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
                    signalingOfferSocketPayloadJson
                )

                //---------------------------------------------------------------
                val message =
                    "Send \"ANSAWER\" doctor call offer. DoctorId:$doctorUuid"
//                ApiManager.sendApiLog(
//                    AppKeyLog.UI_CALL,
//                    AppKeyLog.SEND_SOCKET,
//                    AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
//                    SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
//                    message
//                )
                //-----------------------------------------------------------------
            }
        }, constraints)
    }

    private fun sdpModify(sdp: String): String {
        val splitSdp = sdp.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        var sdpWithBitRate = ""
        val bitRate = ";x-google-max-bitrate=500;x-google-min-bitrate=72;x-google-start-bitrate=72"
        for (position in splitSdp.indices) {
            Timber.e("SDP_0:" + splitSdp[position])
            if (splitSdp[position].contains("a=fmtp")) {
                splitSdp[position] = splitSdp[position] + bitRate
                Timber.e("SDP_1:" + splitSdp[position])
            } else if (splitSdp[position].contains("a=mid:1") || splitSdp[position].contains("a=mid:video)")) {
                splitSdp[position] = """
                    ${splitSdp[position]}
                    b=AS:500
                    """.trimIndent()
                Timber.e("SDP_2:" + splitSdp[position])
            } else {
                Timber.e("SDP_3:" + splitSdp[position])
            }
            splitSdp[position] = """
                ${splitSdp[position]}
                
                """.trimIndent()
            sdpWithBitRate = sdpWithBitRate + splitSdp[position]
        }
        if (sdp == sdpWithBitRate) {
            Timber.e("sdpWithBitRate same")
        } else {
            Timber.e("sdpWithBitRate not same")
        }
        return sdpWithBitRate
    }

    fun onIceCandidate(iceCandidate: IceCandidate) {
        val payload =
            IceCandidateSocket(iceCandidate.sdp, iceCandidate.sdpMLineIndex, iceCandidate.sdpMid)
        val jsonStringFromObject: String = JsonUtil.getJsonStringFromObject(payload)
        val signalingOfferSocket = SignalingOfferSocket(
            SocketKey.KEY_TYPE_CANDIDATE,
            "",
            "",
            jsonStringFromObject,
            LocalData.getUserUuid(),
            SocketKey.RECEIVER_ID
        )
        val signalingoffersocketpayloadjosn: String =
            JsonUtil.getJsonStringFromObject(signalingOfferSocket)
        Timber.e("sendMessage iceCandidate::$signalingoffersocketpayloadjosn")
        socketManager?.sendData(
            SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
            signalingoffersocketpayloadjosn
        )

        //---------------------------------------------------------------
        val message = "Send WebRtc-ICE_CANDIDATE. DoctorId:" + SocketKey.RECEIVER_ID
//        ApiManager.sendApiLog(
//            AppKeyLog.UI_CALL,
//            AppKeyLog.SEND_SOCKET,
//            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
//            SocketKey.LISTENER_DATA_WEBRTC_SIGNALING,
//            message
//        )
        //-----------------------------------------------------------------
    }

    fun removeIceCandidates(iceCandidates: Array<IceCandidate>) {
        for (i in iceCandidates.indices) {
            Timber.e("onIceCandidatesRemoved: " + iceCandidates[i])
        }
        mPeerConnection!!.removeIceCandidates(iceCandidates)
    }

    fun onAddTrack(
        rtpReceiver: RtpReceiver,
        mediaStreams: Array<MediaStream>,
        mRemoteSurfaceView: SurfaceViewRenderer?
    ) {
        val track = rtpReceiver.track()
        if (track is VideoTrack) {
            Timber.e("onAddVideoTrack")
            val videoSink = ProxyVideoSink()
            videoSink.setTarget(mRemoteSurfaceView)
            val remoteVideoTrack = track
            remoteVideoTrack.setEnabled(true)
            remoteVideoTrack.addSink(videoSink)
        }
    }

    fun onRemoteOfferReceivedSocket(
        signalingOfferSocket: SignalingOfferSocket,
        doctorUuid: String
    ) {
        Timber.e("Receive Remote Call ...")
        createPeerConnection1()
        mPeerConnection!!.setRemoteDescription(
            SimpleSdpObserver(),
            SessionDescription(SessionDescription.Type.OFFER, signalingOfferSocket.getOffer())
        )
        answerCallSocket(doctorUuid)
    }

    private var remoteIceCandidate: IceCandidate? = null
    fun onRemoteCandidateReceivedSocket(signalingOfferSocket: SignalingOfferSocket) {
        Timber.e("Receive Remote Candidate ...")
        Timber.e("signalingOfferSocket.getCandidate() :: " + signalingOfferSocket.getCandidate())
        createPeerConnection1()
        val iceCandidate: IceCandidateSocket = JsonUtil.getModelFromStringJson(
            signalingOfferSocket.getCandidate(),
            IceCandidateSocket::class.java
        )
        if (iceCandidate == null) {
            Timber.e("iceCandidate==null")
            return
        }
        remoteIceCandidate = IceCandidate(
            iceCandidate.getSDPMid(),
            iceCandidate.getSDPMLineIndex(),
            iceCandidate.getSDP()
        )
        Timber.e("onRemoteCandidateReceivedSocket::: " + iceCandidate.getSDP())
        Timber.e("onRemoteCandidateReceivedSocket::: getSDPMLineIndex" + iceCandidate.getSDPMLineIndex())
        Timber.e("onRemoteCandidateReceivedSocket::: getSDPMid" + iceCandidate.getSDPMid())
        mPeerConnection!!.addIceCandidate(remoteIceCandidate)
    }

    fun reTryIceConnection() {
        if (mPeerConnection != null && remoteIceCandidate != null) {
            mPeerConnection!!.addIceCandidate(remoteIceCandidate)
        }
    }

    //---------------------------------------------------------------------------------------------------
    fun doEndCall() {
        val preOfferAnswerSocket = PreOfferAnswerSocket(
            SocketKey.KEY_TYPE_CALL_ENDED,
            LocalData.getUserUuid(),
            SocketKey.RECEIVER_ID
        )
        val json: String = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket)
        socketManager?.sendData(SocketKey.LISTENER_PRE_OFFER_ANSWER, json)

        //---------------------------------------------------------------
        val message =
            "Send " + SocketKey.KEY_TYPE_CALL_ENDED + " Click Call End Button . DoctorId:" + SocketKey.RECEIVER_ID
//        ApiManager.sendApiLog(
//            AppKeyLog.UI_CALL,
//            AppKeyLog.SEND_SOCKET,
//            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
//            SocketKey.LISTENER_PRE_OFFER_ANSWER,
//            message
//        )
        //-----------------------------------------------------------------
    }

    fun doForceStopCall() {
        val preOfferAnswerSocket = PreOfferAnswerSocket(
            SocketKey.KEY_TYPE_CALL_HANGED_UP,
            LocalData.getUserUuid(),
            SocketKey.RECEIVER_ID
        )
        val json: String = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket)
        socketManager?.sendData(SocketKey.LISTENER_USER_HANGUP, json)
        //---------------------------------------------------------------
        val message =
            "Send " + SocketKey.KEY_TYPE_CALL_HANGED_UP + " in solar ui for force Stop Call & Click hangedUp Button . DoctorId:" + SocketKey.RECEIVER_ID
//        ApiManager.sendApiLog(
//            AppKeyLog.UI_CALL,
//            AppKeyLog.SEND_SOCKET,
//            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
//            SocketKey.LISTENER_USER_HANGUP,
//            message
//        )
        //-----------------------------------------------------------------
    }

    fun onDestroy() {
        try {
            mVideoCapturer!!.dispose()
            mSurfaceTextureHelper.dispose()
            PeerConnectionFactory.stopInternalTracingCapture()
            PeerConnectionFactory.shutdownInternalTracer()


            //=============================================================
            if (mPeerConnection == null) {
                return
            }
            mPeerConnection!!.close()
            mPeerConnection = null
        } catch (e: Exception) {
            Timber.e("Error:$e")
            //ApiManager.sendApiLogErrorCodeScope(e)
        }
    }

    private var preOfferSocketJson = ""
    private var alreadySend = false
    fun callOffer(doctorUuid: String, callType: String?) {
        val preOfferSocket = PreOfferSocket(
            LocalData.getUserName(),
            LocalData.getUserProfile(),
            AppKey.getTime1(),
            callType,
            LocalData.getUserUuid(),
            doctorUuid
        )
        if (socketManager?.isSocketConnected!!) {
            Timber.e("sendCallPreOffer socket already connected")
            sendCallPreOffer(JsonUtil.getJsonStringFromObject(preOfferSocket), doctorUuid)
            preOfferSocketJson = ""
            alreadySend = true
        } else {
            preOfferSocketJson = JsonUtil.getJsonStringFromObject(preOfferSocket)
            alreadySend = false
            Timber.e("Socket Not Connected!")
        }
    }

    private fun sendCallPreOffer(preOfferSocketJson: String, doctorUuid: String) {
        socketManager?.sendData(SocketKey.LISTENER_PRE_OFFER, preOfferSocketJson)

        //---------------------------------------------------------------
        val message = "Send  call preOffer sdp. DoctorId:$doctorUuid"
//        ApiManager.sendApiLog(
//            AppKeyLog.UI_CALL,
//            AppKeyLog.SEND_SOCKET,
//            AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER,
//            SocketKey.LISTENER_PRE_OFFER,
//            message
//        )
        //-----------------------------------------------------------------
    }

    fun resetOffer(offerJson: String) {
        Timber.d("resetOffer:$offerJson")
        preOfferSocketJson = ""
    }

    //=========================================================================================================
    private var iSocketConnection: SocketManager.ISocketConnection? =
        SocketManager.ISocketConnection { connection ->
            Timber.e("Socket Connection:$connection")
            Timber.e("preOfferSocketJson:$preOfferSocketJson")
            Timber.e("alreadySend:$alreadySend")
            if (!preOfferSocketJson.isEmpty() && !alreadySend) {
                Timber.e("sendCallPreOffer socket already connected")
                sendCallPreOffer(preOfferSocketJson, SocketKey.RECEIVER_ID)
                preOfferSocketJson = ""
                alreadySend = true
            }
        }

    init {
        context = TeleFarmerApp.appContext!!
        mPeerConnectionFactory =
            UtilWerRtc.createPeerConnectionFactory(UtilWerRtc.getEglBase(), context)
        mSurfaceTextureHelper = SurfaceTextureHelper.create(
            "CaptureThread",
            UtilWerRtc.getEglBase().getEglBaseContext()
        )
        socketManager = SocketManager.getSocketManager(iSocketConnection)
        socketManager?.socketConnect() //First time connected!
    }

    fun socketDisConnect() {
        socketManager?.disconnect()
        socketManager = null
        iSocketConnection = null
    }

    companion object {
        /*  private static final int VIDEO_RESOLUTION_WIDTH = 1280;
    private static final int VIDEO_RESOLUTION_HEIGHT = 720;
    private static final int VIDEO_FPS = 30;*/
        private const val VIDEO_RESOLUTION_WIDTH = 852
        private const val VIDEO_RESOLUTION_HEIGHT = 480
        private const val VIDEO_FPS = 30
        private var mVideoCapturer: VideoCapturer? = null
        private var callManager: CallManager? = null
        val instance: CallManager?
            get() {
                callManager = CallManager()
                return callManager
            }

        fun switchCamera() {
            if (mVideoCapturer == null) {
                Timber.e("videoCapturer == null")
                return
            }
            if (mVideoCapturer is CameraVideoCapturer) {
                val cameraVideoCapturer = mVideoCapturer as CameraVideoCapturer?
                cameraVideoCapturer!!.switchCamera(null)
            } else {
                // Will not switch camera, video capturer is not a camera
                Timber.e("Will not switch camera, video capturer is not a camera")
            }
        }
    }
}
