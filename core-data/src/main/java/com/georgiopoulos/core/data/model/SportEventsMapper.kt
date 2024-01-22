package com.georgiopoulos.core.data.model

import com.georgiopoulos.core.database.entity.EventEntity
import com.georgiopoulos.core.network.response.SportEventsResponse

interface SportEventsMapper {

    fun map(sportEvents: SportEventsResponse, favoriteEventIdsSet: Set<String>): SportEventsDataModel

    fun map(eventEntity: EventEntity): EventDataModel

    fun map(event: EventDataModel): EventEntity
}
