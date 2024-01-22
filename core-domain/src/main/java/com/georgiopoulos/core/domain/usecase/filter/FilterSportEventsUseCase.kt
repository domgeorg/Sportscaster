package com.georgiopoulos.core.domain.usecase.filter

import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.EventDomainModel

interface FilterSportEventsUseCase {

    suspend fun getFavoriteEvents(sportId: String, domainEvents: List<EventDomainModel>): Outcome<List<EventDomainModel>>

    suspend fun getAllEventsById(sportId: String): Outcome<List<EventDomainModel>>
}