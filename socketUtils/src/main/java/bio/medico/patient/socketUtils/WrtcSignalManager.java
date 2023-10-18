package bio.medico.patient.socketUtils;

import com.skh.hkhr.util.JsonUtil;

import bio.medico.patient.data.ApiManager;
import bio.medico.patient.model.socket.PreOfferAnswerSocket;
import bio.medico.patient.model.socket.SignalingOfferSocket;
import timber.log.Timber;

public class WrtcSignalManager {

    private static ICallUiListener mICallUiListener;


    public static void removeSignalEventListener() {
        mICallUiListener = null;
        Timber.e("removeSignalEventListener");
    }

    public static void setSignalEventListener(final ICallUiListener listener) {
        Timber.e("ADD SignalEventListener");
        mICallUiListener = listener;
    }


    //=======================================================================================
    public static SocketIncomingData socketIncomingData = new SocketIncomingData() {
        @Override
        public void onData(String data) {

            if (mICallUiListener == null) {
                Timber.e("----------AppUi out of range-----------");
                return;
            }

            Timber.e("----------CAll UI-----------");
          //  mICallUiListener.onBroadcastReceived(data);

        }

        @Override
        public void onSocketResponseData(String listenerKey, String socketDataResponse) {

            if (mICallUiListener == null) {
                Timber.e("----------IN AppUi [out of range]-----------");
                return;
            }

            Timber.e("----------CAll UI-----------");


            switch (listenerKey) {

                case SocketKey.LISTENER_PRE_OFFER_ANSWER:
                case SocketKey.LISTENER_MANAGE_UI:
                    try {

                        PreOfferAnswerSocket preOfferAnswerSocket = JsonUtil.getModelFromStringJson(socketDataResponse, PreOfferAnswerSocket.class);

                        if (preOfferAnswerSocket == null) {
                            Timber.e("preOfferAnswerSocket null");
                            return;
                        }


                        SocketKey.setReceiverDeviceId(preOfferAnswerSocket.getFromDeviceId());
                        mICallUiListener.onPreOfferAnswer(preOfferAnswerSocket);
                    } catch (Exception e) {
                        Timber.e("Error:" + e.toString());
                        ApiManager.sendApiLogErrorCodeScope(e);
                    }

                    break;

                case SocketKey.LISTENER_DATA_WEBRTC_SIGNALING:

                    SignalingOfferSocket signalingOfferSocket = JsonUtil.getModelFromStringJson(socketDataResponse, SignalingOfferSocket.class);

                    if (signalingOfferSocket == null) {
                        Timber.e("signalingOfferSocket null");
                        return;
                    }

                    mICallUiListener.onWebRtcSignaling(signalingOfferSocket);
                    break;


                default:
                    Timber.e("Listener Not Found!:" + socketDataResponse);
                    break;
            }


        }


    };


    //=======================================================
    public interface SocketIncomingData {
        @Deprecated
        void onData(String socketDataResponse);

        void onSocketResponseData(String listenerKey, String socketDataResponse);

    }


    public interface ICallUiListener {

      /*  @Deprecated
        void onBroadcastReceived(String message);
*/
        void onPreOfferAnswer(PreOfferAnswerSocket preOfferAnswerSocket);

        void onWebRtcSignaling(SignalingOfferSocket signalingOfferSocket);
    }

}