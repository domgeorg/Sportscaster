package com.georgiopoulos.feature.home

import com.georgiopoulos.core.domain.model.EventDomainModel

sealed interface HomeEvent {

    data class FilterFavoriteEvents(
        val sportId: String,
        val sportIndex: Int,
        val activeEvents: List<EventDomainModel>,
    ) : HomeEvent

    data class GetEventsBySportId(
        val sportIndex: Int,
        val sportId: String
    ) : HomeEvent

    data class UpdateFavoriteEvent(
        val sportEvent: EventDomainModel,
        val sportIndex: Int,
        val sportEventIndex: Int,
        val addToFavorites: Boolean
    ) : HomeEvent

    data object Dispose : HomeEvent
}
