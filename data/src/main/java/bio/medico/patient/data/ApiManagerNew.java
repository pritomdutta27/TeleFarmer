package bio.medico.patient.data;

import androidx.annotation.NonNull;

import com.skh.hkhr.util.JsonUtil;
import com.skh.hkhr.util.log.ToastUtil;
import com.skh.hkhr.util.view.LoadingUtil;
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


import bio.medico.patient.common.AppKey;
import bio.medico.patient.common.AppKeyLog;
import bio.medico.patient.common.AppUser;
import bio.medico.patient.common.DeviceIDUtil;
import bio.medico.patient.common.UiNavigation;
import bio.medico.patient.data.local.LocalData;
import bio.medico.patient.model.apiResponse.CommonResponse;
import bio.medico.patient.model.apiResponse.ResponseMedicine;
import bio.medico.patient.model.apiResponse.OrderHistoryResponseModel;
import bio.medico.patient.model.apiResponse.OrderRequest;
import bio.medico.patient.model.apiResponse.PackageModel;
import bio.medico.patient.model.apiResponse.ProviderResponse;
import bio.medico.patient.model.apiResponse.RequestActivityErrorLog;
import bio.medico.patient.model.apiResponse.RequestActivityLog;
import bio.medico.patient.model.apiResponse.RequestFirebaseTokenUpdate;
import bio.medico.patient.model.apiResponse.RequestFollowUp;
import bio.medico.patient.model.apiResponse.RequestLabReport;
import bio.medico.patient.model.apiResponse.RequestLogin;
import bio.medico.patient.model.apiResponse.RequestLogout;
import bio.medico.patient.model.apiResponse.RequestMedicationSurgeryInsert;
import bio.medico.patient.model.apiResponse.RequestNewToken;
import bio.medico.patient.model.apiResponse.RequestOrderMedicineWithPrescription;
import bio.medico.patient.model.apiResponse.RequestOtpSend;
import bio.medico.patient.model.apiResponse.RequestOtpVerify;
import bio.medico.patient.model.apiResponse.RequestPatientUpdate;
import bio.medico.patient.model.apiResponse.RequestRemoveTrustedDevice;
import bio.medico.patient.model.apiResponse.RequestSignUp;
import bio.medico.patient.model.apiResponse.RequestStatusUpdate;
import bio.medico.patient.model.apiResponse.ResponseCallHistoryModel;
import bio.medico.patient.model.apiResponse.ResponseCommonVendor;
import bio.medico.patient.model.apiResponse.ResponseDrugs;
import bio.medico.patient.model.apiResponse.ResponseFollowUp;
import bio.medico.patient.model.apiResponse.ResponseHelpLine;
import bio.medico.patient.model.apiResponse.ResponseLabReport;
import bio.medico.patient.model.apiResponse.ResponseLabTestList;
import bio.medico.patient.model.apiResponse.ResponseLogin;
import bio.medico.patient.model.apiResponse.ResponseMedication;
import bio.medico.patient.model.apiResponse.ResponseMetaInfo;
import bio.medico.patient.model.apiResponse.ResponseNewToken;
import bio.medico.patient.model.apiResponse.ResponseOrderMedicineWithPrescription;
import bio.medico.patient.model.apiResponse.ResponseOtpVerify;
import bio.medico.patient.model.apiResponse.ResponsePackList;
import bio.medico.patient.model.apiResponse.ResponsePatientInfo;
import bio.medico.patient.model.apiResponse.ResponseSingleDoctor;
import bio.medico.patient.model.apiResponse.ResponseSpecializedDoctorStatus;
import bio.medico.patient.model.apiResponse.ResponseSubInfo;
import bio.medico.patient.model.apiResponse.ResponseTrustedDevicesList;
import bio.medico.patient.model.apiResponse.ResponseUnsubscribe;
import bio.medico.patient.model.apiResponse.chat.ResponsemessageBody;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class ApiManagerNew {

    public static void requestLogin(String mobile, ApiManager.IApiResponseAuth iApiResponseAuth) {

        String apiEndPoint = AppUrl.URL_PATIENT_LOGIN;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        RequestLogin requestLogin = new RequestLogin(AppKey.USER_PATIENT, AppKey.CHANNEL, DeviceIDUtil.getDeviceID(), mobile, AppKey.NEW_USER);

        Call<ResponseLogin> callCategory = ApiClient.getApiInterface().login(header_UserInfo, requestLogin);
        callCategory.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, @NonNull Response<ResponseLogin> response) {

                try {
                    if (response.isSuccessful()) {
                        ResponseLogin responseCategory = response.body();
                        iApiResponseAuth.onSuccess(responseCategory);

                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponseAuth);
                    }

                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                    errorException(apiEndPoint, e, iApiResponseAuth);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponseAuth);

            }
        });

    }

    public static void requestOTPOTPVerification(String mobile, String otp, boolean isTrusted, ApiManager.IApiResponseAuth iApiResponseAuth) {
        String apiEndPoint = AppUrl.URL_PATIENT_OTP_VERIFICATION;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        RequestOtpVerify model = new RequestOtpVerify(mobile, otp, DeviceIDUtil.getDeviceID(),
                isTrusted, DeviceIDUtil.getDeviceName(), AppKey.CHANNEL
        );
        Call<ResponseOtpVerify> callCategory = ApiClient.getApiInterface().requestOTPverification(header_UserInfo, model);
        callCategory.enqueue(new Callback<ResponseOtpVerify>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOtpVerify> call, @NonNull Response<ResponseOtpVerify> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseOtpVerify responseCategory = response.body();
                        iApiResponseAuth.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponseAuth);
                    }
                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                    errorException(apiEndPoint, e, iApiResponseAuth);

                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOtpVerify> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponseAuth);

            }
        });

    }


    public static void requestOTPSend(String mobile, String otpType, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_OTP_SEND;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        RequestOtpSend model = new RequestOtpSend(AppKey.CHANNEL,
                DeviceIDUtil.getDeviceID(), mobile, AppKey.USER_PATIENT, otpType);
        Call<CommonResponse> callCategory = ApiClient.getApiInterface().sendOTP(header_UserInfo, model);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                try {
                    CommonResponse responseCategory = response.body();
                    if (response.isSuccessful()) {
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

    public static void requestNewUserProfileCreate(String name, String mobile, String birthday, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_CREATE_PROFILE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            return;
        }


        RequestSignUp requestModel = new RequestSignUp(name, mobile, mobile, mobile, AppKey.CHANNEL, "no", DeviceIDUtil.getDeviceID(), birthday);
        Call<CommonResponse> callCategory = ApiClient.getApiInterface().requestReg(token, header_UserInfo, requestModel);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse commonResponse = response.body();
                        iApiResponse.onSuccess(commonResponse);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }


    public static void requestLogOut(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_PASSWORD_LESS_LOGOUT;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        RequestLogout requestLogout = new RequestLogout(LocalData.getPhoneNumber(), DeviceIDUtil.getDeviceID());

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            return;
        }

        Call<String> callCategory = ApiClient.getApiInterface().logOut(token, header_UserInfo, requestLogout);
        callCategory.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {

                    if (response.isSuccessful()) {
                        String responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);

                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

    public static void getNewTokenApi(ApiManager.IApiResponseNewToken iApiResponseNewToken) {
        String apiEndPoint = AppUrl.URL_PATIENT_TOKEN;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        RequestNewToken requestNewToken = new RequestNewToken(LocalData.getToken(), LocalData.getPhoneNumber(), LocalData.getUserUuid(), AppKey.CHANNEL);

        Call<ResponseNewToken> callCategory = ApiClient.getApiInterface().getNewToken(header_UserInfo, requestNewToken);
        callCategory.enqueue(new Callback<ResponseNewToken>() {
            @Override
            public void onResponse(@NonNull Call<ResponseNewToken> call, @NonNull Response<ResponseNewToken> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseNewToken responseSignIn = response.body();
                        LocalData.setToken(responseSignIn.getAccessToken());

                        iApiResponseNewToken.onSuccess();
                        ApiManager.sendApiLogEndpointApi(AppKeyLog.NA, "TOKEN_UPDATE_SUCCESS", apiEndPoint, "TOKEN_UPDATE_SUCCESS | token:" + responseSignIn.getAccessToken());

                    } else {
                        iApiResponseNewToken.onFailed("");
                        ApiManager.sendApiLogErrorApi(apiEndPoint, "TOKEN_UPDATE_FAILED ResponseCode:" + response.code());
                        ApiManager.sendApiLogEndpointApi(AppKeyLog.NA, "TOKEN_UPDATE_FAILED", apiEndPoint, "ResponseCode:" + response.code());

                    }
                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                    iApiResponseNewToken.onFailed("something went wrong!");
                    ApiManager.sendApiLogErrorApi(e, apiEndPoint);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseNewToken> call, @NonNull Throwable throwable) {
                iApiResponseNewToken.onFailed("");
                Timber.e("Error:" + throwable.toString());
                ApiManager.sendApiLogErrorApi(throwable, apiEndPoint);
            }
        });
    }

    public static void updateFirebaseToken(String token1, boolean isLogin, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_FIREBASE_TOKEN_UPDATE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        RequestFirebaseTokenUpdate requestSignIn = new RequestFirebaseTokenUpdate(
                isLogin,
                token1,
                DeviceIDUtil.getDeviceID(),
                AppKey.USER_PATIENT,
                AppKey.ACTIVE,
                AppKey.CHANNEL,
                LocalData.getPhoneNumber(),
                LocalData.getUserUuid(),
                LocalData.getUserName().isEmpty() ? "Medico" : LocalData.getUserName()
        );
        Call<CommonResponse> callCategory = ApiClient.getApiInterface().updateFirebaseTokenUpdate(token, header_UserInfo, requestSignIn);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    public static void getFollowUp(ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_PATIENT_FOLLOW_UP;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        RequestFollowUp requestFollowUp = new RequestFollowUp(LocalData.getUserUuid());
        Call<ResponseFollowUp> callCategory = ApiClient.getApiInterface().getCallFollowApi(token, header_UserInfo, requestFollowUp);
        callCategory.enqueue(new Callback<ResponseFollowUp>() {
            @Override
            public void onResponse(@NonNull Call<ResponseFollowUp> call, @NonNull Response<ResponseFollowUp> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseFollowUp responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseFollowUp> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    //==================================================

    public static void getPatientDetails(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_DETAILS;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponsePatientInfo> callCategory = ApiClient.getApiInterface().getPatientDetails(token, header_UserInfo, LocalData.getUserUuid());
        callCategory.enqueue(new Callback<ResponsePatientInfo>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePatientInfo> call, @NonNull Response<ResponsePatientInfo> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponsePatientInfo responseCategory = response.body();
                        if (iApiResponse != null) {
                            iApiResponse.onSuccess(responseCategory);
                        }
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePatientInfo> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

    //==================================================

    public static void getPatientDetails(String msisdn, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_DETAILS;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponsePatientInfo> callCategory = ApiClient.getApiInterface().getLoginPatientDetails(token, header_UserInfo, msisdn);
        callCategory.enqueue(new Callback<ResponsePatientInfo>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePatientInfo> call, @NonNull Response<ResponsePatientInfo> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponsePatientInfo responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePatientInfo> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }

    public static void getSpecialistDoctorStatus(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_SPECIALIST_DOCTOR_STATUS;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseSpecializedDoctorStatus> doctorStatusCall = ApiClient.getApiInterface().getSpecialistDoctorStatus(token, header_UserInfo);
        doctorStatusCall.enqueue(new Callback<ResponseSpecializedDoctorStatus>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSpecializedDoctorStatus> call, @NonNull Response<ResponseSpecializedDoctorStatus> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseSpecializedDoctorStatus responseCategory = response.body();
                        if (iApiResponse != null) {
                            iApiResponse.onSuccess(responseCategory);
                        }
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSpecializedDoctorStatus> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

    public static void updatePatientDetails(RequestPatientUpdate requestPatientUpdate, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_DETAILS;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<CommonResponse> callCategory = ApiClient.getApiInterface().patientUpdate(token, header_UserInfo, LocalData.getUserUuid(), requestPatientUpdate);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }
    //==================================================


    public static void getTrusted_device(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_TRUSTED_DEVICE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseTrustedDevicesList> callCategory = ApiClient.getApiInterface().getTrusted_device(token, header_UserInfo, LocalData.getPhoneNumber());
        callCategory.enqueue(new Callback<ResponseTrustedDevicesList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTrustedDevicesList> call, @NonNull Response<ResponseTrustedDevicesList> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseTrustedDevicesList responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTrustedDevicesList> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void removeTrusted_device(String removePhoneNumber, String removeDeviceId, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_REMOVE_TRUSTED_DEVICE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        RequestRemoveTrustedDevice requestRemoveTrustedDevice = new RequestRemoveTrustedDevice(removeDeviceId, removePhoneNumber);

        Call<String> callCategory = ApiClient.getApiInterface().removeTrusted_device(token, header_UserInfo, requestRemoveTrustedDevice);
        callCategory.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    public static void getCallHistory(String status, String pageNumber, ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_CALL_HISTORY;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseCallHistoryModel> callCategory = ApiClient.getApiInterface().getCallHistory(token, header_UserInfo, LocalData.getUserUuid(), status, pageNumber, AppUrl.KEY_PER_PAGE_COUNT_VALUE);
        callCategory.enqueue(new Callback<ResponseCallHistoryModel>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCallHistoryModel> call, @NonNull Response<ResponseCallHistoryModel> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseCallHistoryModel responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCallHistoryModel> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void getOrderHistory(ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_ORDER_HISTORY;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<OrderHistoryResponseModel> callCategory = ApiClient.getApiInterface().getOrderHistory(token, header_UserInfo, LocalData.getPhoneNumber());
        callCategory.enqueue(new Callback<OrderHistoryResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<OrderHistoryResponseModel> call, @NonNull Response<OrderHistoryResponseModel> response) {
                try {
                    if (response.isSuccessful()) {
                        OrderHistoryResponseModel responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderHistoryResponseModel> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    public static void uploadImage(File imgFile, String imgFileName, String folderName, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PATIENT_LAB_REPORT_UPLOAD;
        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);
        Timber.e("token:" + token);


        RequestBody requestFile = RequestBody.create(AppUrlKey.MEDIA_TYPE_IMAGE_JPG, imgFile);
        MultipartBody.Part imageBody = MultipartBody.Part.createFormData(AppUrl.API_KEY_IMAGE, imgFileName, requestFile);

        ApiInterface jsonPostService = ApiClient.getClient(LocalData.getMetaInfoMetaData().getImageUploadBaseUrl()).create(ApiInterface.class);
        Call<CommonResponse> call = jsonPostService.imageUpload(token, header_UserInfo, imageBody, LocalData.getUserUuid(), folderName, AppKey.CHANNEL);

        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {

                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            ToastUtil.showToastMessage("Upload Failed Try Again");
                            return;
                        }

                        String imageUrl = LocalData.getMetaInfoMetaData().getImgBaseUrl() + response.body().file;
                        Timber.e("imageUrl::" + imageUrl);

                        iApiResponse.onSuccess(response.body());

                        //}
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                        Timber.e("Error:" + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        ToastUtil.showToastMessage("Upload Failed Try Again");
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                    Timber.e("Error:" + e);
                    ToastUtil.showToastMessage("Upload Failed Try Again");
                    // ApiManager.sendApiLogErrorApi(e, AppUrl.URL_PATIENT_LAB_REPORT_UPLOAD);

                }

                LoadingUtil.hide();
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable t) {
                LoadingUtil.hide();
                Timber.e("Error:" + t);
                ToastUtil.showToastMessage("Image Upload Failed Try Again");
                apiOnFailed(apiEndPoint, call, t, iApiResponse);
            }

        });

    }

    public static void getNurseList(String apiEndPoint, ApiManager.IApiResponse iApiResponse) {


        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        Call<ResponseCommonVendor> callCategory = ApiClient.getApiInterface().getNurseList(token, header_UserInfo, apiEndPoint);
        callCategory.enqueue(new Callback<ResponseCommonVendor>() {
            @Override
            public void onResponse(@NonNull Call<ResponseCommonVendor> call, @NonNull Response<ResponseCommonVendor> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseCommonVendor responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);

                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseCommonVendor> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void getMessages(String conversionId, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = "http://log.medico.bio/api/v1/chat-messge/" + conversionId;


        ApiManager.sendApiLog(AppKeyLog.NA, AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, apiEndPoint, "call Api");

        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }
        Call<ResponsemessageBody> callCategory = ApiClient.getApiInterface().getMessages(apiEndPoint);

        callCategory.enqueue(new Callback<ResponsemessageBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponsemessageBody> call, @NonNull Response<ResponsemessageBody> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponsemessageBody responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsemessageBody> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

/*
    public static void getPatientListMessages(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_MESSAGES;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        */
    /*  String url = "http://log.medico.bio/api/v1/chat-messge/user/chatlist?page_number=0&per_page_count=100";
     *//*


        String url = "";
        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<ResponseChatPatientList> callCategory = ApiClient.getApiInterface().getChatPatientMessages(url);
        callCategory.enqueue(new Callback<ResponseChatPatientList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseChatPatientList> call, @NonNull Response<ResponseChatPatientList> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseChatPatientList responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseChatPatientList> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }
*/


    //=================================================================
    private static Call<ResponseSingleDoctor> callCategory;

    public static void getDoctorCancel() {
        if (callCategory != null) {
            callCategory.cancel();
        }
    }

    public static void getDoctor(ApiManager.IApiResponse iApiResponse, boolean isFreeCall) {

        String apiEndPoint = AppUrl.URL_CALL_SINGLE_DOCTOR;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        callCategory = ApiClient.getApiInterface().getDoctor(token, header_UserInfo, isFreeCall);
        callCategory.enqueue(new Callback<ResponseSingleDoctor>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSingleDoctor> call, @NonNull Response<ResponseSingleDoctor> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseSingleDoctor responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);

                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSingleDoctor> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }


    //=================================================================
    public static void getPackages(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PACKAGE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<PackageModel> callCategory = ApiClient.getApiInterface().getPackages(token, header_UserInfo, LocalData.getUserUuid(), LocalData.getPhoneNumber(), AppKey.CHANNEL);
        callCategory.enqueue(new Callback<PackageModel>() {
            @Override
            public void onResponse(@NonNull Call<PackageModel> call, @NonNull Response<PackageModel> response) {
                try {
                    if (response.isSuccessful()) {
                        PackageModel responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<PackageModel> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    //=================================================================
    public static void getPackagesList(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PACKAGE_LIST;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponsePackList> callCategory = ApiClient.getApiInterface().getPackagesList(token, header_UserInfo, LocalData.getUserUuid(), LocalData.getPhoneNumber(), AppKey.CHANNEL);
        callCategory.enqueue(new Callback<ResponsePackList>() {
            @Override
            public void onResponse(@NonNull Call<ResponsePackList> call, @NonNull Response<ResponsePackList> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponsePackList responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponsePackList> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    public static void subInfo(ApiManager.IApiResponse iApiResponse) {


        String apiEndPoint = AppUrl.URL_SUB_INFO;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        String phoneNumber = LocalData.getPhoneNumber();
        if (phoneNumber.isEmpty()) {
            Timber.e("phoneNumber Empty found!");
            return;
        }

        Call<ResponseSubInfo> callCategory = ApiClient.getApiInterface().subInfo(token, header_UserInfo, phoneNumber);
        callCategory.enqueue(new Callback<ResponseSubInfo>() {
            @Override
            public void onResponse(@NonNull Call<ResponseSubInfo> call, @NonNull Response<ResponseSubInfo> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseSubInfo responseCategory = response.body();

                        LocalData.saveSubInfoData(responseCategory.getIsSubscribe());

                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseSubInfo> call, @NonNull Throwable throwable) {
                ApiManager.sendApiLogErrorApi(throwable, apiEndPoint);

                Timber.e("Error:" + throwable.toString());
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }


    public static void statusUpdate(String doctorUuid, String status, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_STATUS_UPDATE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        RequestStatusUpdate requestSingIn = new RequestStatusUpdate(doctorUuid, status);
        Call<CommonResponse> callCategory = ApiClient.getApiInterface().statusUpdate(token, header_UserInfo, requestSingIn);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {

                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }


    //=================================================================

    public static void getMedicationData(String type, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_MEDICATION_SURGERY_LIST;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseMedication> callCategory = ApiClient.getApiInterface().getMedicationSurgeryList(token, header_UserInfo, LocalData.getUserUuid(), type);
        callCategory.enqueue(new Callback<ResponseMedication>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMedication> call, @NonNull Response<ResponseMedication> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseMedication responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMedication> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void getLabReport(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_LAB_REPORT_LIST;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseLabReport> callCategory = ApiClient.getApiInterface().getLabReportList(token, header_UserInfo, LocalData.getUserUuid());
        callCategory.enqueue(new Callback<ResponseLabReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLabReport> call, @NonNull Response<ResponseLabReport> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseLabReport responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLabReport> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void labReportDelete(String id, String rev, ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_LAB_REPORT_DELETE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<CommonResponse> requstCall = ApiClient.getApiInterface().labReportDelete(token, header_UserInfo, id, rev);
        requstCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }


    public static void getPreviousPrescriptionList(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PREVIOUS_PRESCRIPTION_LIST;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseLabReport> callCategory = ApiClient.getApiInterface().getPreviousPrescriptionList(token, header_UserInfo, LocalData.getUserUuid());
        callCategory.enqueue(new Callback<ResponseLabReport>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLabReport> call, @NonNull Response<ResponseLabReport> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseLabReport responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLabReport> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }

    public static void getLabTestList(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_LAB_TEST_LIST;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseLabTestList> callCategory = ApiClient.getApiInterface().getLabTestList(token, header_UserInfo);
        callCategory.enqueue(new Callback<ResponseLabTestList>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLabTestList> call, @NonNull Response<ResponseLabTestList> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseLabTestList responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLabTestList> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void getMedicineProvider(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_MEDICINE_PROVIDER;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<ProviderResponse> callCategory = ApiClient.getApiInterface().getMedicineProvider(token, header_UserInfo);
        callCategory.enqueue(new Callback<ProviderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProviderResponse> call, @NonNull Response<ProviderResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        ProviderResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProviderResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void getLabProvider(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_LAB_REPORT_PROVIDER;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<ProviderResponse> callCategory = ApiClient.getApiInterface().getLabProvider(token, header_UserInfo);
        callCategory.enqueue(new Callback<ProviderResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProviderResponse> call, @NonNull Response<ProviderResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        ProviderResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProviderResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    public static void labPrescriptionDelete(String id, String rev, ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_PRESCRIPTION_DELETE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<CommonResponse> requstCall = ApiClient.getApiInterface().labPrescriptionDelete(token, header_UserInfo, id, rev);
        requstCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }


            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });
    }


    public static void postLabReportUrl(RequestLabReport requestLabReport, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_LAB_REPORT_FILE_URL_UPLOAD;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<CommonResponse> callCategory = ApiClient.getApiInterface().patientLabReportFileURLUpload(token, header_UserInfo, requestLabReport);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }

            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void postPrescriptionFileURLUpload(RequestLabReport requestLabReport, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_PREVIOUS_PRESCRIPTION_UPLOAD;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<CommonResponse> callCategory = ApiClient.getApiInterface().patientPrescriptionFileURLUpload(token, header_UserInfo, requestLabReport);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    CommonResponse responseCategory = response.body();
                    iApiResponse.onSuccess(responseCategory);
                } else {
                    responseCodeCheck(apiEndPoint, response, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }

    public static void postMedicationSurgery(RequestMedicationSurgeryInsert requestMedicationSurgeryInsert, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_MEDICATION_SURGERY_INSERT;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }


        Call<CommonResponse> callCategory = ApiClient.getApiInterface().medicationSurgeryInsert(token, header_UserInfo, requestMedicationSurgeryInsert);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    CommonResponse responseCategory = response.body();
                    iApiResponse.onSuccess(responseCategory);
                } else {
                    responseCodeCheck(apiEndPoint, response, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }


    public static void getHelpline(ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_HELPLINE;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<ResponseHelpLine> callCategory = ApiClient.getApiInterface().getHelpline(token, header_UserInfo, LocalData.getPhoneNumber());
        callCategory.enqueue(new Callback<ResponseHelpLine>() {
            @Override
            public void onResponse(@NonNull Call<ResponseHelpLine> call, @NonNull Response<ResponseHelpLine> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseHelpLine responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseHelpLine> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    //==============================================================================================


    public static void logApi(String uiType, String userActivity, String endPointType, String endPoint, String message) {

        if (AppUser.LOG_API_CALL_DISABLE) {
            return;
        }

        String endUrl = AppUrl.BASE_LOG_URL;
        String token = AppUrl.BASE_LOG_WRITE_TOKEN;

        String msisdn = LocalData.getPhoneNumber();
        String uuid = LocalData.getUserUuid();

        if (uuid.isEmpty()) {
            Timber.e("uuid:" + uuid);
            //return;
        }


        RequestActivityLog requestActivityLog = new RequestActivityLog(
                msisdn,
                uuid,
                DeviceIDUtil.getDeviceID(),
                uiType,
                userActivity,
                endPointType,
                endPoint,
                message,
                AppKey.CHANNEL,
                DeviceIDUtil.getAppVersion(),
                DeviceIDUtil.getOsVersion(),
                DeviceIDUtil.getDeviceName()

        );

        Call<String> callCategory = ApiClient.getApiInterface().logPrint(endUrl, token, requestActivityLog);
        callCategory.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                Timber.e("Error:" + throwable.toString());
            }
        });
    }


    public static void sendApiLogError(String uiType, String errorType, String endPoint, String message) {
        if (AppUser.LOG_API_CALL_DISABLE) {
            return;
        }

        Timber.e("Error:" + message);
        String endUrl = AppUrl.BASE_LOG_URL + "/error";
        String token = AppUrl.BASE_LOG_WRITE_TOKEN;

        String uuid = LocalData.getUserUuid();

        if (uuid.isEmpty()) {
            Timber.e("uuid:" + uuid);
            //return;
        }


        RequestActivityErrorLog requestActivityLog = new RequestActivityErrorLog(
                LocalData.getPhoneNumber(),
                LocalData.getUserUuid(),
                DeviceIDUtil.getDeviceID(),
                DeviceIDUtil.getAppVersion(),
                DeviceIDUtil.getOsVersion(),
                DeviceIDUtil.getDeviceName(),
                AppKey.CHANNEL,
                uiType,
                errorType,
                AppKeyLog.NA,
                AppKeyLog.NA,
                endPoint,
                message
        );

        Call<String> callCategory = ApiClient.getApiInterface().logPrintError(endUrl, token, requestActivityLog);
        callCategory.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {

                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable throwable) {
                Timber.e("Error:" + throwable.toString());
            }
        });
    }

    //==============================================================================================
    public static void unSubPack(String unSubUrl, ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = unSubUrl;


        Call<ResponseUnsubscribe> callCategory = ApiClient.getApiInterface().unSubPack(unSubUrl);
        ApiManager.IApiResponse finalIApiResponse = iApiResponse;
        callCategory.enqueue(new Callback<ResponseUnsubscribe>() {
            @Override
            public void onResponse(@NonNull Call<ResponseUnsubscribe> call, @NonNull Response<ResponseUnsubscribe> response) {
                try {

                    if (response.isSuccessful()) {
                        ResponseUnsubscribe responseMetaInfo = response.body();

                        if (finalIApiResponse != null) {
                            finalIApiResponse.onSuccess(responseMetaInfo);
                        }
                    } else {
                        responseCodeCheck(apiEndPoint, response, finalIApiResponse);
                    }
                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                    errorException(apiEndPoint, e, finalIApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseUnsubscribe> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, finalIApiResponse);

            }
        });
    }

    //==============================================================================================
    public static void getMetaInfo(ApiManager.IApiResponse iApiResponse) {
        String apiEndPoint = AppUrl.URL_META_INFO;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);

        String token = LocalData.getToken();
        Call<ResponseMetaInfo> callCategory = ApiClient.getApiInterface().getMetaInfo(token, header_UserInfo);
        ApiManager.IApiResponse finalIApiResponse = iApiResponse;
        callCategory.enqueue(new Callback<ResponseMetaInfo>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMetaInfo> call, @NonNull Response<ResponseMetaInfo> response) {
                try {

                    if (response.isSuccessful()) {
                        ResponseMetaInfo responseMetaInfo = response.body();
                        LocalData.saveData(responseMetaInfo);

                        if (finalIApiResponse != null) {
                            finalIApiResponse.onSuccess(responseMetaInfo);
                        }
                    } else {
                        responseCodeCheck(apiEndPoint, response, finalIApiResponse);
                    }
                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                    errorException(apiEndPoint, e, finalIApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMetaInfo> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, finalIApiResponse);

            }
        });
    }

    public static void getDrugList() {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            //   ToastUtil1.showNoInternetConnection();
            return;
        }

        String apiEndPoint = AppUrl.URL_DRUG;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            // return;
        }


        ApiManager.IApiResponse iApiResponse = new ApiManager.IApiResponse() {
            @Override
            public <T> void onSuccess(T model) {

            }

            @Override
            public void onFailed(String message) {
                Timber.e("Message:" + message);
            }
        };


        Call<List<ResponseDrugs>> callCategory = ApiClient.getApiInterface().getDrugList(token, header_UserInfo);
        callCategory.enqueue(new Callback<List<ResponseDrugs>>() {
            @Override
            public void onResponse(@NonNull Call<List<ResponseDrugs>> call, @NonNull Response<List<ResponseDrugs>> response) {
                try {

                    if (response.isSuccessful()) {
                        List<ResponseDrugs> responseCategory = response.body();
                        List<String> drugList = new ArrayList<>();

                        for (ResponseDrugs responseDrugs : responseCategory) {
                            drugList.add(responseDrugs.getName());
                        }
                        LocalData.saveDrugList(drugList);

                        //  iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }

                } catch (Exception exception) {
                    Timber.e("Error:" + exception.toString());
                    errorException(apiEndPoint, exception, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ResponseDrugs>> call, @NonNull Throwable throwable) {
                Timber.e("Error:" + throwable.toString());
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }


    public static void getDrugSearchList(String keyword, ApiManager.IApiResponse iApiResponse) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            //   ToastUtil1.showNoInternetConnection();
            return;
        }

        String apiEndPoint = AppUrl.URL_DRUG_SEARCH;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            // return;
        }


        Call<ResponseMedicine> callCategory = ApiClient.getApiInterface().getDrugSearchList(token, header_UserInfo, keyword);
        callCategory.enqueue(new Callback<ResponseMedicine>() {
            @Override
            public void onResponse(@NonNull Call<ResponseMedicine> call, @NonNull Response<ResponseMedicine> response) {

                if (response.isSuccessful()) {
                    ResponseMedicine responseCategory = response.body();

                    iApiResponse.onSuccess(responseCategory);

                } else {
                    responseCodeCheck(apiEndPoint, response, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseMedicine> call, @NonNull Throwable throwable) {
                // iApiResponse.onFailed("onFailed");
                Timber.e("Error:" + throwable.toString());
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);

            }
        });

    }


    public static void orderConfirm(OrderRequest orderRequest, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_ORDER_CONFIRM;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        if (token.isEmpty()) {
            Timber.e("Token not found!" + token);
            LoadingUtil.hide();
            return;
        }

        Call<CommonResponse> callCategory = ApiClient.getApiInterface().orderConfirm(token, header_UserInfo, orderRequest);
        callCategory.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(@NonNull Call<CommonResponse> call, @NonNull Response<CommonResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        CommonResponse responseCategory = response.body();
                        iApiResponse.onSuccess(responseCategory);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResponse> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }

    public static void orderMedicineWithPrescription(String address, String prescription_link, String prescriptionType, ApiManager.IApiResponse iApiResponse) {

        String apiEndPoint = AppUrl.URL_ORDER_CONFIRM;
        String header_UserInfo = UserDevices.getUserDevicesJson(apiEndPoint);


        String token = LocalData.getToken();

        RequestOrderMedicineWithPrescription requestOrderMedicineWithPrescription = new RequestOrderMedicineWithPrescription(
                LocalData.getUserName(), address, prescription_link, LocalData.getPhoneNumber(),prescriptionType
        );

        ApiManager.sendApiLog(AppKeyLog.UI_MEDICINE_ORDER, AppKeyLog.CLICK_BUTTON, AppKeyLog.NA, AppKeyLog.NA, "Try Medicine order prescription upload: "
                + JsonUtil.getJsonStringFromObject(requestOrderMedicineWithPrescription));


        Call<ResponseOrderMedicineWithPrescription> callCategory = ApiClient.getApiInterface().orderMedicineWithPrescription(token, header_UserInfo, requestOrderMedicineWithPrescription);
        callCategory.enqueue(new Callback<ResponseOrderMedicineWithPrescription>() {
            @Override
            public void onResponse(@NonNull Call<ResponseOrderMedicineWithPrescription> call, @NonNull Response<ResponseOrderMedicineWithPrescription> response) {
                try {
                    if (response.isSuccessful()) {
                        ResponseOrderMedicineWithPrescription body = response.body();
                        iApiResponse.onSuccess(body);
                    } else {
                        responseCodeCheck(apiEndPoint, response, iApiResponse);
                    }
                } catch (Exception e) {
                    errorException(apiEndPoint, e, iApiResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseOrderMedicineWithPrescription> call, @NonNull Throwable throwable) {
                apiOnFailed(apiEndPoint, call, throwable, iApiResponse);
            }
        });

    }


    //==============================================================================================
    //==============================================================================================


    private static void responseCodeCheck(String apiEndPoint, Response<?> response, ApiManager.IApiResponse iApiResponse) {

        try {
            switch (response.code()) {

                case AppUrl.UNAUTHORIZED_SERVER_CODE:

                    APIErrorEmon apiErrorEmon = ErrorUtils.parseErrorEmon(response);
                    iApiResponse.onFailed(apiErrorEmon.getMessage());

                    ApiManager.sendApiLog(AppKeyLog.NA, AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, apiEndPoint, apiErrorEmon.getMessage() + " | ResponseCode:" + response.code());

                    getNewTokenApi(new ApiManager.IApiResponseNewToken() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailed(String message) {

                        }
                    });

                    break;

                case AppUrl.ANOTHER_DEVICES_LOGIN_CODE:
                    APIErrorEmon error = ErrorUtils.parseErrorEmon(response);
                    Timber.e("Error:" + error.getMessage());
                    iApiResponse.onFailed("Login others Devices!");
                    ApiManager.sendApiLog(AppKeyLog.NA, AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, apiEndPoint, error.getMessage() + " | ResponseCode:" + response.code());

                    break;

                case AppUrl.VALIDATION_ERROR_CODE:
                    ErrorUtils.parchErrorMessage(response, apiEndPoint, iApiResponse);

                    break;

                case AppUrl.USER_BLOCK_CODE:
                    APIErrorEmon errorBlock = ErrorUtils.parseErrorEmon(response);
                    Timber.e("Error:" + errorBlock.getMessage());
                    iApiResponse.onFailed(errorBlock.getMessage());


                    UiNavigation.iNavigation.localLoginInfoRemove();
                    UiNavigation.iNavigation.goBlockUserActivity(errorBlock.getMessage());

                    break;


                case AppUrl.SERVER_ERROR_CODE:
                case AppUrl.SERVER_ERROR_CODE2:

                    String message = "Server can't be reached. Please try again..";
                    ToastUtil.showToastMessage("Server can't be reached. Please try again..");
                    ApiManager.sendApiLogErrorApi(apiEndPoint, message + " | ResponseCode:" + response.code());

                    break;

                default:

                    APIError apiError = ErrorUtils.parseError(response);
                    iApiResponse.onFailed(apiError.getMessage());

                    ApiManager.sendApiLogErrorApi(apiEndPoint, apiError.getMessage() + " | ResponseCode:" + response.code());

                    break;
            }

        } catch (Exception e) {
            Timber.e("Error:" + e.toString());
            errorException(apiEndPoint, e, iApiResponse);
        }

    }

    private static void responseCodeCheck(String apiEndPoint, Response<?> response, ApiManager.IApiResponseAuth iApiResponseAuth) {

        try {
            switch (response.code()) {

                case AppUrl.UNAUTHORIZED_SERVER_CODE:
                    APIErrorEmon apiErrorEmon = ErrorUtils.parseErrorEmon(response);
                    ApiManager.sendApiLog(AppKeyLog.NA, AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, apiEndPoint, apiErrorEmon.getMessage() + " | ResponseCode:" + response.code());

                    getNewTokenApi(new ApiManager.IApiResponseNewToken() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailed(String message) {

                        }
                    });
                    break;

                case AppUrl.ANOTHER_DEVICES_LOGIN_CODE:
                    APIErrorEmon error = ErrorUtils.parseErrorEmon(response);
                    Timber.e("Error:" + error.getMessage());
                    iApiResponseAuth.onFailed(error.getInstruction_url(), true);
                    break;

                case AppUrl.USER_BLOCK_CODE:
                    APIErrorEmon errorBlock = ErrorUtils.parseErrorEmon(response);
                    Timber.e("Error:" + errorBlock.getMessage());
                    iApiResponseAuth.onFailed(errorBlock.getMessage(), false);

                    UiNavigation.iNavigation.goBlockUserActivity(errorBlock.getMessage());

                    break;

                case AppUrl.VALIDATION_ERROR_CODE:
                    ErrorUtils.parchErrorMessage(response, iApiResponseAuth);
                    break;

                default:
                    APIError error12 = ErrorUtils.parseError(response);
                    iApiResponseAuth.onFailed(error12.getMessage(), false);
                    break;
            }

        } catch (Exception exception) {
            Timber.e("Error:" + exception.toString());
            errorException(apiEndPoint, exception, iApiResponseAuth);
        }

    }

    private static void errorException(String endpoint, Exception exception, ApiManager.IApiResponseAuth iApiResponse) {
        iApiResponse.onFailed("something went wrong!", false);
        ApiManager.sendApiLogErrorApi(exception, endpoint);
    }

    private static void errorException(String endpoint, Exception exception, ApiManager.IApiResponse iApiResponse) {
        iApiResponse.onFailed("something went wrong!");
        ApiManager.sendApiLogErrorApi(exception, endpoint);
    }

    //==============================================================================================
    private static void apiOnFailed(String apiEndPoint, Call<?> call, Throwable throwable, ApiManager.IApiResponse iApiResponse) {
        ApiManager.sendApiLogErrorApi(throwable, apiEndPoint);

        Timber.e("Error:" + throwable.toString());

        if (throwable instanceof SocketTimeoutException) {
            Timber.e("Connection Timeout");//Read timed out
            iApiResponse.onFailed("Network Error!");
        } else if (throwable instanceof IOException) {
            Timber.e("Timeout");
            iApiResponse.onFailed("Timeout");
        } else {

            //Call was cancelled by user
            if (call.isCanceled()) {
                Timber.e("Call was cancelled forcefully");

                Timber.e("Call was cancelled forcefully");
                iApiResponse.onFailed("");
            } else {
                //Generic error handling
                Timber.e("Network Error :: " + throwable.getLocalizedMessage());
                iApiResponse.onFailed("something went wrong.");
            }
        }
    }

    //==============================================================================================

    private static void apiOnFailed(String apiEndPoint, Call<?> call, Throwable throwable, ApiManager.IApiResponseAuth iApiResponse) {
        ApiManager.sendApiLogErrorApi(throwable, apiEndPoint);

        Timber.e("Error:" + throwable.toString());

        if (throwable instanceof SocketTimeoutException) {
            iApiResponse.onFailed("Network Error!", false);
        } else if (throwable instanceof SocketException) {
            Timber.e("Error:" + throwable.getMessage());
            iApiResponse.onFailed("Connection reset", false);

        } else if (throwable instanceof IOException) {
            Timber.e("Error:" + throwable.getMessage());

            iApiResponse.onFailed("Timeout", false);
        } else {

            //Call was cancelled by user
            if (call.isCanceled()) {
                Timber.e("Call was cancelled forcefully");
            } else {
                //Generic error handling
                Timber.e("Network Error :: " + throwable.getLocalizedMessage());
                iApiResponse.onFailed("onFailed", false);
            }
        }
    }


  /*  public static String getErrorMessage(Throwable throwable) {
        if (!isNetworkAvailable())
            return ERROR_NO_NETWORK_CONNECTION;

        if (throwable instanceof HttpException) {

            if (((HttpException) throwable).code() == 403)
                return ERROR_NO_SERVER_CONNECTION;

            try {
                return new JSONObject(((HttpException) throwable).response().errorBody().source().readUtf8()).getString("message");
            } catch (IOException | JSONException e) {
                return ERROR_BAD_REQUEST;
            }
        }
        return ERROR_UNEXPECTED;
    }*/

    //==============================================================================================


}