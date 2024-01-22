package com.georgiopoulos.core.domain.usecase.add_remove

import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsMapper
import com.georgiopoulos.core.domain.model.error.ErrorMapper
import javax.inject.Inject

internal class AddRemoveFavoriteEventUseCaseImpl @Inject constructor(
    private val repository: SportsEventsRepository,
    private val errorMapper: ErrorMapper,
    private val sportsEventsMapper: SportEventsMapper,
) : AddRemoveFavoriteEventUseCase {

    override suspend fun addRemoveEventToFavorites(event: EventDomainModel): Outcome<Unit> =
        try {
            repository.addRemoveEventToFavorites(sportsEventsMapper.mapEvent(event)).fold(
                onSuccess = {
                    Outcome.Success(Unit)
                },
                onFailure = { exception ->
                    Outcome.Error(errorMapper.mapError(exception))
                },
            )
        } catch (exception: Exception) {
            Outcome.Error(errorMapper.mapError(exception))
        }
}
