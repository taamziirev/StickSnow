package com.project.data.usecases.event

import com.project.data.entities.Event
import com.project.data.repositories.EventRepository

class UnEventUseCase(private val eventRepository: EventRepository) {
    suspend operator fun invoke(event: Event) = eventRepository.unEvent(event)
}