package com.theroyalsoft.telefarmer.utils;

import com.farmer.primary.network.model.metadata.MetaModel;
import com.farmer.primary.network.model.profile.ProfileModel;
import com.farmer.primary.network.repositorys.metadata.ResponseMetaInfo;
import com.theroyalsoft.telefarmer.model.ResponsePatientInfo;

import timber.log.Timber;

/**
 * Created by Pritom Dutta on 5/8/23.
 */
public class LocalData {

    //==================================================================================

//    public static void saveDrugList(List<String> drugList) {
//        String json = JsonUtil.getJsonStringFromObject(drugList);
//        CacheDataUtil.write(SpKey.sp_drugs, json);
//        Timber.e("savedrugs");
//    }

//    public static List<String> getDrugList() {
//        String json = CacheDataUtil.read(SpKey.sp_drugs);
//        List<String> drugList = JsonUtil.getModelListFromStringJson(json, String.class);
//        if (drugList == null) {
//            Timber.e("drugs Not Found!");
//        }
//        return NullRemoveUtil.getNotNull(drugList);
//    }


//    public static String getLanguage() {
//        return CacheDataUtil.read(SpKey.SP_KEY_LANGUAGE);
//    }
//
//    public static void setLanguageEN() {
//        CacheDataUtil.write(SpKey.SP_KEY_LANGUAGE, AppKey.LANGUAGE_EN);
//    }

//    public static void setLanguageBN() {
//        CacheDataUtil.write(SpKey.SP_KEY_LANGUAGE, AppKey.LANGUAGE_BN);
//    }


    public static void setUserName(String userName) {
        CacheDataUtil.write(SpKey.sp_userName, userName);
    }

    public static String getUserName() {
        return CacheDataUtil.read(SpKey.sp_userName);
    }

    public static void setUserProfile(String userProfile) {
        CacheDataUtil.write(SpKey.sp_userProfile, userProfile);
    }

    public static String getUserProfile() {
        return CacheDataUtil.read(SpKey.sp_userProfile);
    }


    public static String getUserInfo() {
        return CacheDataUtil.read(SpKey.sp_userInfo);
    }

    public static void removeUserInfoOld() {
        CacheDataUtil.write(SpKey.sp_userInfo, "");
        Timber.e("removeUserInfo");
    }

    public static void removeUserInfo() {
        removeUserInfoOld();

        CacheDataUtil.write(SpKey.sp_Token, "");
        CacheDataUtil.write(SpKey.sp_userName, "");
        CacheDataUtil.write(SpKey.sp_setUserProfileAll, "");
        CacheDataUtil.write(SpKey.sp_setUserProfileAll, "");
        Timber.e("removeUserInfo");
    }


    public static String getUserUuid() {
//        ProfileModel userInfo = getResponsePatientInfo();
//        Timber.d("Found userInfo: "+userInfo);
//        if (userInfo == null) {
//            return "";
//        }
//        Timber.d("Found userInfo");

        return "b0c317ea-edd2-4951-8b86-5a87f93bfbd2";
    }


    public static void setPhoneNumber(String value) {
        CacheDataUtil.write(SpKey.SP_KEY_PHONE_NUMBER, value);
    }

    public static String getPhoneNumber() {
        return CacheDataUtil.read(SpKey.SP_KEY_PHONE_NUMBER);
    }


    public static void saveData(MetaModel responseMetaInfo) {
        String json = JsonUtil.getJsonStringFromObject(responseMetaInfo);
        CacheDataUtil.write(SpKey.sp_ResponseMetaInfo, json);
    }

    public static void clearSubInfoData() {
        CacheDataUtil.write(SpKey.sp_ResponseSubInfo, false);
    }

    public static void saveSubInfoData(boolean subStatus) {
        CacheDataUtil.write(SpKey.sp_ResponseSubInfo, subStatus);
    }

    public static boolean getSubStatus() {
        boolean value = CacheDataUtil.readBoolean(SpKey.sp_ResponseSubInfo);
        Timber.e("=========sub status=========" + value);

        return value;
    }

    public static void getSubStatusWithMessage() {
        if (!getSubStatus()) {
            //ToastUtil.showToastMessage("Please Subscribe first!");
        }
    }


    //==========================================================================
    public static MetaModel getMetaInfoMetaData() {
        MetaModel metaInfo = JsonUtil.getModelFromStringJson(CacheDataUtil.read(SpKey.sp_ResponseMetaInfo), MetaModel.class);
        if (metaInfo == null) {
            Timber.e("ResponseMetaInfo Not Found!");
            return null;
        }
        return metaInfo;
    }

    public static String getImgBaseUrl() {
        if (LocalData.getMetaInfoMetaData() == null) {
            Timber.e("Error: MetaInfoMetaData() == null");
            return "";
        }

        return LocalData.getMetaInfoMetaData().getImgBaseUrl();

    }


    public static String getInstructionsLink() {
        if (LocalData.getMetaInfoMetaData() == null) {
            return "";
        }
        return LocalData.getMetaInfoMetaData().getInstructionsLink();
    }

    public static String getSocketBaseUrl() {
        if (LocalData.getMetaInfoMetaData() == null) {
            return "";
        }
        return LocalData.getMetaInfoMetaData().getSocketBaseUrl();
    }


    public static String getSocketChatUrl() {
        if (LocalData.getMetaInfoMetaData() == null) {
            return "";
        }
        return LocalData.getMetaInfoMetaData().getChatUrl();
    }






    public static void setToken(String userToken) {
        Timber.e("setToken:" + userToken);

        CacheDataUtil.write(SpKey.sp_Token, userToken);
    }

    public static String getToken() {
        String token = CacheDataUtil.read(SpKey.sp_Token);

        if (!token.isEmpty()) {
            // token = "Bearer " + token;
        }

        return NullRemoveUtil.getNotNull(token);
        //   return "";
    }


    public static String getImageUploadBaseUrl() {
        if (getMetaInfoMetaData() == null) {
            return "";
        }
        return getMetaInfoMetaData().getImageUploadBaseUrl();
    }

    public static String getPlaystoreLink() {
        if (getMetaInfoMetaData() == null) {
            return "";
        }
        return getMetaInfoMetaData().getPlaystoreLink();
    }

    public static void setUserProfileAll(ResponsePatientInfo responsePatientInfo) {
        LocalData.setPhoneNumber(responsePatientInfo.getPhoneNumber());
        LocalData.setUserName(responsePatientInfo.getName());
        LocalData.setUserProfile(responsePatientInfo.getImage());

        String json = JsonUtil.getJsonStringFromObject(responsePatientInfo);
        CacheDataUtil.write(SpKey.sp_setUserProfileAll, json);
    }

    public static ProfileModel getResponsePatientInfo() {
        ProfileModel responsePatientInfo = JsonUtil.getModelFromStringJson(CacheDataUtil.read(SpKey.sp_userProfile), ProfileModel.class);
        return responsePatientInfo;
    }

}
