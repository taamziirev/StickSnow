package com.project.data.api.event

import com.project.data.entities.EventResult
import com.squareup.moshi.Json

class EventApiResponse(val items: List<Item>)

data class Item(
    @field:Json(name = "count")
    var count: Int,
    @field:Json(name = "next")
    var next: String,
    @field:Json(name = "previous")
    var previous: String,
    @field:Json(name = "results")
    var eventResults: List<EventResult>
)
data class EventResult(
    @field:Json(name = "id")
    var id: Int,
    @field:Json(name = "description")
    var description: String,
    @field:Json(name = "title")
    var title: String
)
