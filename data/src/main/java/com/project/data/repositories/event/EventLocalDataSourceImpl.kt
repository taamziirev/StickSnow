package com.project.data.repositories.event

import com.project.data.entities.Event
import com.project.data.mappers.event.EventEntityMapper
import com.project.data.db.event.EventDao
import com.project.data.entities.EventResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class EventLocalDataSourceImpl(
    private val EventDao: EventDao,
    private val dispatcher: CoroutineDispatcher,
    private val EventEntityMapper: EventEntityMapper
) : EventLocalDataSource {
    override suspend fun event(Event: Event) = withContext(dispatcher){
        EventDao.insertEvents(EventEntityMapper.toEventEntity(Event))
    }

    override suspend fun unEvent(Event: Event) = withContext(dispatcher){
        EventDao.deleteEvent(EventEntityMapper.toEventEntity(Event))
    }

    override suspend fun getEvents(): Flow<List<EventResult>> {
        val savedFlow = EventDao.getAllEvents()
        return savedFlow.map { list ->
            list.map { element ->
                EventEntityMapper.toEventResult(element)
            }
        }
    }
}