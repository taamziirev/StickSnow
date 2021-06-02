package com.project.data.entities.event

import androidx.room.*
import com.project.data.db.Converters

@Entity
class EventEntity(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var results: List<EventResult?>?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @Entity
    data class EventResult(
        @PrimaryKey
        var id: Int?,
        var description: String?,
        var title: String?
    )
}
