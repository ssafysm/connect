package com.ssafy.smartstore_jetpack.domain.repository

import com.ssafy.smartstore_jetpack.domain.model.Event
import com.ssafy.smartstore_jetpack.domain.model.Result

interface EventRepository {

    suspend fun getEvents(): Result<List<Event>>
}