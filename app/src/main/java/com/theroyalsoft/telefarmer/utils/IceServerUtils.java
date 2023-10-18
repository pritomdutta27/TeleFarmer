package com.theroyalsoft.telefarmer.utils;

import android.util.Log;

import com.farmer.primary.network.model.metadata.MetaModel;
import com.farmer.primary.network.model.metadata.TurnAuth;

import org.webrtc.PeerConnection;

import java.util.ArrayList;
import java.util.List;

//import bio.medico.patient.data.local.LocalData;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class IceServerUtils {


    public static List<PeerConnection.IceServer> getIceServers() {
//        return getIceServersMetadata();
           return getIceServersApiGate();
        // return getIceServersXirsys();
        // return getIceServersXirsys_1();
    }


    //==================================================================================================
    public static List<PeerConnection.IceServer> getIceServersApiGate() {
        String userName = "root";
        String password = "live@#01";

        String stunServer = "stun:xturn.apigate.pro";
        String[] turnServer = {

                "turn:xturn.apigate.pro:3478",
                "turn:xturn.apigate.pro:3479",
                "turns:xturn.apigate.pro:5350",
                "turns:xturn.apigate.pro:5349"
        };

        return getPeerConnectionIceServer(stunServer, turnServer, userName, password);
    }

    //==================================================================================================
//    public static List<PeerConnection.IceServer> getIceServersMetadata() {
//        M metaData = LocalData.getMetaInfoMetaData();
//        Log.e("PeerConnectionMeta", "getIceServersMetadata: "+metaData.toString() );
//
//        if (metaData == null) {
//            return new ArrayList<>();
//        }
//        TurnAuth turnAuth = metaData.getTurnAuth();
//
//        String userName = turnAuth.getUser();
//        String password = turnAuth.getPassword();
//
//        return getPeerConnectionIceServer(metaData.getStunServer(), metaData.getTurnServer(), userName, password);
//    }

    //==================================================================================================
    public static List<PeerConnection.IceServer> getIceServersXirsys() {
        String userName = "pmAt-HZz5y8zbmMLxY6dy1VXVHgKeaqiBxSHFoISivm6h6O9vIBN7CkdWrk-EkreAAAAAGFK8rpqYWhpcnVsNjA5";
        String password = "c1dc29c8-1b84-11ec-b68a-0242ac140004";

        String stunServer = "stun:stun.l.google.com:19302";
        String[] turnServer = {

                "turn:bn-turn1.xirsys.com:80?transport=udp",
                "turn:bn-turn1.xirsys.com:3478?transport=udp",
                "turn:bn-turn1.xirsys.com:80?transport=tcp",
                "turn:bn-turn1.xirsys.com:3478?transport=tcp",
                "turns:bn-turn1.xirsys.com:443?transport=tcp",
                "turns:bn-turn1.xirsys.com:5349?transport=tcp"
        };

        return getPeerConnectionIceServer(stunServer, turnServer, userName, password);
    }

    //==================================================================================================
    public static List<PeerConnection.IceServer> getIceServersXirsys_1() {
        String userName = "cjCdcGa6NQiUAGCiJDxrkTge4nzrxUG4kZH8riXd4BOcBnffQqyFmm4HqAHbPVRcAAAAAGHnpKFTaGltYW50bzYwOQ==";
        String password = "817b639e-78ea-11ec-ba88-0242ac140004";

        //  String stunServer = "stun:stun.l.google.com:19302";
        String stunServer = "stun:ss-turn1.xirsys.com";
        String[] turnServer = {

                "turn:ss-turn1.xirsys.com:80?transport=udp",
                "turn:ss-turn1.xirsys.com:3478?transport=udp",
                "turn:ss-turn1.xirsys.com:80?transport=tcp",
                "turn:ss-turn1.xirsys.com:3478?transport=tcp",
                "turns:ss-turn1.xirsys.com:443?transport=tcp",
                "turns:ss-turn1.xirsys.com:5349?transport=tcp"
        };

        return getPeerConnectionIceServer(stunServer, turnServer, userName, password);
    }


    //===================================================================================================
    public static List<PeerConnection.IceServer> getPeerConnectionIceServer(String stunServer, String[] turnServer, String userName, String password) {
        List<PeerConnection.IceServer> peerIceServers = new ArrayList<>();

        peerIceServers.add(getPeerConnectionIceServer(stunServer, "", ""));
        for (String ts : turnServer) {
            peerIceServers.add(getPeerConnectionIceServer(ts, userName, password));
        }

        return peerIceServers;
    }    //===================================================================================================

    public static List<PeerConnection.IceServer> getPeerConnectionIceServer(List<String> stunServer, List<String> turnServer, String userName, String password) {
        List<PeerConnection.IceServer> peerIceServers = new ArrayList<>();

        for (String ss : stunServer) {
            peerIceServers.add(getPeerConnectionIceServer(ss, "", ""));
        }

        for (String ts : turnServer) {
            peerIceServers.add(getPeerConnectionIceServer(ts, userName, password));
        }

        return peerIceServers;
    }


    //======================================================================================================================
    private static PeerConnection.IceServer getPeerConnectionIceServer(String serverUrl, String userName, String password) {
        return PeerConnection.IceServer.builder(serverUrl)
                .setUsername(userName)
                .setPassword(password)
                .setTlsCertPolicy(PeerConnection.TlsCertPolicy.TLS_CERT_POLICY_INSECURE_NO_CHECK)
                .createIceServer();
    }


}
