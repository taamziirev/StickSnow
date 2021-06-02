package com.project.data.repositories

import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.entities.Event
import com.project.data.entities.EventResult
import kotlinx.coroutines.flow.Flow

interface EventRepository{
    suspend fun getRemoteEvents(): Result<List<EventResult>>

    suspend fun getEvents(): Flow<List<EventResult>>

    suspend fun event(event: Event)

    suspend fun unEvent(event: Event)
}