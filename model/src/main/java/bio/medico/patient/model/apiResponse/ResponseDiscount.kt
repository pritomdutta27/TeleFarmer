package bio.medico.patient.model.apiResponse

data class ResponseDiscount(
    val discount_list: List<Discount>
)

data class Discount(
    val id: String,
    val address: String,
    val area: String,
    val discount: String,
    val locationUrl: String,
    val logo: String,
    val name: String?,
    val type: String
)