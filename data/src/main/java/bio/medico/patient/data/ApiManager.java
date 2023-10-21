package bio.medico.patient.data;

import android.os.AsyncTask;

import com.farmer.primary.network.dataSource.local.LocalData;
import com.skh.hkhr.util.view.LoadingUtil;
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils;
import com.theroyalsoft.mydoc.apputil.log.ClassLineNoUtil;

import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import bio.medico.patient.common.AppKeyLog;
import bio.medico.patient.model.apiResponse.OrderRequest;
import bio.medico.patient.model.apiResponse.RequestLabReport;
import bio.medico.patient.model.apiResponse.RequestMedicationSurgeryInsert;
import bio.medico.patient.model.apiResponse.RequestPatientUpdate;
import timber.log.Timber;

public class ApiManager {

    public static void requestLogin(String mobile, IApiResponseAuth iApiResponseAuth) {
        ApiManagerNew.requestLogin(mobile, iApiResponseAuth);
    }

    public static void requestOTPOTPVerification(String mobile, String otp, boolean isTrusted, ApiManager.IApiResponseAuth iApiResponseAuth) {
        ApiManagerNew.requestOTPOTPVerification(mobile, otp, isTrusted, iApiResponseAuth);
    }

    public static void requestOTPSend(String mobile, String otpType, IApiResponse iApiResponse) {
        ApiManagerNew.requestOTPSend(mobile, otpType, iApiResponse);
    }

