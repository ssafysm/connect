package com.ssafy.smartstore_jetpack.data.repository.user

import com.ssafy.smartstore_jetpack.data.mapper.UserInfoMapper
import com.ssafy.smartstore_jetpack.data.mapper.UserMapper
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.model.User
import com.ssafy.smartstore_jetpack.domain.model.UserInfo
import com.ssafy.smartstore_jetpack.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun postUser(user: User): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.postUser(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun getUserInfo(id: String): Result<UserInfo> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.getUserInfo(id)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Timber.d("Exception: ${response.body()}")
                Result.success(UserInfoMapper(body))
            } else {
                Timber.d("Exception: ${response.errorBody().toString()}")
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Timber.d("Exception: $e")
            Result.fail()
        }

    override suspend fun postUserInfo(user: User): Result<UserInfo> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.postUserInfo(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Timber.d("Exception: ${response.body()}")
                Result.success(UserInfoMapper(body))
            } else {
                Timber.d("Exception: ${response.errorBody().toString()}")
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Timber.d("Exception: $e")
            Result.fail()
        }

    override suspend fun getIsUsedId(id: String): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.getIsUsedId(id)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun postUserForLogin(user: User): Result<User> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.postUserForLogin(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(UserMapper(body))
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun putPassword(user: User): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.putPassword(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun putAlarmMode(user: User): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.putAlarmMode(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }

    override suspend fun putAppTheme(user: User): Result<Boolean> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                userRemoteDataSource.putAppTheme(user)
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(body)
            } else {
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Result.fail()
        }
}