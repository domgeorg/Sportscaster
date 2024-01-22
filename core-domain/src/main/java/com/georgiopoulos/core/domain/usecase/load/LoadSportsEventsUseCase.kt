package com.georgiopoulos.core.domain.usecase.load

import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.SportEventsDomainModel

interface LoadSportsEventsUseCase {

    suspend fun loadSportEventList(): Outcome<List<SportEventsDomainModel>>
}