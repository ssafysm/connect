package com.ssafy.smartstore_jetpack.data.mapper

import com.ssafy.smartstore_jetpack.data.entity.EventEntity
import com.ssafy.smartstore_jetpack.domain.model.Event

object EventsMapper {

    operator fun invoke(eventEntities: List<EventEntity>): List<Event> {
        val newEvents = mutableListOf<Event>()

        eventEntities.forEach { eventEntity ->
            newEvents.add(
                Event(
                    id = eventEntity.id.toString(),
                    name = eventEntity.name ?: "",
                    image = eventEntity.image ?: "",
                    url = eventEntity.url ?: ""
                )
            )
        }

        return newEvents
    }
}