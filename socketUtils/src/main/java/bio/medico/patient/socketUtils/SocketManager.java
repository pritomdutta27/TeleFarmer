package bio.medico.patient.socketUtils;

import static bio.medico.patient.socketUtils.WrtcSignalManager.socketIncomingData;
import static io.socket.client.Socket.EVENT_CONNECT;
import static io.socket.client.Socket.EVENT_CONNECT_ERROR;
import static io.socket.client.Socket.EVENT_DISCONNECT;

import com.skh.hkhr.util.JsonUtil;
import com.skh.hkhr.util.NullRemoveUtil;
import com.theroyalsoft.mydoc.apputil.TimeUtil;


import java.net.URISyntaxException;


import bio.medico.patient.common.AppKey;
import bio.medico.patient.common.AppKeyLog;
import bio.medico.patient.common.DeviceIDUtil;
import bio.medico.patient.data.activityLog.AppLog;
import bio.medico.patient.data.local.LocalData;
import bio.medico.patient.model.socket.UserInfoSocket;
import io.socket.client.IO;
import io.socket.client.Socket;
import timber.log.Timber;

public class SocketManager {

    private Socket socket;

    private static SocketManager socketManager;
    public static ISocketConnection iSocketConnection;
    private final String uiName = AppKeyLog.UI_CALL;


    public static SocketManager getSocketManager(ISocketConnection iSocketConnection1) {
        iSocketConnection = iSocketConnection1;
        if (socketManager == null) {
            socketManager = new SocketManager();
            Timber.d("SocketManagerChat new Create!");
        }
        return socketManager;
    }

    public void disconnect() {
        if (socket != null) {
            Timber.e("SOCKET: disconnect");
            socket.disconnect();
            socket = null;
        }
    }

    public void init() {

        String SocketUrl = LocalData.getSocketBaseUrl();
        Timber.e("Socket URL:" + SocketUrl);

        if (SocketUrl.isEmpty()) {
            Timber.e("Error: socket base url not found!");
            return;
        }

        try {
            String customId = "uuid=" + LocalData.getUserUuid();
            String devicesId = "deviceId=" + DeviceIDUtil.getDeviceID();
            String query = customId + "&" + devicesId;

            Timber.e("query:" + query);

            IO.Options opts = new IO.Options();
            opts.forceNew = false;
            opts.query = query;

            socket = IO.socket(SocketUrl, opts);
            socket.off();

            socketStatus();

        } catch (URISyntaxException e) {
            Timber.e("Error:" + e.toString());
            AppLog.sendApiLogErrorCodeScope(e);
        }
    }


    public void socketConnect() {

        if (socket == null) {
            init();
        }

        try {
            if (socket.connected()) {
                Timber.e("Already connected!");
                onConnection(true);
            } else {
                Timber.e("Socket try connect!");
                socket.connect();
            }

        } catch (Exception e) {
            Timber.e("Error:" + e.toString());
            onConnection(false);
            AppLog.sendApiLogErrorCodeScope(e);
        }
    }


    public void socketDisconnect() {
        if (socket == null) {
            Timber.e("socket == null");
            onConnection(false);
            return;
        }

        try {
            Timber.e("Socket try disconnect!");
            socket.disconnect();
            socket = null;
        } catch (Exception e) {
            Timber.e("Error:" + e.toString());
        }

    }

    //==================================================================
    private void onConnection(boolean connectionStatus) {
        if (iSocketConnection == null) {
            Timber.e("iSocketConnection == null");
            return;
        }
        iSocketConnection.onConnection(connectionStatus);
    }


    private void socketStatus() {

        if (socket == null) {
            Timber.e("socket == null");
            return;
        }

        socket.on(EVENT_CONNECT, args -> {
                    Timber.e("Socket: connect");
                    Timber.e("Socket ID:" + socket.id());


                    String socketId = NullRemoveUtil.getNotNull(socket.id());

                    if (socketId.isEmpty()) {
                        Timber.e("Socket Id Not found!");
                        return;
                    }


                    Timber.e("socket socketStatus");
                    String uuid = LocalData.getUserUuid();

                    String userName = LocalData.getUserName();
                    String userMobile = LocalData.getPhoneNumber();


                    if (uuid.isEmpty()) {
                        Timber.e("uuid not found");
                        return;
                    }


                    UserInfoSocket userInfoSocket = new UserInfoSocket(uuid, socketId, userName, userMobile, TimeUtil.getTime1(), AppKey.USER_PATIENT, DeviceIDUtil.getDeviceID(), AppKey.CHANNEL);

                    String userInfoSocketJson = JsonUtil.getJsonStringFromObject(userInfoSocket);
                    Timber.e("userInfoSocket:" + userInfoSocketJson);

                    sendData(SocketKey.LISTENER_USER_INFO, userInfoSocketJson);
                    onConnection(true);


                    //---------------------------------------------------------------

                    String message = "SOCKET CONNECTED >> with Socket.socketId: " + socketId;
                    AppLog.sendSocketLog(uiName, SocketKeyChat.LISTENER_USER_INFO, message);
                    //-----------------------------------------------------------------

                }).on(EVENT_CONNECT_ERROR, args -> {
                    Timber.e("SOCKET: ERROR disconnect");
                    AppLog.sendSocketConnection(uiName, EVENT_CONNECT_ERROR, "SOCKET: ERROR disconnect");

                    onConnection(false);
                }).on(EVENT_DISCONNECT, args -> {
                    AppLog.sendSocketConnection(uiName, EVENT_DISCONNECT, "SOCKET: disconnect");
                    Timber.e("SOCKET: disconnect");
                    onConnection(false);
                })
                //-------------------------------------------------------------
                .on(SocketKey.LISTENER_PRE_OFFER_ANSWER, args -> {
                    dataConvert(args, SocketKey.LISTENER_PRE_OFFER_ANSWER);

                }).on(SocketKey.LISTENER_DATA_WEBRTC_SIGNALING, args -> {
                    dataConvert(args, SocketKey.LISTENER_DATA_WEBRTC_SIGNALING);
                }).on(SocketKey.LISTENER_MANAGE_UI, args -> {
                    dataConvert(args, SocketKey.LISTENER_MANAGE_UI);
                });
        ;

    }


    private void dataConvert(Object[] socketResponses, String listenerKey) {
        Timber.e("=============" + listenerKey + "===================");

        if (socketResponses.length <= 0) {
            Timber.e("SocketDataResponse NOT Found!");
            return;
        }

        String socketDataResponse = "";
        try {
            socketDataResponse = NullRemoveUtil.getNotNull(socketResponses[0].toString());
            socketIncomingData.onSocketResponseData(listenerKey, socketDataResponse);

        } catch (Exception error) {
            Timber.e("Error: " + error.toString());
            AppLog.sendApiLogErrorCodeScope(error);
        }

    }


    public boolean isSocketConnected() {
        if (socket == null) {
            Timber.e("socket == null");
            socketConnect();
            return false;
        }

        return socket.connected();
    }

    //==============================================================================================
    public void sendData(String listener, String data) {

        if (socket == null) {
            Timber.e("socket == null");
            socketConnect();
        }

        Timber.d("");
        Timber.e("============================== SOCKET SEND =>>>>[ " + listener + " ]----");
        Timber.e("data:" + data);
        socket.emit(listener, data);
        Timber.e("=====================================================================");


    }

    //==============================================================================================

    public interface ISocketConnection {
        void onConnection(boolean connection);
    }


}


