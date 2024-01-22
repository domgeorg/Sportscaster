package com.georgiopoulos.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.Outcome.Error
import com.georgiopoulos.core.domain.Outcome.Success
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.usecase.add_remove.AddRemoveFavoriteEventUseCase
import com.georgiopoulos.core.domain.usecase.countdown.CountDownTimerUseCase
import com.georgiopoulos.core.domain.usecase.filter.FilterSportEventsUseCase
import com.georgiopoulos.core.domain.usecase.load.LoadSportsEventsUseCase
import com.georgiopoulos.feature.home.HomeEvent.Dispose
import com.georgiopoulos.feature.home.HomeEvent.FilterFavoriteEvents
import com.georgiopoulos.feature.home.HomeEvent.GetEventsBySportId
import com.georgiopoulos.feature.home.HomeEvent.UpdateFavoriteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val loadSportsEventsUseCase: LoadSportsEventsUseCase,
    private val filterSportEventsUseCase: FilterSportEventsUseCase,
    private val addRemoveFavoriteEventUseCase: AddRemoveFavoriteEventUseCase,
    private val countDownTimerUseCase: CountDownTimerUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiData())
    val uiState = _uiState.asStateFlow()

    fun triggerEvent(homeEvent: HomeEvent) {
        when (homeEvent) {

            is UpdateFavoriteEvent -> updateFavoriteEvent(
                event = homeEvent.sportEvent,
                sportIndex = homeEvent.sportIndex,
                eventIndex = homeEvent.sportEventIndex,
                addToFavorites = homeEvent.addToFavorites,
            )

            Dispose -> onDispose()

            is FilterFavoriteEvents -> filterFavoriteEvents(
                sportId = homeEvent.sportId,
                sportIndex = homeEvent.sportIndex,
                activeEvents = homeEvent.activeEvents,
            )

            is GetEventsBySportId -> getAllTheEventsBySportId(
                sportIndex = homeEvent.sportIndex,
                sportId = homeEvent.sportId,
            )
        }
    }

    init {
        observeCountDownTimer()
        load()
    }

    private fun load() {
        viewModelScope.launch {
            loadSportsEventsUseCase.loadSportEventList().also { outcome: Outcome<List<SportEventsDomainModel>> ->
                when (outcome) {
                    is Error -> _uiState.update { previousState ->
                        previousState.copy(
                            isLoading = false,
                            error = outcome.value,
                        )
                    }

                    is Success -> {
                        _uiState.update { previousState -> previousState.copy(isLoading = false) }
                        countDownTimerUseCase.starTimer(outcome.value)
                    }
                }
            }
        }
    }

    private fun observeCountDownTimer() {
        viewModelScope.launch {
            countDownTimerUseCase.sportEventsList.collect { sportEventsList ->
                _uiState.update { previousState -> previousState.copy(sportEventsList = sportEventsList) }
            }
        }
    }

    private fun updateFavoriteEvent(
        event: EventDomainModel,
        sportIndex: Int,
        eventIndex: Int,
        addToFavorites: Boolean,
    ) {
        viewModelScope.launch {
            when (val outcome =
                addRemoveFavoriteEventUseCase.addRemoveEventToFavorites(event.copy(isFavorite = addToFavorites))) {
                is Error -> _uiState.update { previousState -> previousState.copy(error = outcome.value) }
                is Success -> {
                    val updatedSportEventsList = _uiState.value.sportEventsList.toMutableList()
                    val sportEvent = updatedSportEventsList[sportIndex].copy(
                        activeEvents = updatedSportEventsList[sportIndex].activeEvents.toMutableList().apply {
                            set(eventIndex, event.copy(isFavorite = addToFavorites))
                        }
                    )
                    updatedSportEventsList[sportIndex] = sportEvent
                    countDownTimerUseCase.starTimer(updatedSportEventsList)
                }
            }
        }
    }

    private fun filterFavoriteEvents(
        sportId: String,
        sportIndex: Int,
        activeEvents: List<EventDomainModel>,
    ) {
        viewModelScope.launch {
            when (val outcome = filterSportEventsUseCase.getFavoriteEvents(sportId, activeEvents)) {
                is Error -> _uiState.update { previousState -> previousState.copy(error = outcome.value) }
                is Success -> updateSportEventsList(sportIndex, outcome.value)
            }
        }
    }

    private fun getAllTheEventsBySportId(
        sportIndex: Int,
        sportId: String,
    ) {
        viewModelScope.launch {
            when (val outcome = filterSportEventsUseCase.getAllEventsById(sportId)) {
                is Error -> _uiState.update { previousState -> previousState.copy(error = outcome.value) }
                is Success -> updateSportEventsList(sportIndex, outcome.value)
            }
        }
    }

    private fun updateSportEventsList(
        sportIndex: Int,
        newActiveEvents: List<EventDomainModel>,
    ) {
        viewModelScope.launch {
            val updatedSportEventsList = _uiState.value.sportEventsList.toMutableList()
            updatedSportEventsList[sportIndex] = updatedSportEventsList[sportIndex].copy(
                activeEvents = newActiveEvents
            )
            countDownTimerUseCase.starTimer(updatedSportEventsList)
        }
    }

    private fun onDispose() {
        countDownTimerUseCase.stopTimer()
    }
}
