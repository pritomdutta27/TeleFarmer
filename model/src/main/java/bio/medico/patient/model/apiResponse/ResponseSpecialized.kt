package bio.medico.patient.model.apiResponse


/**
Created by Samiran Kumar on 17,May,2023
 **/
data class ResponseSpecialized(
    val items: List<SpecializedDoctor>
)

data class SpecializedDoctor(
    val bookingStartTime: String,
    val isBooked: Boolean,  //if true Booked for // false
    val isBookingEnabled: Boolean, // isBook isBEna false booking start on // true book now
    val callingStatus: Boolean, //true join now callingStatus
    val callingURL: String,
    val createdAt: String,
    val degree: List<String>,
    val doctorId: String,
    val endDateTime: String,
    val id: Int,
    private val image: String?,
    val name: String,
    val phoneNumber: String,
    val scheduleDate: String,
    val specialty: List<String>,
    var startDateTime: String,
    val updatedAt: Any,
    val scheduleId: String,
    val jwt: String,
    val scheduleStatus: SpecializedDoctorUIType /*
    scheduleStatus : {
    JOIN_NOW
    BOOKING_WILL_START_ON
    BOOKED
    BOOK_NOW
    COMPLETE
    EXPIRED
} */
) {
    fun getImage(): String = if (this.image.isNullOrEmpty()) "" else this.image

}


/*
queueStatus : {
    CALLING : 'calling',
    WAITING : 'waiting',
    DROPPED : 'dropped',
    CS_QUERY : 'cs_query',
    SAVE_DRAFT : 'save_draft',
    COMPLETED : 'completed'
}

* */

//JOIN_NOW, BOOKING_WILL_START_ON, BOOKED_FOR, BOOK_NOW
enum class SpecializedDoctorUIType(val textEng: String, val textBng: String) {
    JOIN_NOW("Join Now", "এখনি যোগদিন"),
    BOOKING_WILL_START_ON("Booking will start on", "বুকিং শুরু হবে"),
    BOOKED("Booked For", "বুকিং এর জন্য"),
    BOOK_NOW("Get Appointment", "এ্যাপয়েন্টমেন্ট নিন"),
    COMPLETE("", ""),
    EXPIRED("", ""),
    CANCELLED("", ""),
    DROPPED("", ""),
}


//remove EXPIRED schedule
fun List<SpecializedDoctor>.getModifyData(): List<SpecializedDoctor> {
    return filter { item -> item.scheduleStatus.name != SpecializedDoctorUIType.EXPIRED.name }
}