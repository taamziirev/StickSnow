package com.project.data.repositories.event

import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.entities.Event
import com.project.data.entities.EventResult
import com.project.data.repositories.EventRepository
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val localDataSource: EventLocalDataSource,
    private val remoteDataSource: EventRemoteDataSource
) : EventRepository {

    override suspend fun getRemoteEvents(): Result<List<EventResult>> {
        return remoteDataSource.getRemoteEvents()
    }

    override suspend fun getEvents(): Flow<List<EventResult>> {
        return localDataSource.getEvents()
    }

    override suspend fun event(event: Event) {
        localDataSource.event(event)
    }

    override suspend fun unEvent(event: Event) {
        localDataSource.unEvent(event)
    }
}