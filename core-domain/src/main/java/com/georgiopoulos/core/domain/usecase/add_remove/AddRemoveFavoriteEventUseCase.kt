package com.georgiopoulos.core.domain.usecase.add_remove

import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.EventDomainModel

interface AddRemoveFavoriteEventUseCase {

    suspend fun addRemoveEventToFavorites(event: EventDomainModel): Outcome<Unit>
}