    public static void getNewTokenApi(IApiResponseNewToken iApiResponse) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            return;
        }

        ApiManagerNew.getNewTokenApi(iApiResponse);
    }

    public static void requestNewUserProfileCreate(String name, String mobile, String birthday, ApiManager.IApiResponse iApiResponse) {

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.requestNewUserProfileCreate(name, mobile, birthday, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    LoadingUtil.hide();
                }
            });
            return;
        }
        ApiManagerNew.requestNewUserProfileCreate(name, mobile, birthday, iApiResponse);
    }

    public static void requestLogOut(ApiManager.IApiResponse iApiResponse) {

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.requestLogOut(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }


        ApiManagerNew.requestLogOut(iApiResponse);
    }


    public static void updateFirebaseToken(String token1, boolean isLogin, IApiResponse iApiResponse) {

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.updateFirebaseToken(token1, isLogin, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.updateFirebaseToken(token1, isLogin, iApiResponse);

    }


    public static void getFollowUp(IApiResponse iApiResponseToken) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getFollowUp(iApiResponseToken);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponseToken.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getFollowUp(iApiResponseToken);

    }


    //==================================================
    public static void getPatientDetails(String msisdn, IApiResponse iApiResponse) {

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPatientDetails(msisdn, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }

        ApiManagerNew.getPatientDetails(msisdn, iApiResponse);
    }


    public static void getPatientDetails(IApiResponse iApiResponseToken) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPatientDetails(iApiResponseToken);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponseToken.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getPatientDetails(iApiResponseToken);

    }

    public static void getSpecialistDoctorStatus(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getSpecialistDoctorStatus(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getSpecialistDoctorStatus(iApiResponse);

    }


    public static void updatePatientDetails(RequestPatientUpdate requestPatientUpdate, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.updatePatientDetails(requestPatientUpdate, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.updatePatientDetails(requestPatientUpdate, iApiResponse);

    }
    //==================================================


    public static void getTrusted_device(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getTrusted_device(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed("");
                }
            });
            return;
        }
        ApiManagerNew.getTrusted_device(iApiResponse);

    }

    public static void removeTrusted_device(String removePhoneNumber, String removeDeviceId, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.removeTrusted_device(removePhoneNumber, removeDeviceId, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.removeTrusted_device(removePhoneNumber, removeDeviceId, iApiResponse);

    }


    public static void getCallHistory(String status, String pageNumber, IApiResponse iApiResponseToken) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getCallHistory(status, pageNumber, iApiResponseToken);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponseToken.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getCallHistory(status, pageNumber, iApiResponseToken);

    }


    public static void getOrderHistory(IApiResponse iApiResponseToken) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getOrderHistory(iApiResponseToken);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    LoadingUtil.hide();
                }
            });
            return;
        }
        ApiManagerNew.getOrderHistory(iApiResponseToken);

    }

    public static void getNurseList(String apiEndPoint, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getNurseList(apiEndPoint, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getNurseList(apiEndPoint, iApiResponse);

    }

    public static void uploadImage(File imgFile, String imgFileName, String apiEndPoint, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.uploadImage(imgFile, imgFileName, apiEndPoint, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.uploadImage(imgFile, imgFileName, apiEndPoint, iApiResponse);

    }

    public static void getMessages(String conversionId, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getMessages(conversionId, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getMessages(conversionId, iApiResponse);

    }

 /*   public static void getPatientlistMessages(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPatientListMessages(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getPatientListMessages(iApiResponse);

    }
*/

    //=================================================================


    public static void getDoctorCancel() {
        ApiManagerNew.getDoctorCancel();
    }

    public static void getDoctor(IApiResponse iApiResponseToken, boolean isFreeCall) {

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getDoctor(iApiResponseToken, isFreeCall);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponseToken.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getDoctor(iApiResponseToken, isFreeCall);

    }


    //=================================================================
    public static void getPackages(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPackages(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getPackages(iApiResponse);

    }

    //=================================================================
    public static void getPackagesList(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPackagesList(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getPackagesList(iApiResponse);

    }

    public static void subInfo(IApiResponse iApiResponse) {

        if (iApiResponse == null) {
            iApiResponse = new ApiManager.IApiResponse() {
                @Override
                public <T> void onSuccess(T model) {

                }

                @Override
                public void onFailed(String message) {

                }
            };
        }

        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            IApiResponse finalIApiResponse = iApiResponse;
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.subInfo(finalIApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);

                    finalIApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.subInfo(iApiResponse);
    }

    public static void statusUpdate(String doctorUuid, String status, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.statusUpdate(doctorUuid, status, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.statusUpdate(doctorUuid, status, iApiResponse);
    }


    //=================================================================

    public static void getMedicationData(String type, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getMedicationData(type, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getMedicationData(type, iApiResponse);

    }

    public static void getLabReport(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getLabReport(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getLabReport(iApiResponse);

    }

    public static void labReportDelete(String id, String rev, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.labReportDelete(id, rev, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.labReportDelete(id, rev, iApiResponse);
    }


    public static void getPreviousPrescriptionList(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getPreviousPrescriptionList(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getPreviousPrescriptionList(iApiResponse);
    }

    public static void getLabTestList(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getLabTestList(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getLabTestList(iApiResponse);

    }

    public static void getMedicineProvider(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getMedicineProvider(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getMedicineProvider(iApiResponse);

    }

    public static void getLabProvider(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getLabProvider(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getLabProvider(iApiResponse);

    }

    public static void orderConfirm(OrderRequest orderRequest, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.orderConfirm(orderRequest, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.orderConfirm(orderRequest, iApiResponse);

    }

    public static void orderMedicineWithPrescription(String address, String prescription_link,String prescriptionType, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.orderMedicineWithPrescription(address, prescription_link,prescriptionType, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.orderMedicineWithPrescription(address, prescription_link,prescriptionType, iApiResponse);

    }


    public static void labPrescriptionDelete(String id, String rev, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.labPrescriptionDelete(id, rev, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.labPrescriptionDelete(id, rev, iApiResponse);
    }


    public static void postLabReportUrl(RequestLabReport requestLabReport, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.postLabReportUrl(requestLabReport, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.postLabReportUrl(requestLabReport, iApiResponse);

    }

    public static void postPrescriptionFileURLUpload(RequestLabReport requestLabReport, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.postPrescriptionFileURLUpload(requestLabReport, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.postPrescriptionFileURLUpload(requestLabReport, iApiResponse);

    }

    public static void postMedicationSurgery(RequestMedicationSurgeryInsert requestMedicationSurgeryInsert, IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.postMedicationSurgery(requestMedicationSurgeryInsert, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.postMedicationSurgery(requestMedicationSurgeryInsert, iApiResponse);

    }

    public static void getHelpline(IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getHelpline(iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }
        ApiManagerNew.getHelpline(iApiResponse);

    }

    //==============================================================================================
    public static void sendApiLog(String uiType, String userActivity, String endPointType, String endPoint, String message) {
        AsyncTask.execute(() -> {
            ApiManagerNew.logApi(uiType, userActivity, endPointType, endPoint, message);
        });
    }


    public static void sendApiLogDelay(String uiType, String userActivity, String endPointType, String endPoint, String message) {
        AsyncTask.execute(() -> {
            timmer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ApiManagerNew.logApi(uiType, userActivity, endPointType, endPoint, message);
                }
            }, getRandomDelaySec());
        });
    }

    private static Timer timmer = new Timer();

    private static long getRandomDelaySec() {
        Random random = new Random();
        int sec = random.nextInt(60 - 1) + 60;
        int milliSec = random.nextInt(1000 - 1) + 1000;

        long delaySec = sec * 1000 + milliSec;
        return delaySec;
    }

    public static void sendApiLogAppOpen(String uiType) {
        AsyncTask.execute(() -> {
            String userName = LocalData.getUserName().isEmpty() ? "" : " >> USER_NAME: " + LocalData.getUserName();

           // ApiManagerNew.logApi(uiType, AppKeyLog.UI_OPEN, AppKeyLog.NA, AppKeyLog.NA, "OpenUI" + userName);
        });
    }

    public static void sendApiLogEndpointApi(String uiType, String userActivity, String endPoint, String message) {
        AsyncTask.execute(() -> {
           // ApiManagerNew.logApi(uiType, userActivity, AppKeyLog.ENDPOINT_TYPE_API, endPoint, message);
        });
    }

    public static void sendApiLogEndpointSocket(String uiType, String userActivity, String endPoint, String message) {
        AsyncTask.execute(() -> {
           // ApiManagerNew.logApi(uiType, userActivity, AppKeyLog.ENDPOINT_TYPE_SOCKET_LISTENER, endPoint, message);
        });
    }


    public static void sendApiLogErrorApi(String endpoint, String message) {
        if (endpoint.equals(AppUrl.URL_CALL_SINGLE_DOCTOR)) {
            Timber.e("Not send error Api log>>> because not error" + endpoint);
            return;//
        }

        AsyncTask.execute(() -> {
           // ApiManagerNew.sendApiLogError(AppKeyLog.NA, AppKeyLog.ENDPOINT_TYPE_API, endpoint, message);
        });
    }

    public static void sendApiLogErrorApi(Throwable exception, String endpoint) {
        AsyncTask.execute(() -> {
           // ApiManagerNew.sendApiLogError(ClassLineNoUtil.getLineNumber(exception), AppKeyLog.ENDPOINT_TYPE_API, endpoint, exception.toString());
        });
    }

    public static void sendApiLogErrorApi(Exception exception, String endpoint) {
        AsyncTask.execute(() -> {
          //  ApiManagerNew.sendApiLogError(ClassLineNoUtil.getLineNumber(exception), AppKeyLog.ENDPOINT_TYPE_API, endpoint, exception.toString());

        });
    }

    public static void sendApiLogErrorApiDelay(Exception exception, String endpoint) {

        AsyncTask.execute(() -> {
            timmer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ApiManagerNew.sendApiLogError(ClassLineNoUtil.getLineNumber(exception), AppKeyLog.ENDPOINT_TYPE_API, endpoint, exception.toString());
                }
            }, getRandomDelaySec());
        });
    }

    public static void sendApiLogErrorCodeScope(Exception exception) {
        AsyncTask.execute(() -> {
            //ApiManagerNew.sendApiLogError(ClassLineNoUtil.getLineNumber(exception), AppKeyLog.CODE_SCOPE, AppKeyLog.NA, exception.toString());
        });
    }


    //==============================================================================================
    public static void unSubPack(String unSubUrl, IApiResponse iApiResponse) {
        ApiManagerNew.unSubPack(unSubUrl, iApiResponse);
    }

    //==============================================================================================
    public static void getMetaInfo(IApiResponse iApiResponse) {
        ApiManagerNew.getMetaInfo(iApiResponse);
    }

    public static void getMetaInfo() {
        ApiManager.getMetaInfo(new ApiManager.IApiResponse() {
            @Override
            public <T> void onSuccess(T model) {
            }

            @Override
            public void onFailed(String message) {
            }
        });
    }

    public static void getDrugList() {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getDrugList();
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                }
            });
            return;
        }

        ApiManagerNew.getDrugList();

    }

    public static void getDrugSearchList(String keyword, ApiManager.IApiResponse iApiResponse) {
        boolean isTokenExpire = JWTUtils.isTokenExpire();
        Timber.e("isTokenExpire:" + isTokenExpire);
        if (JWTUtils.isTokenExpire()) {
            getNewTokenApi(new IApiResponseNewToken() {
                @Override
                public void onSuccess() {
                    ApiManagerNew.getDrugSearchList(keyword, iApiResponse);
                }

                @Override
                public void onFailed(String message) {
                    Timber.e("Failed:" + message);
                    iApiResponse.onFailed(message);
                }
            });
            return;
        }

        ApiManagerNew.getDrugSearchList(keyword, iApiResponse);

    }


    //==============================================================================================

    //==============================================================================================
    public interface IApiResponse {
        <T> void onSuccess(T model);

        void onFailed(String message);
    }

    public interface IApiResponseAuth {
        <T> void onSuccess(T model);

        void onFailed(String message, boolean foundOtherDevicesLogin);
    }

    public interface IApiResponseNewToken {
        void onSuccess();

        void onFailed(String message);
    }

}
