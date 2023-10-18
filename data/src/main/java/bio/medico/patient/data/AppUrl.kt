package bio.medico.patient.data

import bio.medico.patient.common.AppUser
import timber.log.Timber

object AppUrl {


    ///========================================================================
    const val KEY_REV = "rev"
    const val KEY_ID = "id"
    const val KEY_UUID = "uuid"
    const val KEY_FOLDER_NAME = "folderName"
    const val KEY_CHANNEL = "channel"
    const val KEY_STATUS = "status"
    const val KEY_SENDER = "sender"
    const val KEY_RECEIVER = "receiver"
    const val KEY_LIMIT = "limit"
    const val KEY_KEYWORD = "keyword"
    const val KEY_TYPE = "type"
    const val KEY_PAGE_NUMBER = "pageNumber"
    const val KEY_MSISDN = "msisdn"
    const val KEY_PER_PAGE_COUNT = "perpageCount"
    const val KEY_PER_PAGE_COUNT_VALUE = "10"
    const val KEY_MESSAGE = "message" //message
    const val KEY_DOCTOR_ROOM = "DOCTOR_ROOM" //DOCTOR_ROOM
    const val KEY_patientId = "patientId"
    const val KEY_doctorUUID = "doctorUUID"
    const val KEY_scheduleId = "scheduleId"
    const val KEY_queueStatus = "queueStatus"
    const val STATUS_COMPLETED = "completed"
    const val STATUS_INCOMPLETED = "save_draft"
    const val STATUS_DROPPED = "dropped"

    const val patientId = "patientId"
    const val doctorId = "doctorId"
    const val scheduleId = "scheduleId"
    const val queueStatus = "queueStatus"
    const val user_id = "user_id"
    //===================================================================================


    val baseurl: String
        get() {
            var url = when (AppUser.BUILD_MODE) {
                AppUser.DEVELOPMENT -> URL_BASE_DEVELOPMENT
                AppUser.PRODUCTION -> URL_BASE_PRODUCTION
                else -> URL_BASE_PRODUCTION
            }
            Timber.d("URL_BASE:$url")
            return url
        }

    //====================================================================================
    val specializedDoctorBaseurl: String
        get() {
            var url = when (AppUser.BUILD_MODE) {
                AppUser.DEVELOPMENT -> URL_SPECIALIZED_DOCTOR_DEVELOPMENT
                AppUser.PRODUCTION -> URL_SPECIALIZED_DOCTOR_PRODUCTION
                else -> URL_SPECIALIZED_DOCTOR_PRODUCTION
            }
            Timber.d("SpecializedDoctorBaseurl:$url")
            return url
        }


    //======================================================================
    const val BASE_DISCOUNT_SEARCH_URL = "https://search.medico.bio/api/v1/"

    private const val URL_BASE_DEVELOPMENT = "https://api.farmer.arthik.io/"
    private const val URL_BASE_PRODUCTION = "https://api.farmer.arthik.io/"

    private const val URL_SPECIALIZED_DOCTOR_PRODUCTION = "https://meet.medico.bio/"
    private const val URL_SPECIALIZED_DOCTOR_DEVELOPMENT = "https://meet.medico.bio/"
    //  String URL_SPECIALIZED_DOCTOR_DEVELOPMENT = "https://beta.meet.medico.bio/";

    //=======================================================================
    const val BASE_LOG_URL = "http://log.medico.bio/api/v1/log"
    const val BASE_LOG_WRITE_TOKEN = "Bearer 1pake4mh5ln64h5t26kpvm3iri"
    //=======================================================================

    const val URL_PATIENT_CREATE_PROFILE = "/patient/passwordless-singup"
    const val URL_PATIENT_LOGIN = "patient/passwordless-login"
    const val URL_PATIENT_PASSWORD_LESS_LOGOUT = "/patient/passwordless-logout"
    const val URL_PATIENT_PROFILE = "patient/profile/"
    const val URL_PATIENT_OTP_VERIFICATION = "otp/otp-verification"
    const val URL_PATIENT_OTP_SEND = "otp/send-otp" //for resend

    const val URL_PATIENT_TOKEN = "patient/token"


    const val URL_PATIENT_DETAILS = "patient/"
    const val URL_FIREBASE_TOKEN_UPDATE = "firebase"


    const val URL_TRUSTED_DEVICE = "patient/trusted-device"
    const val URL_REMOVE_TRUSTED_DEVICE = "patient/remove-trusted-device"


