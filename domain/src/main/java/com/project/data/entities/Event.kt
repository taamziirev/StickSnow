package com.project.data.entities


data class Event(
    var count: Int?,
    var next: String?,
    var previous: String?,
    var eventResults: List<EventResult?>?
) {
}