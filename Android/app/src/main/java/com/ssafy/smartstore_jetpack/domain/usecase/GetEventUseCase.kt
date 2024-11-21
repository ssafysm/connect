package com.ssafy.smartstore_jetpack.domain.usecase

import com.ssafy.smartstore_jetpack.domain.model.Event
import com.ssafy.smartstore_jetpack.domain.model.Result
import com.ssafy.smartstore_jetpack.domain.repository.EventRepository
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend fun getEvents(): Result<List<Event>> = eventRepository.getEvents()
}