package com.project.data.mappers.event

import com.project.data.api.event.EventApiResponse
import com.project.data.entities.EventResult


class EventApiResponseMapper {
    fun toEventResultList(response: EventApiResponse): List<EventResult> {
        var a = response.items
        lateinit var eventResult: MutableList<EventResult>
        a.map{
            eventResult.addAll(it.eventResults)
        }
        return eventResult
    }
}