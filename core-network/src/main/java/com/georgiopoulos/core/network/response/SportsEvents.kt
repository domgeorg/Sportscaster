package com.georgiopoulos.core.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportEventsResponse(
    @Json(name = "i") val sportId: String,
    @Json(name = "d") val sportName: String,
    @Json(name = "e") val activeEventResponses: List<EventResponse>,
)

@JsonClass(generateAdapter = true)
data class EventResponse(
    @Json(name = "i") val eventId: String,
    @Json(name = "si") val sportId: String,
    @Json(name = "d") val eventName: String,
    @Json(name = "tt") val eventStartTime: Long,
)