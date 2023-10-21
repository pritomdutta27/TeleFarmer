package com.farmer.primary.network.dataSource.local;

import com.skh.hkhr.util.CacheDataUtil;
import com.skh.hkhr.util.JsonUtil;
import com.skh.hkhr.util.NullRemoveUtil;
import com.skh.hkhr.util.log.ToastUtil;

import java.util.List;

import bio.medico.patient.common.AppKey;

import bio.medico.patient.model.apiResponse.ResponseMetaInfo;
import bio.medico.patient.model.apiResponse.ResponsePatientInfo;
import timber.log.Timber;

public class LocalData {

    //==================================================================================

    public static void saveDrugList(List<String> drugList) {
        String json = JsonUtil.getJsonStringFromObject(drugList);
        CacheDataUtil.write(SpKey.sp_drugs, json);
        Timber.e("savedrugs");
    }

    public static List<String> getDrugList() {
        String json = CacheDataUtil.read(SpKey.sp_drugs);
        List<String> drugList = JsonUtil.getModelListFromStringJson(json, String.class);
        if (drugList == null) {
            Timber.e("drugs Not Found!");
        }
        return NullRemoveUtil.getNotNull(drugList);
    }


    public static String getLanguage() {
        return CacheDataUtil.read(SpKey.SP_KEY_LANGUAGE);
    }

    public static void setLanguageEN() {
        CacheDataUtil.write(SpKey.SP_KEY_LANGUAGE, AppKey.LANGUAGE_EN);
    }

    public static void setLanguageBN() {
        CacheDataUtil.write(SpKey.SP_KEY_LANGUAGE, AppKey.LANGUAGE_BN);
    }


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
        LocalData.setCallLimit("");
        Timber.e("Remove Local UserInfo!!");
    }


    public static String getUserUuid() {
        ResponsePatientInfo userInfo = getResponsePatientInfo();
        if (userInfo == null) {
            Timber.e("userInfo Not Found ");
            return "";
        }


        return userInfo.getUuid();
    }


    public static void setPhoneNumber(String value) {
        CacheDataUtil.write(SpKey.SP_KEY_PHONE_NUMBER, value);
    }

    public static String getPhoneNumber() {
        return CacheDataUtil.read(SpKey.SP_KEY_PHONE_NUMBER);
    }


    public static void saveData(ResponseMetaInfo responseMetaInfo) {
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
            ToastUtil.showToastMessage("Please Subscribe first!");
        }
    }


    //==========================================================================
    public static ResponseMetaInfo.MetaData getMetaInfoMetaData() {
        ResponseMetaInfo metaInfo = JsonUtil.getModelFromStringJson(CacheDataUtil.read(SpKey.sp_ResponseMetaInfo), ResponseMetaInfo.class);
        if (metaInfo == null) {
            Timber.e("ResponseMetaInfo Not Found!");
            return null;
        }
        return metaInfo.getMeta();
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

    public static ResponsePatientInfo getResponsePatientInfo() {
        ResponsePatientInfo responsePatientInfo = JsonUtil.getModelFromStringJson(CacheDataUtil.read(SpKey.sp_setUserProfileAll), ResponsePatientInfo.class);
        return responsePatientInfo;
    }


    //===========================================================
    static String getSpLimitName() {
        return SpKey.sp_call_limit + "_" + LocalData.getUserUuid();
    }

    public static String getCallLimit() {
        return CacheDataUtil.read(getSpLimitName());
    }

    public static void setCallLimit(String call_limit) {
        CacheDataUtil.write(getSpLimitName(), call_limit);
        Timber.d("Save Local CallLimit: " + call_limit);
    }


    public static String getLogSubStatus() {
        return "SUB_STATUS:" + LocalData.getSubStatus();
    }


}
