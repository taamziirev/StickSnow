package com.project.data.db.event

import androidx.room.*
import com.project.data.api.event.EventResult
import com.project.data.entities.event.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(event: EventEntity)

    @Query("SELECT results from EventEntity")
    fun getAllEvents(): Flow<List<EventResult>>

    @Delete
    suspend fun deleteEvent(event: EventEntity)
}