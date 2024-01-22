package com.georgiopoulos.core.data.model

import com.georgiopoulos.core.database.entity.EventEntity
import com.georgiopoulos.core.network.response.EventResponse
import com.georgiopoulos.core.network.response.SportEventsResponse
import javax.inject.Inject

internal class SportEventsMapperImpl @Inject constructor() : SportEventsMapper {

    override fun map(sportEvents: SportEventsResponse, favoriteEventIdsSet: Set<String>): SportEventsDataModel =
        SportEventsDataModel(
            sportId = sportEvents.sportId,
            sportName = sportEvents.sportName,
            activeEvents = sportEvents.activeEventResponses.map { event ->
                transformEvent(event, favoriteEventIdsSet)
            }
        )

    override fun map(eventEntity: EventEntity): EventDataModel =
        EventDataModel(
            id = eventEntity.id,
            sportId = eventEntity.sportId,
            eventName = eventEntity.eventName,
            eventStartTime = eventEntity.eventStartTime,
            isFavorite = eventEntity.isFavorite,
        )

    override fun map(event: EventDataModel): EventEntity =
        EventEntity(
            id = event.id,
            sportId = event.sportId,
            eventName = event.eventName,
            eventStartTime = event.eventStartTime,
            isFavorite = event.isFavorite,
        )

    private fun transformEvent(event: EventResponse, favoriteEventIdsSet: Set<String>): EventDataModel {
        return EventDataModel(
            id = event.eventId,
            sportId = event.sportId,
            eventName = event.eventName,
            eventStartTime = event.eventStartTime,
            // Check if the event is a favorite using O(1) Set lookup
            isFavorite = favoriteEventIdsSet.contains(event.eventId),
        )
    }
}
