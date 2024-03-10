package dynamic.app.survey.data.dataSource.local.preferences.implementation

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.utils.AppConstants
import com.google.gson.GsonBuilder
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.DataStoreRepository
import dynamic.app.survey.data.dataSource.local.preferences.abstraction.LoggedInMode
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject
import javax.security.auth.login.LoginException


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = AppConstants.PREF_NAME)

class DataStoreRepositoryImpl @Inject constructor(
    private val context: Context
) : DataStoreRepository {

    override suspend fun <T> putModel(key: String, value: T) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = GsonBuilder().create().toJson(value)
        }
    }

    override suspend fun <T> getModel(key: String, classModel: Class<T>): T? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return try {
            GsonBuilder().create().fromJson(preferences[preferencesKey], classModel)
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        val preferencesKey = intPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        val preferencesKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun getUserType(): String {
        val preferencesKey = stringPreferencesKey(AppConstants.PREF_KEY_USER_TYPE)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey].toString()
    }

    override suspend fun getInt(key: String): Int? {
        val preferencesKey = intPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey] ?: 0
    }

    override suspend fun getUserID(): Int? {
        val preferencesKey = intPreferencesKey(AppConstants.PREF_KEY_USER_ID)
        val preferences = context.dataStore.data.first()
        return preferences[preferencesKey]
    }

    override suspend fun userLoginMode() {
        val preferencesKey = intPreferencesKey(AppConstants.PREF_KEY_USER_LOGGED_IN_MODE)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = LoggedInMode.LOGGED_IN_MODE_SERVER.type
        }
    }

    override suspend fun userLoginOut() {
        val preferencesKey = intPreferencesKey(AppConstants.PREF_KEY_USER_LOGGED_IN_MODE)
        val preferencesAccessKey = stringPreferencesKey(AppConstants.PREF_KEY_ACCESS_TOKEN)
        val preferencesPhoneNum = stringPreferencesKey(AppConstants.PREF_KEY_USER_PHONE_NUM)
        LocalData.setToken("")
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type
            preferences[preferencesAccessKey] = ""
            preferences[preferencesPhoneNum] = ""
        }
    }
}