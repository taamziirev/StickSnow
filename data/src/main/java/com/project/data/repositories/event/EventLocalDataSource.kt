package com.project.data.repositories.event

import com.project.data.entities.Event
import com.project.data.entities.EventResult
import kotlinx.coroutines.flow.Flow

interface EventLocalDataSource {
    suspend fun event(Event: Event)
    suspend fun unEvent(Event: Event)
    suspend fun getEvents(): Flow<List<EventResult>>
}