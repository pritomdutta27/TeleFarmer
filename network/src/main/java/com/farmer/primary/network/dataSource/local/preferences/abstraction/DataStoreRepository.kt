package dynamic.app.survey.data.dataSource.local.preferences.abstraction

enum class LoggedInMode(val type: Int) {
    LOGGED_IN_MODE_LOGGED_OUT(0),
    LOGGED_IN_MODE_SERVER(1)
}

interface DataStoreRepository {

    suspend fun <T> putModel(key: String, value: T)
    suspend fun <T> getModel(key: String, classModel:Class<T>): T?
    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun getString(key: String): String?
    suspend fun getUserType(): String?
    suspend fun getInt(key: String): Int?
    suspend fun getUserID(): Int?

    suspend fun userLoginMode()
    suspend fun userLoginOut()
}