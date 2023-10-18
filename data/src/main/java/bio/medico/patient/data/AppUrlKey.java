package bio.medico.patient.data;

import okhttp3.MediaType;

public interface AppUrlKey {



    String TEXT_PLAIN = "text/plain";
    String IMAGE_JPG = "image/jpg";

    MediaType MEDIA_TYPE_TEXT_PLAIN = MediaType.parse(TEXT_PLAIN);
    MediaType MEDIA_TYPE_IMAGE_JPG = MediaType.parse(IMAGE_JPG);

    String ACCEPT = "Accept: application/json";
    String CONTENT_TYPE = "Content-Type: application/json";




/*

    String BASE_DISCOUNT_SEARCH_URL = "https://search.medico.bio/api/v1/";
    String BASE_LOG_URL = "http://log.medico.bio/api/v1/log";
    String BASE_LOG_WRITE_TOKEN = "Bearer 1pake4mh5ln64h5t26kpvm3iri";

    String URL_BASE_DEVELOPMENT = "https://beta.api.medico.bio/";
    String URL_BASE_PRODUCTION = "https://api.v2.medico.bio/";
    //  String URL_BASE_PRODUCTION = "https://api.medico.bio/";


    String URL_SPECIALIZED_DOCTOR_PRODUCTION = "https://meet.medico.bio/";
    String URL_SPECIALIZED_DOCTOR_DEVELOPMENT = "https://meet.medico.bio/";
  //  String URL_SPECIALIZED_DOCTOR_DEVELOPMENT = "https://beta.meet.medico.bio/";


    static String getBaseurl() {
        String url = URL_BASE_PRODUCTION;
        switch (AppUser.BUILD_MODE) {
            case AppUser.DEVELOPMENT:
                url = URL_BASE_DEVELOPMENT;
                break;

            case AppUser.PRODUCTION:
            default:
                url = URL_BASE_PRODUCTION;
                break;
        }

        Timber.d("URL_BASE:" + url);
        return url;
    }

    static String getSpecializedDoctorBaseurl() {
        String url = URL_SPECIALIZED_DOCTOR_PRODUCTION;
        switch (AppUser.BUILD_MODE) {
            case AppUser.DEVELOPMENT:
                url = URL_SPECIALIZED_DOCTOR_DEVELOPMENT;
                break;

            case AppUser.PRODUCTION:
            default:
                url = URL_SPECIALIZED_DOCTOR_PRODUCTION;
                break;
        }

        Timber.d("SpecializedDoctorBaseurl:" + url);
        return url;
    }

    //=========================================================
    String URL_SPECIALIST_DOCTOR_STATUS = "specialistDoctor/status";
    String URL_SPECIALIST_DOCTOR_SCHEDULE_LIST = "doctorSchedule/{user_id}";
    String URL_SPECIALIST_DOCTOR_BOOKED_SCHEDULE = "doctorSchedule/getBookedSchedule/{user_id}";
    String URL_SPECIALIST_DOCTOR_BOOKING = "doctorBooking";
    String URL_SPECIALIST_DOCTOR_QUEUE_LIST = "specialistDoctor/getQueueList/{doctorId}/{patientId}/{scheduleId}";
    String URL_SPECIALIST_DOCTOR_QUEUE_CALLING_STATUS_UPDATE = "doctorAssistant/callingStatusUpdate/{patientId}/{doctorId}/{scheduleId}/{queueStatus}";

    String URL_MESSAGES = "messages/";

    String URL_META_INFO = "metaInfo";//meta_info

    String URL_DRUG = "drug";
    String URL_DRUG_SEARCH = AppUrl.URL_DRUG + "/{" + AppUrl.KEY_KEYWORD + "}";
    String URL_DRUG_order_use_prescription = "drug/order-use-prescription";


    String URL_PATIENT_CREATE_PROFILE = "/patient/passwordless-singup";
    String URL_PATIENT_LOGIN = "patient/passwordless-login";
    String URL_PATIENT_PASSWORD_LESS_LOGOUT = "/patient/passwordless-logout";

    String URL_PATIENT_PROFILE = "patient/profile/";

    String URL_PATIENT_OTP_VERIFICATION = "otp/otp-verification";
    String URL_PATIENT_OTP_SEND = "otp/send-otp"; //for resend
    String URL_PATIENT_TOKEN = "patient/token";


    String URL_PATIENT_DETAILS = "patient/";
    String URL_FIREBASE_TOKEN_UPDATE = "firebase";
    String URL_MEDICATION_SURGERY_INSERT = "medicationAndSurgery/";
    String URL_PATIENT_PROFILE_PICTURE = "uploadImageApi";
    String URL_PATIENT_LAB_REPORT_UPLOAD = "labReportUploadApi";

    String URL_MEDICATION_SURGERY_LIST = "medicationAndSurgery/patient/";

    String URL_TRUSTED_DEVICE = "patient/trusted-device";
    String URL_REMOVE_TRUSTED_DEVICE = "patient/remove-trusted-device";


    String URL_PATIENT_FOLLOW_UP = "patient/followUp";
    String URL_LAB_REPORT_FILE_URL_UPLOAD = "labReport";
    String URL_LAB_REPORT_DELETE = "labReport/";
    String URL_PRESCRIPTION_DELETE = "previewPrescription/";
    String URL_LAB_REPORT_LIST = "labReport/patient/";
    String URL_PREVIOUS_PRESCRIPTION_UPLOAD = "previewPrescription";
    String URL_PREVIOUS_PRESCRIPTION_LIST = "previewPrescription/patient/";
    String URL_LAB_TEST_LIST = "labTestList";
    String URL_CALL_HISTORY = "/callHistory/patient/";
    String URL_ORDER_HISTORY = "drug/order-history/";
    String URL_STATUS_UPDATE = "doctor/statusUpdate";


    String URL_CALL_SINGLE_DOCTOR = "/doctor/callDoctor";
    String URL_PRESCRIPTION_DETAILS = "/iframe-prescription/";


    String URL_PACKAGE_LIST = "packages/list/";
    String URL_PACKAGE = "packages";
    // String URL_SUB_INFO = "/subscribetion/sub-info";
    String URL_SUB_INFO = "/subscribetion/sub-info-medico";

    String URL_MEDICINE_PROVIDER = "onlineDeliveryProvider/category/medicine";
    String URL_LAB_REPORT_PROVIDER = "onlineDeliveryProvider/category/lab_report";
    String URL_ORDER_CONFIRM = "drug/place-order";
    String URL_HELPLINE = "helpline/";


    //========================================================================================
    String HEADER_CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json";
    String HEADER_AUTHORIZATION = "Authorization";
    String HEADER_USER_INFO = "UserInfo";


    ///========================================================================
    String KEY_REV = "rev";
    String KEY_ID = "id";
    String KEY_UUID = "uuid";
    String KEY_FOLDER_NAME = "folderName";
    String KEY_CHANNEL = "channel";
    String KEY_STATUS = "status";
    String KEY_SENDER = "sender";
    String KEY_RECEIVER = "receiver";
    String KEY_LIMIT = "limit";
    String KEY_KEYWORD = "keyword";
    String KEY_TYPE = "type";
    String KEY_PAGE_NUMBER = "pageNumber";
    String KEY_MSISDN = "msisdn";
    String KEY_PER_PAGE_COUNT = "perpageCount";
    String KEY_PER_PAGE_COUNT_VALUE = "10";
    String KEY_MESSAGE = "message";//message
    String KEY_DOCTOR_ROOM = "DOCTOR_ROOM";//DOCTOR_ROOM

    String KEY_patientId = "patientId";
    String KEY_doctorUUID = "doctorUUID";
    String KEY_scheduleId = "scheduleId";
    String KEY_queueStatus = "queueStatus";

    String STATUS_COMPLETED = "completed";
    String STATUS_INCOMPLETED = "save_draft";
    String STATUS_DROPPED = "dropped";
    //===================================================================================


    //===============================================
    int UNAUTHORIZED_SERVER_CODE = 429; //token expire
    int ANOTHER_DEVICES_LOGIN_CODE = 438;
    int USER_BLOCK_CODE = 418;

    int USER_DOCTOR_BOOKED_LIMIT_CODE = 426;

    int SERVER_UNEXPECTED_ERROR_CODE = 422; //server issue
    int VALIDATION_ERROR_CODE = 401;
    int SERVER_ERROR_CODE = 502;
    int SERVER_ERROR_CODE2 = 500;
    //===============================================

*/

}
