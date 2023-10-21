package bio.medico.patient.callingWebrtc.freeCall;

import com.farmer.primary.network.dataSource.local.LocalData;
import com.skh.hkhr.util.JsonUtil;
import com.skh.hkhr.util.NullRemoveUtil;


import timber.log.Timber;

/**
 * Created by Samiran Kumar on 16,March,2023
 **/
public class LocalCallLimitStatusExpire {

    public final static String STATUS_TYPE_ALL_TIME = "alltime";
    public final static String STATUS_TYPE_EVERYDAY = "everyday";

    private String userId;
    private String dateTime;
    private int callCount;

    public LocalCallLimitStatusExpire(String userId, String dateTime, int callCount) {
        this.userId = userId;
        this.dateTime = dateTime;
        this.callCount = callCount;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public String getUserId() {
        return NullRemoveUtil.getNotNull(userId);
    }

    public String getDateTime() {
        return NullRemoveUtil.getNotNull(dateTime);
    }

    public int getCallCount() {
        return callCount;
    }


    public static String getCallLimitStatusExpireJson(LocalCallLimitStatusExpire callLimitStatusExpire) {
        return JsonUtil.getJsonStringFromObject(callLimitStatusExpire);
    }

    public static LocalCallLimitStatusExpire getCallLimitStatusExpire() {
        String json = LocalData.getCallLimit();
        Timber.d("LocalCallLimit: " + json);
        return JsonUtil.getModelFromStringJson(LocalData.getCallLimit(), LocalCallLimitStatusExpire.class);
    }

    public static String getCallLimitStatusExpireJson() {
        return LocalData.getCallLimit().isEmpty() ? "{}" : LocalData.getCallLimit();
    }

}
