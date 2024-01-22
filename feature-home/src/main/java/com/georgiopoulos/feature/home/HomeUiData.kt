package com.georgiopoulos.feature.home

import androidx.compose.runtime.Immutable
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.model.error.ErrorModel

@Immutable
data class HomeUiData(
    val isLoading: Boolean = true,
    val sportEventsList: List<SportEventsDomainModel> = emptyList(),
    val error: ErrorModel? = null,
)