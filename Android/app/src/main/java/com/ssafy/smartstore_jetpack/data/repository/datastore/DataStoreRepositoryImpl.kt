package com.ssafy.smartstore_jetpack.data.repository.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.ssafy.smartstore_jetpack.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    override suspend fun setUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = userId
        }
    }

    override fun getUserId(): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[USER_ID] ?: ""
        }
    }

    override suspend fun setLoginCookie(cookies: HashSet<String>) {
        dataStore.edit { preferences ->
            preferences[COOKIE_INFO] = cookies
        }
    }

    override fun getLoginCookie(): Flow<Set<String>> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[COOKIE_INFO] ?: setOf()
        }
    }

    override suspend fun deleteLoginCookie() {
        dataStore.edit { preferences ->
            preferences.remove(COOKIE_INFO)
        }
    }

    override suspend fun setAppTheme(theme: String) {
        dataStore.edit { preferences ->
            preferences[THEME_NAME] = theme
        }
    }

    override fun getAppTheme(): Flow<String> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[THEME_NAME] ?: "기본"
        }
    }

    override suspend fun setNotices(notices: HashSet<String>) {
        dataStore.edit { preferences ->
            preferences[NOTICE_INFO] = notices
        }
    }

    override fun getNotices(): Flow<List<String>> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            val notices = preferences[NOTICE_INFO]
            when (notices.isNullOrEmpty()) {
                true -> listOf()

                else -> notices.toList()
            }
        }
    }

    override suspend fun setAlarmReceiveMode(flag: Boolean) {
        dataStore.edit { preferences ->
            preferences[ALARM_RECEIVE_MODE] = flag
        }
    }

    override fun getAlarmReceiveMode(): Flow<Boolean> {
        return dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map { preferences ->
            preferences[ALARM_RECEIVE_MODE] ?: false
        }
    }

    companion object {

        private val USER_ID = stringPreferencesKey("user_id")
        private val COOKIE_INFO = stringSetPreferencesKey("cookie_info")
        private val THEME_NAME = stringPreferencesKey("theme_name")
        private val NOTICE_INFO = stringSetPreferencesKey("notice_info")
        private val ALARM_RECEIVE_MODE = booleanPreferencesKey("alarm_receive_mode")
    }
}