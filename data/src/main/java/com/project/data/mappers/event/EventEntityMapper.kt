package com.project.data.mappers.event

import com.project.data.entities.Event
import com.project.data.api.event.EventResult
import com.project.data.entities.event.EventEntity

class EventEntityMapper {
    fun toEventEntity(event: Event): EventEntity {
        return EventEntity(
            count = event.count,
            next = event.next,
            previous = event.previous,
            results = event.eventResults?.map{ x -> EventEntity.EventResult(x?.id, x?.description, x?.title) }
        )
    }

    fun toEventResult(eventResult: EventResult): com.project.data.entities.EventResult {
        return com.project.data.entities.EventResult(
            eventResult.id,
            eventResult.description,
            eventResult.title
        )


    }
}