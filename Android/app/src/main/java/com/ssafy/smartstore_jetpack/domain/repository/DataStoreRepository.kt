package com.ssafy.smartstore_jetpack.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    suspend fun setUserId(userId: String)

    fun getUserId(): Flow<String>

    suspend fun setLoginCookie(cookies: HashSet<String>)

    fun getLoginCookie(): Flow<Set<String>>

    suspend fun deleteLoginCookie()

    suspend fun setAppTheme(theme: String)

    fun getAppTheme(): Flow<String>

    suspend fun setNotices(notices: HashSet<String>)

    fun getNotices(): Flow<List<String>>
}