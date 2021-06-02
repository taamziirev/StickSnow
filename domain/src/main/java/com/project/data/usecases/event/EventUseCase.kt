package com.project.data.usecases.event

import com.project.data.entities.Event
import com.project.data.repositories.EventRepository

class EventUseCase(private val eventRepository: EventRepository) {
    suspend operator fun invoke(event: Event) = eventRepository.event(event)
}