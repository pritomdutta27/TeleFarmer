package  com.theroyalsoft.telefarmer.webrtc

import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjection
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.farmer.primary.network.model.metadata.MetaModel
import com.google.gson.Gson
import com.theroyalsoft.telefarmer.model.SignalingOfferSocket
import com.theroyalsoft.telefarmer.utils.CallManager
import com.theroyalsoft.telefarmer.utils.JsonUtil
import com.theroyalsoft.telefarmer.utils.ModifySdp
import com.theroyalsoft.telefarmer.utils.SocketKey
import com.theroyalsoft.telefarmer.utils.UtilWerRtc
import org.webrtc.AudioTrack
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraVideoCapturer
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.IceServer
import org.webrtc.PeerConnection.RTCConfiguration
import org.webrtc.PeerConnectionFactory
import org.webrtc.ScreenCapturerAndroid
import org.webrtc.SessionDescription
import org.webrtc.SurfaceTextureHelper
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoCapturer
import org.webrtc.VideoTrack
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebRTCClient @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    //class variables
    var listener: Listener? = null
    private lateinit var username: String

    val VIDEO_TRACK_ID = "ARDAMSv0"
    val AUDIO_TRACK_ID = "ARDAMSa0"

    private val VIDEO_RESOLUTION_WIDTH = 852
    private val VIDEO_RESOLUTION_HEIGHT = 480
    private val VIDEO_FPS = 30

    //webrtc variables
    private val eglBaseContext = UtilWerRtc.getEglBase().eglBaseContext
    private val peerConnectionFactory by lazy { createPeerConnectionFactory() }
    private var peerConnection: PeerConnection? = null

    private var iceServer = emptyList<IceServer>()


    private val localVideoSource by lazy { peerConnectionFactory.createVideoSource(false) }
    private val localAudioSource by lazy {
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
        peerConnectionFactory.createAudioSource(audioConstraints)
    }
    private val videoCapturer = getVideoCapturer(context)
    private var surfaceTextureHelper: SurfaceTextureHelper? = null
    private val mediaConstraint = MediaConstraints().apply {
        mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
        mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
    }

    //call variables
    private lateinit var localSurfaceView: SurfaceViewRenderer
    private lateinit var remoteSurfaceView: SurfaceViewRenderer
    private var localStream: MediaStream? = null
    private var localTrackId = ""
    private var localStreamId = ""
    private var localAudioTrack: AudioTrack? = null
    private var localVideoTrack: VideoTrack? = null

    //screen casting
    private var permissionIntent: Intent? = null
    private var screenCapturer: VideoCapturer? = null
    private val localScreenVideoSource by lazy { peerConnectionFactory.createVideoSource(false) }
    private var localScreenShareVideoTrack: VideoTrack? = null

    //installing requirements section
    init {
        initPeerConnectionFactory()
    }

    private fun initPeerConnectionFactory() {
        val options = PeerConnectionFactory.InitializationOptions.builder(context)
            .setEnableInternalTracer(true)
            .createInitializationOptions()
        PeerConnectionFactory.initialize(options)
    }

    private fun getPeerConnectionIceServer(
        stunServer: List<String>,
        turnServer: List<String>,
        userName: String,
        password: String
    ): List<IceServer>? {
        val peerIceServers: MutableList<IceServer> = ArrayList()
        Timber.e("PeerConnectionIceServer: ${userName} ${password}")
        for (ss in stunServer) {
            peerIceServers.add(getPeerConnectionIceServer(ss, "", ""))
        }
        for (ts in turnServer) {
            peerIceServers.add(getPeerConnectionIceServer(ts, userName, password))
        }

        return peerIceServers
    }

    private fun getPeerConnectionIceServer(
        serverUrl: String,
        userName: String,
        password: String
    ): IceServer {
        return IceServer.builder(serverUrl)
            .setUsername(userName)
            .setPassword(password)
            .setTlsCertPolicy(PeerConnection.TlsCertPolicy.TLS_CERT_POLICY_INSECURE_NO_CHECK)
            .createIceServer()
    }

    private fun createPeerConnectionFactory(): PeerConnectionFactory {
        return PeerConnectionFactory.builder()
            .setVideoDecoderFactory(
                DefaultVideoDecoderFactory(eglBaseContext)
            ).setVideoEncoderFactory(
                DefaultVideoEncoderFactory(
                    eglBaseContext, false, true
                )
            ).setOptions(PeerConnectionFactory.Options().apply {
                disableNetworkMonitor = false
                disableEncryption = false
            }).createPeerConnectionFactory()
    }

    fun initializeWebrtcClient(
        username: String,metadata: MetaModel, observer: PeerConnection.Observer) {
        this.username = username
//        localTrackId = "${username}_track"
//        localStreamId = "${username}_stream"
        localTrackId = VIDEO_TRACK_ID
        localStreamId = AUDIO_TRACK_ID
        iceServer = getPeerConnectionIceServer(
            metadata.stunServer ?: emptyList(),
            metadata.turnServer ?: emptyList(),
            metadata.turnAuth?.user ?: "",
            metadata.turnAuth?.password ?: ""
        ) ?: emptyList()
        peerConnection = createPeerConnection(observer)
    }

