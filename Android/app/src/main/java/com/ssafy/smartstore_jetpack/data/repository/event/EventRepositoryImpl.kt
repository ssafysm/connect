package com.ssafy.smartstore_jetpack.data.repository.event

import com.ssafy.smartstore_jetpack.data.mapper.EventsMapper
import com.ssafy.smartstore_jetpack.domain.model.Event
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.EventRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource
) : EventRepository {

    override suspend fun getEvents(): Result<List<Event>> =
        try {
            val response = withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                eventRemoteDataSource.getEvents()
            }

            val body = response.body()
            if (response.isSuccessful && (body != null)) {
                Result.success(EventsMapper(body))
            } else {
                Timber.d("예외는 발생 안 했는데 에러가 있음")
                Result.error(response.errorBody().toString(), null)
            }
        } catch (e: Exception) {
            Timber.d("예외 발생")
            Result.fail()
        }
}