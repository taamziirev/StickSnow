package com.project.data.repositories.event

import com.project.data.api.event.EventApi
import com.project.data.common.Result
import com.project.data.entities.Category
import com.project.data.entities.Event
import com.project.data.entities.EventResult
import com.project.data.mappers.event.EventApiResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventRemoteDataSourceImpl(
    private val service: EventApi,
    private val mapper: EventApiResponseMapper
) : EventRemoteDataSource {
    override suspend fun getRemoteEvents(): Result<List<EventResult>>  =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getEvents()
                if (response.isSuccessful) {
                    return@withContext Result.Success(mapper.toEventResultList(response.body()!!))
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}