    const val URL_MEDICATION_SURGERY_INSERT = "medicationAndSurgery"
    const val URL_PATIENT_PROFILE_PICTURE = "uploadImageApi"
    const val URL_PATIENT_LAB_REPORT_UPLOAD = "labReportUploadApi"
    const val URL_MEDICATION_SURGERY_LIST = "medicationAndSurgery/patient/"
    const val URL_LAB_REPORT_FILE_URL_UPLOAD = "labReport"
    const val URL_LAB_REPORT_DELETE = "labReport/"
    const val URL_PRESCRIPTION_DELETE = "previewPrescription/"
    const val URL_LAB_REPORT_LIST = "labReport/patient/"
    const val URL_PREVIOUS_PRESCRIPTION_UPLOAD = "previewPrescription"
    const val URL_PREVIOUS_PRESCRIPTION_LIST = "previewPrescription/patient/"
    const val URL_LAB_TEST_LIST = "labTestList"

    const val URL_CALL_HISTORY = "/callHistory/patient/"

    const val URL_STATUS_UPDATE = "doctor/statusUpdate"
    const val URL_CALL_SINGLE_DOCTOR = "/doctor/callDoctor"
    const val URL_PATIENT_FOLLOW_UP = "patient/followUp"
    const val URL_PRESCRIPTION_DETAILS = "/iframe-prescription/"

    //========================================================================
    const val URL_PACKAGE_LIST = "packages/list/"
    const val URL_PACKAGE = "packages"
    const val URL_SUB_INFO = "/subscribetion/sub-info-medico"

    //========================================================================
    const val URL_MEDICINE_PROVIDER = "onlineDeliveryProvider/category/medicine"
    const val URL_LAB_REPORT_PROVIDER = "onlineDeliveryProvider/category/lab_report"
    const val URL_ORDER_CONFIRM = "drug/place-order"
    const val URL_ORDER_HISTORY = "drug/order-history/"
    const val URL_DRUG_order_use_prescription = "drug/order-use-prescription"
    const val URL_DRUG = "drug"
    const val URL_DRUG_SEARCH = "$URL_DRUG/{$KEY_KEYWORD}"

    //========================================================================================
    const val URL_MESSAGES = "messages/"
    const val URL_HELPLINE = "helpline/"

    const val URL_META_INFO = "metaInfo" //meta_info


    //========================================================================================
    const val URL_SPECIALIST_DOCTOR_STATUS = "specialistDoctor/status"
    const val URL_SPECIALIST_DOCTOR_SCHEDULE_LIST = "doctorSchedule/{$user_id}"
    const val URL_SPECIALIST_DOCTOR_BOOKED_SCHEDULE =
        "doctorSchedule/getBookedSchedule/{$user_id}"
    const val URL_SPECIALIST_DOCTOR_BOOKING = "doctorBooking"
    const val URL_SPECIALIST_DOCTOR_QUEUE_LIST =
        "specialistDoctor/getQueueList/{doctorId}/{$patientId}/{$scheduleId}"
    const val URL_SPECIALIST_DOCTOR_QUEUE_CALLING_STATUS_UPDATE =
        "doctorAssistant/callingStatusUpdate/{$patientId}/{$doctorId}/{$scheduleId}/{$queueStatus}"
    const val URL_SPECIALIST_DOCTOR_BOOKING_CANCEL =
        "doctorBooking/cancelSchedule/{$scheduleId}/{$patientId}"
    const val URL_SPECIALIST_DOCTOR_QUEUE_STATUS_BY_PATIENT =
        "doctorAssistant/getQueueStatusBypatient/{$patientId}/{$scheduleId}"

    //=========================================================
    const val HEADER_CONTENT_TYPE_APPLICATION_JSON = "Content-Type: application/json"
    const val HEADER_AUTHORIZATION = "Authorization"
    const val HEADER_USER_INFO = "UserInfo"


    const val API_KEY_IMAGE = "file"
    const val TEXT_PLAIN = "text/plain"
    //val IMAGE_JPG = "image/jpg".toMediaType()

    // val MEDIA_TYPE_TEXT_PLAIN: MediaType = TEXT_PLAIN.toMediaType()
    // final val MEDIA_TYPE_IMAGE_JPG: MediaType = IMAGE_JPG
    const val ACCEPT = "Accept: application/json"
    const val CONTENT_TYPE = "Content-Type: application/json"

    //    MediaType MEDIA_TYPE_TEXT_PLAIN = MediaType.parse(TEXT_PLAIN);
    //===============================================
    const val UNAUTHORIZED_SERVER_CODE = 429 //token expire
    const val ANOTHER_DEVICES_LOGIN_CODE = 438
    const val USER_BLOCK_CODE = 418
    const val USER_DOCTOR_BOOKED_LIMIT_CODE = 426
    const val SERVER_UNEXPECTED_ERROR_CODE = 422 //server issue
    const val VALIDATION_ERROR_CODE = 401
    const val SERVER_ERROR_CODE = 502
    const val SERVER_ERROR_CODE2 = 500 //===============================================

}