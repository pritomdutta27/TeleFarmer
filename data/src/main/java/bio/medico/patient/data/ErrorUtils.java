package bio.medico.patient.data;

import java.io.IOException;
import java.lang.annotation.Annotation;

import bio.medico.patient.common.AppKeyLog;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static APIError parseError(Response<?> response) {
        Converter<ResponseBody, APIError> converter = ApiClient.getClient().responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }

    public static APIErrorEmon parseErrorEmon(Response<?> response) {
        Converter<ResponseBody, APIErrorEmon> converter = ApiClient.getClient().responseBodyConverter(APIErrorEmon.class, new Annotation[0]);

        APIErrorEmon error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new APIErrorEmon();
        }

        return error;
    }


    public static ApiError1 parseError1(Response<?> response) {
        Converter<ResponseBody, ApiError1> converter =
                ApiClient.getClient().responseBodyConverter(ApiError1.class, new Annotation[0]);

        ApiError1 error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return null;
        }
        return error;
    }


    public static void parchErrorMessage(Response<?> response, String apiEndPoint, ApiManager.IApiResponse iApiResponse) {
        ApiError1 error1 = ErrorUtils.parseError1(response);
        String message = "";
        if (error1 != null) {

            for (ApiError1.ApiError11 aaa : error1.getError()) {

                if (message.isEmpty()) {
                    message = aaa.getMessage();
                } else {
                    message = message + "\n" + aaa.getMessage();
                }
            }
        }

        iApiResponse.onFailed(message);
        ApiManager.sendApiLog(AppKeyLog.NA, AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, apiEndPoint, message + " | ResponseCode:" + response.code());

    }

    public static void parchErrorMessage(Response<?> response, ApiManager.IApiResponseAuth iApiResponse) {
        ApiError1 error1 = ErrorUtils.parseError1(response);
        String message = "";
        if (error1 != null) {

            for (ApiError1.ApiError11 aaa : error1.getError()) {

                if (message.isEmpty()) {
                    message = aaa.getMessage();
                } else {
                    message = message + "\n" + aaa.getMessage();
                }
            }
        }

        iApiResponse.onFailed(message, false);
    }


}
