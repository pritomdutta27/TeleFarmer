package bio.medico.patient.model.apiResponse

data class ResponseChatList(
	val user: List<UserItem?>?
)

data class UserItem(
	val senderId: String? = null,
	val senderName: String? = null,
	val dateAndTime: String? = null,
	val conversationId: String? = null,
	val body: String? = null,
	val createAt: String? = null,
	val senderImage: String? = null,
	val status: String? = null
)

