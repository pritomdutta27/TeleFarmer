package bio.medico.patient.callingWebrtc.model

import org.webrtc.SdpObserver
import org.webrtc.SessionDescription
import timber.log.Timber

open class SimpleSdpObserver : SdpObserver {
    override fun onCreateSuccess(sessionDescription: SessionDescription) {
        Timber.i("SdpObserver: onCreateSuccess !")
    }

    override fun onSetSuccess() {
        Timber.i("SdpObserver: onSetSuccess")
    }

    override fun onCreateFailure(msg: String) {
        Timber.e("SdpObserver onCreateFailure: $msg")
    }

    override fun onSetFailure(msg: String) {
        Timber.e("SdpObserver onSetFailure: $msg")
    }
}