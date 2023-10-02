package com.theroyalsoft.telefarmer.utils;

import android.content.Context;

import com.theroyalsoft.telefarmer.model.ProxyVideoSink;

import org.webrtc.AudioTrack;
import org.webrtc.Camera1Enumerator;
import org.webrtc.Camera2Enumerator;
import org.webrtc.CameraEnumerator;
import org.webrtc.DefaultVideoDecoderFactory;
import org.webrtc.DefaultVideoEncoderFactory;
import org.webrtc.EglBase;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SurfaceViewRenderer;
import org.webrtc.VideoCapturer;
import org.webrtc.VideoDecoderFactory;
import org.webrtc.VideoEncoderFactory;
import org.webrtc.VideoTrack;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class UtilWerRtc {

    public static final String VIDEO_TRACK_ID = "ARDAMSv0";
    public static final String AUDIO_TRACK_ID = "ARDAMSa0";


    private static EglBase mRootEglBase;

    public static EglBase getEglBase() {
        if (mRootEglBase == null) {
            mRootEglBase = EglBase.create();
        }
        return mRootEglBase;
    }

    public static PeerConnection createPeerConnection(VideoTrack mVideoTrack,
                                                      AudioTrack mAudioTrack, PeerConnectionFactory mPeerConnectionFactory, PeerConnection.Observer mPeerConnectionObserver) {
        Timber.i("Create PeerConnection ...");
        String iceServersJson = JsonUtil.getJsonStringFromObject(IceServerUtils.getIceServers());
        Timber.e("iceServersJson:" + iceServersJson);

        PeerConnection.RTCConfiguration configuration = new PeerConnection.RTCConfiguration(IceServerUtils.getIceServers());
        configuration.tcpCandidatePolicy = PeerConnection.TcpCandidatePolicy.ENABLED;
        configuration.bundlePolicy = PeerConnection.BundlePolicy.MAXBUNDLE;
        configuration.rtcpMuxPolicy = PeerConnection.RtcpMuxPolicy.REQUIRE;
        configuration.continualGatheringPolicy = PeerConnection.ContinualGatheringPolicy.GATHER_CONTINUALLY;
        configuration.keyType = PeerConnection.KeyType.ECDSA;
        configuration.enableDtlsSrtp = true;
        configuration.enableRtpDataChannel = true;
        configuration.sdpSemantics = PeerConnection.SdpSemantics.UNIFIED_PLAN;


        configuration.allowCodecSwitching = true; //Defines advanced optional cryptographic settings related to SRTP and frame encryption

        PeerConnection connection = mPeerConnectionFactory.createPeerConnection(configuration, mPeerConnectionObserver);
        int min = 72 * 1000;
        int current = 72 * 1000;
        int max = 500 * 1000;
        connection.setBitrate(min, current, max);


        if (connection == null) {
            Timber.e("Failed to createPeerConnection !");
            return null;
        }

        connection.addTrack(mVideoTrack);
        connection.addTrack(mAudioTrack);
        return connection;
    }


    /*
     * Read more about Camera2 here
     * https://developer.android.com/reference/android/hardware/camera2/package-summary.html
     **/
    public static VideoCapturer createVideoCapturer(Context context) {
        if (Camera2Enumerator.isSupported(context)) {
            return createCameraCapturer(new Camera2Enumerator(context));
        } else {
            return createCameraCapturer(new Camera1Enumerator(true));
        }
    }

    private static VideoCapturer createCameraCapturer(CameraEnumerator enumerator) {
        final String[] deviceNames = enumerator.getDeviceNames();

        // First, try to find front facing camera
        Timber.d("Looking for front facing cameras.");
        for (String deviceName : deviceNames) {
            if (enumerator.isFrontFacing(deviceName)) {
                Timber.d("Creating front facing camera capturer.");
                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }
        }

        // Front facing camera not found, try something else
        Timber.d("Looking for other cameras.");

        for (String deviceName : deviceNames) {

            if (!enumerator.isFrontFacing(deviceName)) {

                Timber.d("Creating other camera capture.");

                VideoCapturer videoCapturer = enumerator.createCapturer(deviceName, null);
                if (videoCapturer != null) {
                    return videoCapturer;
                }
            }

        }

        return null;
    }


    public static PeerConnectionFactory createPeerConnectionFactory(EglBase mRootEglBase, Context context) {

        VideoEncoderFactory encoderFactory = new DefaultVideoEncoderFactory(
                mRootEglBase.getEglBaseContext(), false /* enableIntelVp8Encoder */, true);
        VideoDecoderFactory decoderFactory = new DefaultVideoDecoderFactory(mRootEglBase.getEglBaseContext());


        PeerConnectionFactory.initialize(PeerConnectionFactory.InitializationOptions.builder(context)
                .setEnableInternalTracer(true)
                .createInitializationOptions());

        PeerConnectionFactory.Options options = new PeerConnectionFactory.Options();


        PeerConnectionFactory.Builder builder = PeerConnectionFactory.builder()
                .setOptions(options)
                .setVideoEncoderFactory(encoderFactory)
                .setVideoDecoderFactory(decoderFactory);


        return builder.createPeerConnectionFactory();
    }


    public static MediaConstraints getMediaConstraints() {
        MediaConstraints mediaConstraints = new MediaConstraints();
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"));
        mediaConstraints.mandatory.add(new MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"));
        mediaConstraints.optional.add(new MediaConstraints.KeyValuePair("DtlsSrtpKeyAgreement", "true"));

        return mediaConstraints;
    }


    public static void setVideoSink(SurfaceViewRenderer surfaceViewRenderer) {
        ProxyVideoSink videoSink = new ProxyVideoSink();
        videoSink.setTarget(surfaceViewRenderer);
    }

}
