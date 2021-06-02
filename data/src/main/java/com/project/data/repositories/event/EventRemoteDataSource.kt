package com.project.data.repositories.event

import com.project.data.common.Result
import com.project.data.entities.Event
import com.project.data.entities.EventResult

interface EventRemoteDataSource {
    suspend fun getRemoteEvents(): Result<List<EventResult>>
}