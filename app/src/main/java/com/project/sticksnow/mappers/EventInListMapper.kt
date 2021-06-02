package com.project.sticksnow.mappers

import com.project.data.entities.Event
import com.project.sticksnow.entities.EventResult

class EventInListMapper {
    fun fromEventToEventInList(
        events: Event,
        eventz: List<EventResult>
    ): List<EventResult> {
        val commonElements = events.eventResults?.filter { it?.id in eventz.map { check -> check.id } }
        val eventsInList = arrayListOf<EventResult>()
        for (event in events.eventResults!!) {
            if (commonElements != null) {
                if (event in commonElements) {
                    eventsInList.add(
                        EventResult(
                            event?.id,
                            event?.description,
                            event?.title
                        )
                    )
                } else {
                    eventsInList.add(
                        EventResult(
                            event?.id,
                            event?.description,
                            event?.title
                        )
                    )
                }
            }
        }
        return eventsInList.sortedBy { it.id }
    }

    fun fromEventInListToEvent(eventInList: List<EventResult?>?): Event {
        return Event(
            1,
            "a",
            "a",
            eventInList?.map{ event ->
                com.project.data.entities.EventResult(
                    event?.id,
                    event?.description,
                    event?.title
                )
            }
        )
    }
}