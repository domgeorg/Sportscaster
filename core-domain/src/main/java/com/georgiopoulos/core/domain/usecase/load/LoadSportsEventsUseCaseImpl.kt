package com.georgiopoulos.core.domain.usecase.load

import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.model.SportEventsMapper
import com.georgiopoulos.core.domain.model.error.ErrorMapper
import com.georgiopoulos.core.domain.model.error.ErrorModel.NoResultsErrorModel
import javax.inject.Inject

internal class LoadSportsEventsUseCaseImpl @Inject constructor(
    private val repository: SportsEventsRepository,
    private val errorMapper: ErrorMapper,
    private val sportsEventsMapper: SportEventsMapper,
) : LoadSportsEventsUseCase {

    override suspend fun loadSportEventList(): Outcome<List<SportEventsDomainModel>> =
        try {
            repository.getSportEventList().fold(
                onSuccess = { sportEventsList ->
                    if (sportEventsList.isEmpty()) {
                        Outcome.Error(NoResultsErrorModel)
                    } else {
                        val result = sportEventsList.map { dataModel -> sportsEventsMapper.map(dataModel) }
                        Outcome.Success(result)
                    }
                },
                onFailure = { exception ->
                    Outcome.Error(errorMapper.mapError(exception))
                },
            )
        } catch (exception: Exception) {
            Outcome.Error(errorMapper.mapError(exception))
        }

}
