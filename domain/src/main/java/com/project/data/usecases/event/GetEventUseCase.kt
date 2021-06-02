package com.project.data.usecases.event

import com.project.data.repositories.EventRepository

class GetEventUseCase(private val eventRepository: EventRepository) {
    suspend operator fun invoke() = eventRepository.getEvents()
}