package com.georgiopoulos.core.domain.usecase.countdown

import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.utils.formater.TimeFormatter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class CountDownTimerUseCaseImpl @Inject constructor(
    private val timeFormatter: TimeFormatter,
) : CountDownTimerUseCase {

    private val _sportEventsList = MutableStateFlow<List<SportEventsDomainModel>>(emptyList())
    override val sportEventsList: StateFlow<List<SportEventsDomainModel>> = _sportEventsList

    private var job: Job? = null

    override suspend fun starTimer(sportEventsList: List<SportEventsDomainModel>) {
        _sportEventsList.value = sportEventsList
        job?.cancel() // Cancel any existing job to avoid overlapping timers

        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                updateCountDown()
                delay(1000)
            }
        }
    }

    override fun stopTimer() {
        job?.cancel()
    }

    private fun updateCountDown() {
        val currentTime = System.currentTimeMillis() / 1000L
        val updatedSportEventsList = _sportEventsList.value.map { sport ->
            sport.copy(activeEvents = sport.activeEvents.map { event ->
                val timeDifference = event.eventStartTime - currentTime
                val formattedTime = if (timeDifference > 0) {
                    timeFormatter.formatTime(timeDifference)
                } else {
                    ""
                }
                event.copy(countDown = formattedTime)
            })
        }

        if (_sportEventsList.value != updatedSportEventsList) {
            _sportEventsList.value = updatedSportEventsList
        }
    }
}