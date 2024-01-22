package com.georgiopoulos.core.domain.usecase.filter

import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsMapper
import com.georgiopoulos.core.domain.model.error.ErrorMapper
import javax.inject.Inject

internal class FilterSportEventsUseCaseImpl @Inject constructor(
    private val repository: SportsEventsRepository,
    private val errorMapper: ErrorMapper,
    private val sportsEventsMapper: SportEventsMapper,
) : FilterSportEventsUseCase {

    override suspend fun getFavoriteEvents(
        sportId: String,
        domainEvents: List<EventDomainModel>,
    ): Outcome<List<EventDomainModel>> =
        try {
            repository.filterFavoriteEvents(
                sportId = sportId,
                events = domainEvents.map { eventDomainModel -> sportsEventsMapper.mapEvent(eventDomainModel) },
            ).fold(
                onSuccess = { eventDataList ->
                    val result = eventDataList.map { dataModel -> sportsEventsMapper.mapEvent(dataModel) }
                    Outcome.Success(result)
                },
                onFailure = { exception ->
                    Outcome.Error(errorMapper.mapError(exception))
                },
            )
        } catch (exception: Exception) {
            Outcome.Error(errorMapper.mapError(exception))
        }

    override suspend fun getAllEventsById(sportId: String): Outcome<List<EventDomainModel>> =
        try {
            repository.getEventsForSportId(sportId).fold(
                onSuccess = { eventDataList ->
                    val result = eventDataList.map { dataModel -> sportsEventsMapper.mapEvent(dataModel) }
                    Outcome.Success(result)
                },
                onFailure = { exception ->
                    Outcome.Error(errorMapper.mapError(exception))
                },
            )
        } catch (exception: Exception) {
            Outcome.Error(errorMapper.mapError(exception))
        }
}
