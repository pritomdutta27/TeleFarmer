package bio.medico.patient.data;

import android.util.Base64;

import com.farmer.primary.network.dataSource.local.LocalData;
import com.skh.hkhr.util.JsonUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import bio.medico.patient.common.AppKeyLog;
import timber.log.Timber;

/**
 * Created by Samiran Kumar on 27,July,2022
 **/
public class JWTUtils {

    public static boolean isTokenExpire() {

        String decodeToken = decoded(LocalData.getToken());
        if (decodeToken.isEmpty()) {
            return false;
        }

        boolean isValid = false;

        try {

            JwModel jwModel = JsonUtil.getModelFromStringJson(decodeToken, JwModel.class);
            if (jwModel == null) {
                return false;
            }

           // String expireTime = TimeUtil.getTime1(jwModel.getExp() * 1000);
            //  Timber.e("Expire Time:" + expireTime);

            long currentTime = (new Date().getTime() + 1) / 1000;
            // Timber.e("expireTime :" + jwModel.getExp());
            // Timber.e("currentTime:" + currentTime);

            if (jwModel.getExp() < currentTime) {
                Timber.e("expireTime:" + false);
                isValid = true;
                ApiManager.sendApiLogEndpointApi(AppKeyLog.NA, "TOKEN_VALIDITY_CHECK", AppKeyLog.NA, "FOUND TOKEN_EXPIRE | token:" + LocalData.getToken());

            } else {
                isValid = false;
            }
            Timber.e("isValid:" + isValid);


        } catch (Exception e) {
            isValid = false;
            ApiManager.sendApiLogErrorCodeScope(e);
        }

        return isValid;
    }


    //======================================================
    private static String decoded(String JWTEncoded) {
        String decodeToken = "";

        try {
            String[] split = JWTEncoded.split("\\.");


            // Timber.e("Header: " + getJson(split[0]));
            // Timber.e("JWT_DECODED:" + "Body: " + getJson(split[1]));

            decodeToken = getJson(split[1]);

        } catch (UnsupportedEncodingException e) {
            Timber.e("Error:" + e.toString());
            decodeToken = "";
            ApiManager.sendApiLogErrorCodeScope(e);

        }catch (Exception e){
            Timber.e("Error:" + e.toString());
            ApiManager.sendApiLogErrorCodeScope(e);
        }

        return decodeToken;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }


}