//    private fun createPeerConnection(observer: PeerConnection.Observer): PeerConnection? {
//        return peerConnectionFactory.createPeerConnection(iceServer, observer)
//    }

    private fun createPeerConnection(observer: PeerConnection.Observer): PeerConnection? {

        val configuration = RTCConfiguration(iceServer)
        configuration.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.ENABLED
        configuration.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE
        configuration.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE
        configuration.continualGatheringPolicy =
            PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY
        configuration.keyType = PeerConnection.KeyType.ECDSA
        configuration.enableDtlsSrtp = true
        configuration.enableRtpDataChannel = true
        configuration.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN

        configuration.allowCodecSwitching = true

        return peerConnectionFactory.createPeerConnection(configuration, observer)
    }

    //negotiation section
    fun call(target: String) {
        peerConnection?.createOffer(object : MySdpObserver() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                super.onCreateSuccess(desc)
                peerConnection?.setLocalDescription(object : MySdpObserver() {
                    override fun onSetSuccess() {
                        super.onSetSuccess()
//                        listener?.onTransferEventToSocket(
//                            DataModel(type = DataModelType.Offer,
//                            sender = username,
//                            target = target,
//                            data = desc?.description)
//                        )
                    }
                }, desc)
            }
        }, mediaConstraint)
    }

    fun answer(target: String, userId: String) {

        val constraints = MediaConstraints()
        constraints.mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        constraints.mandatory.add(
            MediaConstraints.KeyValuePair(
                "OfferToReceiveVideo", "true"
            )
        )

        peerConnection?.createAnswer(object : MySdpObserver() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                super.onCreateSuccess(desc)

                peerConnection?.setLocalDescription(MySdpObserver(), desc)

                Timber.e("SDP1:" + desc?.description)
                val sdp = ModifySdp.sdpModify(desc?.description)
                Timber.e("SDP2:$sdp")
                val json = SignalingOfferSocket(
                    SocketKey.KEY_TYPE_ANSWER,
                    "",
                    sdp,
                    "",
                    userId,
                    target
                )
                val signalingOfferSocketPayloadJson: String =
                    JsonUtil.getJsonStringFromObject(json)
                Timber.e("-------------------------------------------------------")
                Timber.e("sessionDescription:" + JsonUtil.getJsonStringFromObject(desc))
                Timber.e("-------------------------------------------------------")

                listener?.onTransferEventToSocket(
                    signalingOfferSocketPayloadJson
                )
            }
        }, constraints)
    }

    fun onRemoteSessionReceived(sessionDescription: SessionDescription) {
        peerConnection?.setRemoteDescription(MySdpObserver(), sessionDescription)
    }

    fun addIceCandidateToPeer(iceCandidate: IceCandidate) {
        peerConnection?.addIceCandidate(iceCandidate)
    }

    fun sendIceCandidate(mSignalingOfferSocket: String, iceCandidate: IceCandidate) {
        addIceCandidateToPeer(iceCandidate)

        listener?.onTransferEventToSocket(
            mSignalingOfferSocket
        )
//        listener?.onTransferEventToSocket(
//            DataModel(
//                type = DataModelType.IceCandidates,
//                sender = username,
//                target = target,
//                data = gson.toJson(iceCandidate)
//            )
//        )
    }

    fun closeConnection() {
        try {
            videoCapturer.dispose()
            screenCapturer?.dispose()
            localStream?.dispose()
            peerConnection?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun switchCamera() {
        videoCapturer.switchCamera(null)
    }

    fun toggleAudio(shouldBeMuted: Boolean) {
        if (shouldBeMuted) {
            localStream?.removeTrack(localAudioTrack)
        } else {
            localStream?.addTrack(localAudioTrack)
        }
    }

    fun toggleVideo(shouldBeMuted: Boolean) {
        try {
            if (shouldBeMuted) {
                stopCapturingCamera()
            } else {
                startCapturingCamera(localSurfaceView)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //streaming section
    private fun initSurfaceView(view: SurfaceViewRenderer) {
        view.run {
            setMirror(false)
            setEnableHardwareScaler(true)
            init(eglBaseContext, null)
        }
    }

    fun initRemoteSurfaceView(view: SurfaceViewRenderer) {
        this.remoteSurfaceView = view
        initSurfaceView(view)
    }

    fun initLocalSurfaceView(localView: SurfaceViewRenderer, isVideoCall: Boolean) {
        this.localSurfaceView = localView
        initSurfaceView(localSurfaceView)
        startLocalStreaming(localSurfaceView, isVideoCall)
    }

    private fun startLocalStreaming(localView: SurfaceViewRenderer, isVideoCall: Boolean) {
        localStream = peerConnectionFactory.createLocalMediaStream(localStreamId)
        if (isVideoCall) {
            startCapturingCamera(localView)
        }

        localAudioTrack =
            peerConnectionFactory.createAudioTrack(localStreamId, localAudioSource)
        localAudioTrack?.setEnabled(true)

        localStream?.addTrack(localAudioTrack)

        peerConnection?.addStream(localStream)
    }

    private fun startCapturingCamera(localView: SurfaceViewRenderer) {
        surfaceTextureHelper = SurfaceTextureHelper.create(
            Thread.currentThread().name, eglBaseContext
        )

        videoCapturer.initialize(
            surfaceTextureHelper, context, localVideoSource.capturerObserver
        )

        videoCapturer.startCapture(
            VIDEO_RESOLUTION_WIDTH,
            VIDEO_RESOLUTION_HEIGHT,
            VIDEO_FPS
        )

        localVideoTrack =
            peerConnectionFactory.createVideoTrack(localTrackId, localVideoSource)
        localVideoTrack?.setEnabled(true)
        localVideoTrack?.addSink(localView)
        localStream?.addTrack(localVideoTrack)
    }

    private fun getVideoCapturer(context: Context): CameraVideoCapturer =
        Camera2Enumerator(context).run {
            deviceNames.find {
                isFrontFacing(it)
            }?.let {
                createCapturer(it, null)
            } ?: throw IllegalStateException()
        }

    private fun stopCapturingCamera() {

        videoCapturer.dispose()
        localVideoTrack?.removeSink(localSurfaceView)
        localSurfaceView.clearImage()
        localStream?.removeTrack(localVideoTrack)
        localVideoTrack?.dispose()
    }

    //screen capture section

    fun setPermissionIntent(screenPermissionIntent: Intent) {
        this.permissionIntent = screenPermissionIntent
    }

    fun startScreenCapturing() {
        val displayMetrics = DisplayMetrics()
        val windowsManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowsManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthPixels = displayMetrics.widthPixels
        val screenHeightPixels = displayMetrics.heightPixels

        val surfaceTextureHelper = SurfaceTextureHelper.create(
            Thread.currentThread().name, eglBaseContext
        )

        screenCapturer = createScreenCapturer()
        screenCapturer!!.initialize(
            surfaceTextureHelper, context, localScreenVideoSource.capturerObserver
        )
        screenCapturer!!.startCapture(screenWidthPixels, screenHeightPixels, 15)

        localScreenShareVideoTrack =
            peerConnectionFactory.createVideoTrack(localTrackId, localScreenVideoSource)
        localScreenShareVideoTrack?.addSink(localSurfaceView)
        localStream?.addTrack(localScreenShareVideoTrack)
        peerConnection?.addStream(localStream)

    }

    fun stopScreenCapturing() {
        screenCapturer?.stopCapture()
        screenCapturer?.dispose()
        localScreenShareVideoTrack?.removeSink(localSurfaceView)
        localSurfaceView.clearImage()
        localStream?.removeTrack(localScreenShareVideoTrack)
        localScreenShareVideoTrack?.dispose()

    }

    private fun createScreenCapturer(): VideoCapturer {
        return ScreenCapturerAndroid(permissionIntent, object : MediaProjection.Callback() {
            override fun onStop() {
                super.onStop()
                Log.d("permissions", "onStop: permission of screen casting is stopped")
            }
        })
    }


    interface Listener {
        fun onTransferEventToSocket(data: String)
    }
}