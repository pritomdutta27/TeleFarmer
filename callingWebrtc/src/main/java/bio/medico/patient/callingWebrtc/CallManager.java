package bio.medico.patient.callingWebrtc;

import android.content.Context;

import com.skh.hkhr.util.JsonUtil;
import com.theroyalsoft.mydoc.apputil.AppUtilConfig;
import com.theroyalsoft.mydoc.apputil.TimeUtil;

import org.webrtc.AudioSource;
import org.webrtc.AudioTrack;
import org.webrtc.CameraVideoCapturer;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.MediaStream;
import org.webrtc.MediaStreamTrack;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.RtpReceiver;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

import bio.medico.patient.common.AppKey;
import bio.medico.patient.common.AppKeyLog;
import bio.medico.patient.common.DeviceIDUtil;
import bio.medico.patient.data.local.LocalData;
import bio.medico.patient.data.ApiManager;
import bio.medico.patient.socketUtils.SocketKey;
import bio.medico.patient.socketUtils.SocketManager;
import bio.medico.patient.model.socket.IceCandidateSocket;
import bio.medico.patient.model.socket.PreOfferAnswerSocket;
import bio.medico.patient.model.socket.PreOfferSocket;
import bio.medico.patient.model.socket.SignalingOfferSocket;
import bio.medico.patient.callingWebrtc.model.ProxyVideoSink;
import bio.medico.patient.callingWebrtc.model.SimpleSdpObserver;

import timber.log.Timber;

public class CallManager {


  /*  private static final int VIDEO_RESOLUTION_WIDTH = 1280;
    private static final int VIDEO_RESOLUTION_HEIGHT = 720;
    private static final int VIDEO_FPS = 30;*/

    private static final int VIDEO_RESOLUTION_WIDTH = 852;
    private static final int VIDEO_RESOLUTION_HEIGHT = 480;
    private static final int VIDEO_FPS = 30;

    private PeerConnection mPeerConnection;
    private PeerConnectionFactory mPeerConnectionFactory;

    private SurfaceTextureHelper mSurfaceTextureHelper;


    private VideoTrack mVideoTrack;
    private AudioTrack mAudioTrack;

    private static VideoCapturer mVideoCapturer;

    private ProxyVideoSink videoSink;


    private PeerConnection.Observer mPeerConnectionObserver;

    private static CallManager callManager;

    private Context context;

    SocketManager socketManager = null;

    private boolean isVideoCall;
    private final String uiName = AppKeyLog.UI_CALL;

    public static CallManager getInstance() {
        callManager = new CallManager();
        return callManager;
    }

    public CallManager() {
        this.context = AppUtilConfig.getAppContext();
        mPeerConnectionFactory = UtilWerRtc.createPeerConnectionFactory(UtilWerRtc.getEglBase(), context);
        mSurfaceTextureHelper = SurfaceTextureHelper.create("CaptureThread", UtilWerRtc.getEglBase().getEglBaseContext());
        socketManager = SocketManager.getSocketManager(iSocketConnection);
        socketManager.socketConnect();//First time connected!
    }

    public void initUi(PeerConnection.Observer mPeerConnectionObserver) {
        this.mPeerConnectionObserver = mPeerConnectionObserver;
    }


    //===============================================================================================
    public void setCameraOnOff(boolean isCameraOn) {
        mVideoTrack.setEnabled(isCameraOn);
    }

    public boolean isCameraOn() {
        if (mVideoTrack.enabled()) {
            return true;
        }
        return false;
    }

    public void setMicrophoneOnOff(boolean isMicrophoneOn) {
        mAudioTrack.setEnabled(isMicrophoneOn);
    }

    public boolean isMicrophoneOn() {
        if (mAudioTrack.enabled()) {
            return true;
        }
        return false;
    }
    //===============================================================================================

    public void createPeerConnection1() {
        if (mPeerConnection == null) {
            mPeerConnection = UtilWerRtc.createPeerConnection(mVideoTrack, mAudioTrack, mPeerConnectionFactory, mPeerConnectionObserver);
        }
    }

