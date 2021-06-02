package com.project.sticksnow.entities


data class EventInList(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var eventResults: List<EventResult?>?
)
data class EventResult(
    var id: Int?,
    var description: String?,
    var title: String?
)