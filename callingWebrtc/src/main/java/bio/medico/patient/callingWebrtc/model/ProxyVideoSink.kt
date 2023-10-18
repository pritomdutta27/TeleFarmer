package bio.medico.patient.callingWebrtc.model

import org.webrtc.VideoFrame
import org.webrtc.VideoSink
import timber.log.Timber

class ProxyVideoSink : VideoSink {
    private lateinit var mTarget: VideoSink
    @Synchronized
    override fun onFrame(frame: VideoFrame) {
        if (mTarget == null) {
            Timber.d("Dropping frame in proxy because target is null.")
            return
        }
        mTarget.onFrame(frame)
    }

    @Synchronized
    fun setTarget(target: VideoSink) {
        mTarget = target
    }
}