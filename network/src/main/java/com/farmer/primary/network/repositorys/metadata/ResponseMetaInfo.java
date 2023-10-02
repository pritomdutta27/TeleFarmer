package com.farmer.primary.network.repositorys.metadata;


import com.farmer.primary.network.utils.NullRemoveUtil;

import java.util.List;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class ResponseMetaInfo {

    private MetaData meta;

    public MetaData getMeta() {
        return meta;
    }
    //==============================================================


    public static class MetaData {
        private boolean socket;
        private boolean xmpp;
        private int apiVersion;
        private int appVersion;
        //  private int medicineDeliveryCharge = 0;
        private String description;
        private String title;
        private String password;
        private String user;
        private String token;
        private List<String> stunServer;
        private TurnAuth turnAuth;
        private List<String> turnServer;
        private String imgBaseUrl;
        private String apiBaseUrl;
        private String baseUrl;
        private String socketBaseUrl;
        private String imageUploadBaseUrl;
        private String doctorBaseUrl;
        private String redirectAfterbilling;
        private String playstoreLink;
        private String instructionsLink;
        private XMPPInfo xmppInfo;

        private String chatUrl;


        public String getImageUploadBaseUrl() {
            return NullRemoveUtil.getNotNull(imageUploadBaseUrl);
        }

        public boolean isSocket() {
            return socket;
        }

        public boolean isXmpp() {
            return xmpp;
        }

        public int getApiVersion() {
            return apiVersion;
        }

        public int getAppVersion() {
            return appVersion;
        }

        public String getDescription() {
            return NullRemoveUtil.getNotNull(description);
        }

        public String getTitle() {
            return NullRemoveUtil.getNotNull(title);
        }

        public String getPassword() {
            return NullRemoveUtil.getNotNull(password);
        }

        public String getUser() {
            return NullRemoveUtil.getNotNull(user);
        }

        public String getToken() {
            return NullRemoveUtil.getNotNull(token);
        }

        public List<String> getStunServer() {
            return NullRemoveUtil.getNotNull(stunServer);
        }

        public TurnAuth getTurnAuth() {
            return turnAuth;
        }

        public List<String> getTurnServer() {
            return NullRemoveUtil.getNotNull(turnServer);
        }

        public String getImgBaseUrl() {
            return NullRemoveUtil.getNotNull(imgBaseUrl);
        }

        public String getApiBaseUrl() {
            return NullRemoveUtil.getNotNull(apiBaseUrl);
        }

        public String getBaseUrl() {
            return NullRemoveUtil.getNotNull(baseUrl);
        }

        public String getSocketBaseUrl() {
            return NullRemoveUtil.getNotNull(socketBaseUrl);
            //return SocketKeyChat.URL_SOCKET;
        }

        public String getRedirectAfterbilling() {
            return NullRemoveUtil.getNotNull(redirectAfterbilling);
        }

        public String getDoctorBaseUrl() {
            return NullRemoveUtil.getNotNull(doctorBaseUrl);
        }

        public String getPlaystoreLink() {
            return NullRemoveUtil.getNotNull(playstoreLink);
        }

        public String getInstructionsLink() {
            return NullRemoveUtil.getNotNull(instructionsLink);
        }


        public XMPPInfo getXmppInfo() {
            return xmppInfo;
        }

        public String getChatUrl() {
            return NullRemoveUtil.getNotNull(chatUrl);
        }
/* public int getMedicineDeliveryCharge() {
            return NullRemoveUtil.getNotNull(medicineDeliveryCharge);
        }*/
    }

    public static class XMPPInfo {
        private String xmppDomain;
        private String c2sPort;
        private String apiBaseUrl;

        public String getXmppDomain() {
            return NullRemoveUtil.getNotNull(xmppDomain);
        }

        public String getC2sPort() {
            return NullRemoveUtil.getNotNull(c2sPort);
        }

        public String getApiBaseUrl() {
            return NullRemoveUtil.getNotNull(apiBaseUrl);
        }
    }

    //==================================================================================


    public static class TurnAuth {
        private String password;
        private String user;

        public String getPassword() {
            return NullRemoveUtil.getNotNull(password);
        }

        public String getUser() {
            return NullRemoveUtil.getNotNull(user);
        }
    }
}
