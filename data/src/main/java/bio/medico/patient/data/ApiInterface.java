package bio.medico.patient.data;

import java.util.List;

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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    //=========================================================================================

    @POST(AppUrl.URL_PATIENT_LOGIN)
    Call<ResponseLogin> login(
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestLogin requestLogin
    );


    @POST(AppUrl.URL_PATIENT_PASSWORD_LESS_LOGOUT)
    Call<String> logOut(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestLogout requestLogout
    );


    @Headers(AppUrl.HEADER_CONTENT_TYPE_APPLICATION_JSON)
    @POST(AppUrl.URL_PATIENT_TOKEN)
    Call<ResponseNewToken> getNewToken(
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestNewToken requestSignIn);


    @GET(AppUrl.URL_PATIENT_PROFILE + "{" + AppUrl.KEY_MSISDN + "}")
    Call<ResponsePatientInfo> getLoginPatientDetails(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_MSISDN) String msisdn
    );

    @GET(AppUrl.URL_PATIENT_DETAILS + "{uuid}")
    Call<ResponsePatientInfo> getPatientDetails(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid
    );

    @GET(AppUrl.URL_SPECIALIST_DOCTOR_STATUS)
    Call<ResponseSpecializedDoctorStatus> getSpecialistDoctorStatus(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo
    );

    @PUT(AppUrl.URL_PATIENT_DETAILS + "update/" + "{uuid}")
    Call<CommonResponse> patientUpdate(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid,
            @Body RequestPatientUpdate patientUpdate);

    @DELETE(AppUrl.URL_LAB_REPORT_DELETE + "{id}/{rev}")
    Call<CommonResponse> labReportDelete(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_ID) String uuid,
            @Path(AppUrl.KEY_REV) String rev);


    @DELETE(AppUrl.URL_PRESCRIPTION_DELETE + "{id}/{rev}")
    Call<CommonResponse> labPrescriptionDelete(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_ID) String uuid,
            @Path(AppUrl.KEY_REV) String rev);

    @Multipart
    @POST(AppUrl.URL_PATIENT_PROFILE_PICTURE)
    Call<CommonResponse> patientProfilePictureUpdate(
            @Part MultipartBody.Part image,
            @Part(AppUrl.KEY_ID) RequestBody user_id,
            @Part(AppUrl.KEY_FOLDER_NAME) RequestBody folder,
            @Part(AppUrl.KEY_CHANNEL) RequestBody channel
    );

    //@Headers("Content-Type: application/json")
    @Multipart
    @POST(AppUrl.URL_PATIENT_LAB_REPORT_UPLOAD)
    Call<CommonResponse> imageUpload(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Part MultipartBody.Part imageBody,
            @Query(AppUrl.KEY_ID) String user_id,
            @Query(AppUrl.KEY_FOLDER_NAME) String folder,
            @Query(AppUrl.KEY_CHANNEL) String channel);

    @POST(AppUrl.URL_LAB_REPORT_FILE_URL_UPLOAD)
    Call<CommonResponse> patientLabReportFileURLUpload(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestLabReport patientUpdate);

    @POST(AppUrl.URL_PREVIOUS_PRESCRIPTION_UPLOAD)
    Call<CommonResponse> patientPrescriptionFileURLUpload(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestLabReport patientUpdate);

    @GET(AppUrl.URL_CALL_HISTORY + "{uuid}" + "/{status}")
    Call<ResponseCallHistoryModel> getCallHistory(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid,
            @Path(AppUrl.KEY_STATUS) String status,
            @Query(AppUrl.KEY_PAGE_NUMBER) String pageNumber,
            @Query(AppUrl.KEY_PER_PAGE_COUNT) String perpageCount
    );

    @GET(AppUrl.URL_ORDER_HISTORY + "{msisdn}")
    Call<OrderHistoryResponseModel> getOrderHistory(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_MSISDN) String msisdn
    );

    @GET(AppUrl.URL_DRUG)
    Call<List<ResponseDrugs>> getDrugList(@Header(AppUrl.HEADER_AUTHORIZATION) String token,
                                          @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo);

    @GET(AppUrl.URL_DRUG_SEARCH)
    Call<ResponseMedicine> getDrugSearchList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_KEYWORD) String keyword
    );

    @POST(AppUrl.URL_DRUG_order_use_prescription)
    Call<ResponseOrderMedicineWithPrescription> orderMedicineWithPrescription(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestOrderMedicineWithPrescription requestOrderMedicineWithPrescription
    );


    @GET("{type}")
    Call<ResponseCommonVendor> getNurseList(@Header(AppUrl.HEADER_AUTHORIZATION) String token,
                                            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
                                            @Path("type") String apiEndPoint
    );

    @POST(AppUrl.URL_PATIENT_FOLLOW_UP)
    Call<ResponseFollowUp> getCallFollowApi(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestFollowUp requestFollowUp
    );


    @GET(AppUrl.URL_TRUSTED_DEVICE + "/{" + AppUrl.KEY_MSISDN + "}")
    Call<ResponseTrustedDevicesList> getTrusted_device(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_MSISDN) String sender
    );

    @POST(AppUrl.URL_REMOVE_TRUSTED_DEVICE)
    Call<String> removeTrusted_device(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestRemoveTrustedDevice requestRemoveTrustedDevice
    );


    @GET
    Call<ResponsemessageBody> getMessages(@Url String url);

  /*  @GET
    Call<ResponseChatPatientList> getChatPatientMessages(@Url String url);

    @GET("{sender}" + "/{receiver}")
    Call<String> getMessages1(
            @Path(AppUrl.KEY_SENDER) String sender,
            @Path(AppUrl.KEY_RECEIVER) String receiver,
            @Query(AppUrl.KEY_LIMIT) String limit
    );


   */

    @POST(AppUrl.URL_FIREBASE_TOKEN_UPDATE)
    Call<CommonResponse> updateFirebaseTokenUpdate(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestFirebaseTokenUpdate requestFirebaseTokenUpdate);


    @POST(AppUrl.URL_PATIENT_CREATE_PROFILE)
    Call<CommonResponse> requestReg(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestSignUp requestSignUp);


    @POST(AppUrl.URL_PATIENT_OTP_SEND)
    Call<CommonResponse> sendOTP(
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestOtpSend requestOtpSend);

    @POST(AppUrl.URL_MEDICATION_SURGERY_INSERT)
    Call<CommonResponse> medicationSurgeryInsert(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestMedicationSurgeryInsert requestMedicationSurgeryInsert);


    @POST(AppUrl.URL_PATIENT_OTP_VERIFICATION)
    Call<ResponseOtpVerify> requestOTPverification(
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestOtpVerify requestOtpVerify);


    @GET(AppUrl.URL_CALL_SINGLE_DOCTOR)
    Call<ResponseSingleDoctor> getDoctor(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Query("isFreeCall") boolean isFreeCall
    );

    @POST(AppUrl.URL_STATUS_UPDATE)
    Call<CommonResponse> statusUpdate(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body RequestStatusUpdate requestStatusUpdate);


    @POST(AppUrl.URL_SUB_INFO + "/{" + AppUrl.KEY_MSISDN + "}")
    Call<ResponseSubInfo> subInfo(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_MSISDN) String msisdn);


    @GET(AppUrl.URL_MEDICATION_SURGERY_LIST + "{uuid}" + "/{type}")
    Call<ResponseMedication> getMedicationSurgeryList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid,
            @Path(AppUrl.KEY_TYPE) String type
    );

    @GET(AppUrl.URL_LAB_REPORT_LIST + "{uuid}")
    Call<ResponseLabReport> getLabReportList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid
    );

    @GET(AppUrl.URL_PREVIOUS_PRESCRIPTION_LIST + "{uuid}")
    Call<ResponseLabReport> getPreviousPrescriptionList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_UUID) String uuid
    );

    @GET(AppUrl.URL_PACKAGE)
    Call<PackageModel> getPackages(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Query("patientUid") String patientUid,
            @Query("patientPhoneNumber") String patientPhoneNumber,
            @Query("chanel") String chanel
    );

    @GET(AppUrl.URL_PACKAGE_LIST)
    Call<ResponsePackList> getPackagesList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Query("patientUid") String patientUid,
            @Query("patientPhoneNumber") String patientPhoneNumber,
            @Query("chanel") String chanel
    );

    @GET(AppUrl.URL_LAB_TEST_LIST)
    Call<ResponseLabTestList> getLabTestList(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo
    );

    @GET(AppUrl.URL_MEDICINE_PROVIDER)
    Call<ProviderResponse> getMedicineProvider(@Header(AppUrl.HEADER_AUTHORIZATION) String token,
                                               @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo);


    @GET(AppUrl.URL_LAB_REPORT_PROVIDER)
    Call<ProviderResponse> getLabProvider(@Header(AppUrl.HEADER_AUTHORIZATION) String token,
                                          @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo);


    @POST(AppUrl.URL_ORDER_CONFIRM)
    Call<CommonResponse> orderConfirm(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Body OrderRequest orderRequest);


    @GET(AppUrl.URL_HELPLINE + "{" + AppUrl.KEY_MSISDN + "}")
    Call<ResponseHelpLine> getHelpline(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo,
            @Path(AppUrl.KEY_MSISDN) String uuid
    );

    //=========================================================================
    @GET(AppUrl.URL_META_INFO)
    Call<ResponseMetaInfo> getMetaInfo(
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Header(AppUrl.HEADER_USER_INFO) String header_UserInfo
    );


    @GET
    Call<ResponseUnsubscribe> unSubPack(@Url String url);

    @POST
    Call<String> logPrint(
            @Url String url,
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Body RequestActivityLog requestActivityLog
    );

    @POST
    Call<String> logPrintError(
            @Url String url,
            @Header(AppUrl.HEADER_AUTHORIZATION) String token,
            @Body RequestActivityErrorLog requestActivityErrorLog
    );

}
