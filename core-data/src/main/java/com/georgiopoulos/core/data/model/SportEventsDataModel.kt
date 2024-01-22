package com.georgiopoulos.core.data.model

data class SportEventsDataModel(
    val sportId: String,
    val sportName: String,
    val activeEvents: List<EventDataModel>,
)

data class EventDataModel(
    val id: String,
    val sportId: String,
    val eventName: String,
    val eventStartTime: Long,
    val isFavorite: Boolean = false,
)
