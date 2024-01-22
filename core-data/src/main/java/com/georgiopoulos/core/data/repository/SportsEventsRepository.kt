package com.georgiopoulos.core.data.repository

import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel

interface SportsEventsRepository {

    suspend fun getSportEventList(): Result<List<SportEventsDataModel>>

    suspend fun filterFavoriteEvents(sportId: String, events: List<EventDataModel>): Result<List<EventDataModel>>

    suspend fun getEventsForSportId(sportId: String): Result<List<EventDataModel>>

    suspend fun addRemoveEventToFavorites(event: EventDataModel): Result<Unit>
}