    public void createVideoTrack(boolean isVideoCall) {
        this.isVideoCall = isVideoCall;

        VideoSource videoSource = mPeerConnectionFactory.createVideoSource(false);
        mVideoCapturer = UtilWerRtc.createVideoCapturer(context);
        mVideoCapturer.initialize(mSurfaceTextureHelper, context, videoSource.getCapturerObserver());
        //===============================================================================================


        mVideoTrack = mPeerConnectionFactory.createVideoTrack(UtilWerRtc.VIDEO_TRACK_ID, videoSource);

        mVideoTrack.setEnabled(isVideoCall);
        mVideoTrack.addSink(videoSink);


        MediaConstraints audioConstraints = new MediaConstraints();

        // add all existing audio filters to avoid having echos
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googEchoCancellation2", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googDAEchoCancellation", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googTypingNoiseDetection", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAutoGainControl2", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression", "true"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googNoiseSuppression2", "true"));

        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googAudioMirroring", "false"));
        audioConstraints.mandatory.add(new MediaConstraints.KeyValuePair("googHighpassFilter", "true"));


        AudioSource audioSource = mPeerConnectionFactory.createAudioSource(audioConstraints);
        mAudioTrack = mPeerConnectionFactory.createAudioTrack(UtilWerRtc.AUDIO_TRACK_ID, audioSource);
        mAudioTrack.setEnabled(true);
    }


    public void stopCapture() {
        try {
            if (mVideoCapturer != null) {
                mVideoCapturer.stopCapture();
            }
        } catch (InterruptedException e) {
            ApiManager.sendApiLogErrorCodeScope(e);

            Timber.e("Error:" + e.toString());
        }
    }


    public void startCapture() {
        if (mVideoCapturer == null) {
            Timber.e("mVideoCapturer == null");
            return;
        }
        try {
            mVideoCapturer.startCapture(VIDEO_RESOLUTION_WIDTH, VIDEO_RESOLUTION_HEIGHT, VIDEO_FPS);
        } catch (Exception e) {
            Timber.e("Error:" + e.toString());
            ApiManager.sendApiLogErrorCodeScope(e);
        }
    }

    public void setProxyVideoSink(SurfaceViewRenderer mLocalSurfaceView) {
        videoSink = new ProxyVideoSink();
        videoSink.setTarget(mLocalSurfaceView);
    }


    public static void switchCamera() {
        if (mVideoCapturer == null) {
            Timber.e("videoCapturer == null");
            return;
        }


        if (mVideoCapturer instanceof CameraVideoCapturer) {
            CameraVideoCapturer cameraVideoCapturer = (CameraVideoCapturer) mVideoCapturer;
            cameraVideoCapturer.switchCamera(null);
        } else {
            // Will not switch camera, video capturer is not a camera
            Timber.e("Will not switch camera, video capturer is not a camera");
        }
    }

    //=====================================================================================================================


    public void answerCallSocket(String doctorUuid) {

        Timber.i("Answer New Incoming Call...");
        createPeerConnection1();// if not create

        MediaConstraints constraints = new MediaConstraints();
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        constraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", isVideoCall ? "true" : "false"));

        ///-------------------------------------------------------
        mPeerConnection.createAnswer(new SimpleSdpObserver() {
            @Override
            public void onCreateSuccess(SessionDescription sessionDescription) {
                Timber.i("Create answer success !");
                mPeerConnection.setLocalDescription(new SimpleSdpObserver(), sessionDescription);

                Timber.e("SDP1:" + sessionDescription.description);
                String sdp = sdpModify(sessionDescription.description);
                Timber.e("SDP2:" + sdp);

                SignalingOfferSocket json = new SignalingOfferSocket(SocketKey.KEY_TYPE_ANSWER, "", sdp, "", LocalData.getUserUuid(), doctorUuid, DeviceIDUtil.getDeviceID(), SocketKey.getReceiverDeviceId(), AppKey.CHANNEL);
                String signalingOfferSocketPayloadJson = JsonUtil.getJsonStringFromObject(json);


                Timber.e("-------------------------------------------------------");
                Timber.e("sessionDescription:" + JsonUtil.getJsonStringFromObject(sessionDescription));
                Timber.e("-------------------------------------------------------");
                socketManager.sendData(SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, signalingOfferSocketPayloadJson);

                //---------------------------------------------------------------
                String message = "Send \"ANSAWER\" doctor call offer. DoctorId:" + doctorUuid;
                ApiManager.sendApiLog(uiName, AppKeyLog.SEND_SOCKET, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, message);
                //-----------------------------------------------------------------


            }

        }, constraints);

    }

    private String sdpModify(String sdp) {

        String[] splitSdp = sdp.split("\r\n");
        String sdpWithBitRate = "";


        String bitRate = ";x-google-max-bitrate=500;x-google-min-bitrate=72;x-google-start-bitrate=72";

        for (int position = 0; position < splitSdp.length; position++) {

            Timber.e("SDP_0:" + splitSdp[position]);

            if (splitSdp[position].contains("a=fmtp")) {

                splitSdp[position] = splitSdp[position] + bitRate;
                Timber.e("SDP_1:" + splitSdp[position]);

            } else if (splitSdp[position].contains("a=mid:1") || splitSdp[position].contains("a=mid:video)")) {
                splitSdp[position] = splitSdp[position] + "\r\nb=AS:500";
                Timber.e("SDP_2:" + splitSdp[position]);
            } else {
                Timber.e("SDP_3:" + splitSdp[position]);
            }

            splitSdp[position] = splitSdp[position] + "\r\n";

            sdpWithBitRate = sdpWithBitRate + splitSdp[position];
        }


        if (sdp.equals(sdpWithBitRate)) {
            Timber.e("sdpWithBitRate same");
        } else {
            Timber.e("sdpWithBitRate not same");
        }

        return sdpWithBitRate;
    }


    public void onIceCandidate(IceCandidate iceCandidate) {

        IceCandidateSocket payload = new IceCandidateSocket(iceCandidate.sdp, iceCandidate.sdpMLineIndex, iceCandidate.sdpMid);
        String jsonStringFromObject = JsonUtil.getJsonStringFromObject(payload);

        SignalingOfferSocket signalingOfferSocket = new SignalingOfferSocket(SocketKey.KEY_TYPE_CANDIDATE, "", "", jsonStringFromObject, LocalData.getUserUuid(), SocketKey.RECEIVER_ID, DeviceIDUtil.getDeviceID(), SocketKey.getReceiverDeviceId(), AppKey.CHANNEL);
        String signalingoffersocketpayloadjosn = JsonUtil.getJsonStringFromObject(signalingOfferSocket);

        Timber.e("sendMessage iceCandidate::" + signalingoffersocketpayloadjosn);
        socketManager.sendData(SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, signalingoffersocketpayloadjosn);

        //---------------------------------------------------------------
        String message = "Send WebRtc-ICE_CANDIDATE. DoctorId:" + SocketKey.RECEIVER_ID;
        ApiManager.sendApiLog(uiName, AppKeyLog.SEND_SOCKET, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, message);
        //-----------------------------------------------------------------


    }

    public void removeIceCandidates(IceCandidate[] iceCandidates) {
        for (int i = 0; i < iceCandidates.length; i++) {
            Timber.e("onIceCandidatesRemoved: " + iceCandidates[i]);
        }
        mPeerConnection.removeIceCandidates(iceCandidates);
    }


    public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams, SurfaceViewRenderer mRemoteSurfaceView) {
        MediaStreamTrack track = rtpReceiver.track();

        if (track instanceof VideoTrack) {
            Timber.e("onAddVideoTrack");

            ProxyVideoSink videoSink = new ProxyVideoSink();
            videoSink.setTarget(mRemoteSurfaceView);


            VideoTrack remoteVideoTrack = (VideoTrack) track;
            remoteVideoTrack.setEnabled(true);
            remoteVideoTrack.addSink(videoSink);
        }
    }


    public void onRemoteOfferReceivedSocket(SignalingOfferSocket signalingOfferSocket, String doctorUuid) {
        Timber.e("Receive Remote Call ...");
        createPeerConnection1();
        mPeerConnection.setRemoteDescription(new SimpleSdpObserver(), new SessionDescription(SessionDescription.Type.OFFER, signalingOfferSocket.getOffer()));
        answerCallSocket(doctorUuid);
    }

    private IceCandidate remoteIceCandidate;

    public void onRemoteCandidateReceivedSocket(SignalingOfferSocket signalingOfferSocket) {
        Timber.e("Receive Remote Candidate ...");

        Timber.e("signalingOfferSocket.getCandidate() :: " + signalingOfferSocket.getCandidate());

        createPeerConnection1();
        IceCandidateSocket iceCandidate = JsonUtil.getModelFromStringJson(signalingOfferSocket.getCandidate(), IceCandidateSocket.class);

        if (iceCandidate == null) {
            Timber.e("iceCandidate==null");
            return;
        }

        remoteIceCandidate = new IceCandidate(iceCandidate.getSDPMid(), iceCandidate.getSDPMLineIndex(), iceCandidate.getSDP());

        Timber.e("onRemoteCandidateReceivedSocket::: " + iceCandidate.getSDP());
        Timber.e("onRemoteCandidateReceivedSocket::: getSDPMLineIndex" + iceCandidate.getSDPMLineIndex());

        Timber.e("onRemoteCandidateReceivedSocket::: getSDPMid" + iceCandidate.getSDPMid());

        mPeerConnection.addIceCandidate(remoteIceCandidate);

    }

    public void reTryIceConnection() {
        if (mPeerConnection != null && remoteIceCandidate != null) {
            mPeerConnection.addIceCandidate(remoteIceCandidate);
        }
    }

    //---------------------------------------------------------------------------------------------------

    public void doEndCall() {

        PreOfferAnswerSocket preOfferAnswerSocket = new PreOfferAnswerSocket(SocketKey.KEY_TYPE_CALL_ENDED, LocalData.getUserUuid(), SocketKey.RECEIVER_ID, DeviceIDUtil.getDeviceID(), SocketKey.getReceiverDeviceId(), AppKey.CHANNEL);
        String json = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket);
        socketManager.sendData(SocketKey.LISTENER_PRE_OFFER_ANSWER, json);

        //---------------------------------------------------------------
        String message = "Send " + SocketKey.KEY_TYPE_CALL_ENDED + " Click Call End Button . DoctorId:" + SocketKey.RECEIVER_ID;
        ApiManager.sendApiLog(uiName, AppKeyLog.SEND_SOCKET, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, SocketKey.LISTENER_PRE_OFFER_ANSWER, message);
        //-----------------------------------------------------------------

    }


    public void doForceStopCall() {
        PreOfferAnswerSocket preOfferAnswerSocket = new PreOfferAnswerSocket(SocketKey.KEY_TYPE_CALL_HANGED_UP, LocalData.getUserUuid(), SocketKey.RECEIVER_ID, DeviceIDUtil.getDeviceID(), SocketKey.getReceiverDeviceId(), AppKey.CHANNEL);
        String json = JsonUtil.getJsonStringFromObject(preOfferAnswerSocket);
        socketManager.sendData(SocketKey.LISTENER_USER_HANGUP, json);
        //---------------------------------------------------------------
        String message = "Send " + SocketKey.KEY_TYPE_CALL_HANGED_UP + " in solar ui for force Stop Call & Click hangedUp Button . DoctorId:" + SocketKey.RECEIVER_ID;
        ApiManager.sendApiLog(uiName, AppKeyLog.SEND_SOCKET, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, SocketKey.LISTENER_USER_HANGUP, message);
        //-----------------------------------------------------------------


    }


    public void onDestroy() {
        try {
            mVideoCapturer.dispose();
            mSurfaceTextureHelper.dispose();
            PeerConnectionFactory.stopInternalTracingCapture();
            PeerConnectionFactory.shutdownInternalTracer();


            //=============================================================
            if (mPeerConnection == null) {
                return;
            }
            mPeerConnection.close();
            mPeerConnection = null;

        } catch (Exception e) {
            Timber.e("Error:" + e);
            ApiManager.sendApiLogErrorCodeScope(e);

        }
    }


    private String preOfferSocketJson = "";
    private boolean alreadySend = false;

    public void callOffer(String doctorUuid, String callType, boolean isDemoCall) {

        PreOfferSocket preOfferSocket = new PreOfferSocket(LocalData.getUserName(), LocalData.getUserProfile(), TimeUtil.getTime1(), callType, isDemoCall, LocalData.getUserUuid(), doctorUuid, DeviceIDUtil.getDeviceID(), SocketKey.getReceiverDeviceId(), AppKey.CHANNEL);

        if (socketManager.isSocketConnected()) {
            Timber.e("sendCallPreOffer socket already connected");
            sendCallPreOffer(JsonUtil.getJsonStringFromObject(preOfferSocket), doctorUuid);
            preOfferSocketJson = "";
            alreadySend = true;

        } else {
            preOfferSocketJson = JsonUtil.getJsonStringFromObject(preOfferSocket);
            alreadySend = false;
            Timber.e("Socket Not Connected!");
        }
    }


    private void sendCallPreOffer(String preOfferSocketJson, String doctorUuid) {
        socketManager.sendData(SocketKey.LISTENER_PRE_OFFER, preOfferSocketJson);

        //---------------------------------------------------------------
        String message = "Send  call preOffer sdp. DoctorId:" + doctorUuid;
        ApiManager.sendApiLog(uiName, AppKeyLog.SEND_SOCKET, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, SocketKey.LISTENER_PRE_OFFER, message);
        //-----------------------------------------------------------------

    }

    public void resetOffer(String offerJson) {
        Timber.d("resetOffer:" + offerJson);
        preOfferSocketJson = "";
    }

    //=========================================================================================================
    private SocketManager.ISocketConnection iSocketConnection = new SocketManager.ISocketConnection() {
        @Override
        public void onConnection(boolean connection) {
            Timber.e("Socket Connection:" + connection);
            Timber.e("preOfferSocketJson:" + preOfferSocketJson);
            Timber.e("alreadySend:" + alreadySend);

            if (!preOfferSocketJson.isEmpty() && !alreadySend) {
                Timber.e("sendCallPreOffer socket already connected");

                sendCallPreOffer(preOfferSocketJson, SocketKey.RECEIVER_ID);
                preOfferSocketJson = "";
                alreadySend = true;
            }
        }

    };

    public void socketDisConnect() {
        if (socketManager != null) {
            socketManager.disconnect();
            socketManager = null;
            iSocketConnection = null;
        }

    }
}
