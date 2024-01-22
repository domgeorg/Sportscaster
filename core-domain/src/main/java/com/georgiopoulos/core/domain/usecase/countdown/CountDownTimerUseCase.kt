package com.georgiopoulos.core.domain.usecase.countdown

import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface CountDownTimerUseCase {

    val sportEventsList: StateFlow<List<SportEventsDomainModel>>

    suspend fun starTimer(sportEventsList: List<SportEventsDomainModel>)

    fun stopTimer()
}
