package com.georgiopoulos.core.domain.di

import com.georgiopoulos.core.domain.model.SportEventsMapper
import com.georgiopoulos.core.domain.model.SportEventsMapperImpl
import com.georgiopoulos.core.domain.model.error.ErrorMapper
import com.georgiopoulos.core.domain.model.error.ErrorMapperImpl
import com.georgiopoulos.core.domain.usecase.add_remove.AddRemoveFavoriteEventUseCase
import com.georgiopoulos.core.domain.usecase.add_remove.AddRemoveFavoriteEventUseCaseImpl
import com.georgiopoulos.core.domain.usecase.countdown.CountDownTimerUseCase
import com.georgiopoulos.core.domain.usecase.countdown.CountDownTimerUseCaseImpl
import com.georgiopoulos.core.domain.usecase.filter.FilterSportEventsUseCase
import com.georgiopoulos.core.domain.usecase.filter.FilterSportEventsUseCaseImpl
import com.georgiopoulos.core.domain.usecase.load.LoadSportsEventsUseCase
import com.georgiopoulos.core.domain.usecase.load.LoadSportsEventsUseCaseImpl
import com.georgiopoulos.core.domain.utils.formater.DateFormatter
import com.georgiopoulos.core.domain.utils.formater.DateFormatterImpl
import com.georgiopoulos.core.domain.utils.formater.TimeFormatter
import com.georgiopoulos.core.domain.utils.formater.TimeFormatterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainModule {

    @Binds
    fun bindsTimeFormatter(
        timeFormatterImpl: TimeFormatterImpl,
    ): TimeFormatter

    @Binds
    fun bindsErrorMapper(
        errorMapperImpl: ErrorMapperImpl,
    ): ErrorMapper

    @Binds
    fun bindsSportEventsMapper(
        sportEventsMapperImpl: SportEventsMapperImpl,
    ): SportEventsMapper

    @Binds
    fun bindsAddRemoveFavoriteEventUseCase(
        addRemoveFavoriteEventUseCaseImpl: AddRemoveFavoriteEventUseCaseImpl,
    ): AddRemoveFavoriteEventUseCase

    @Binds
    fun bindsCountDownTimerUseCase(
        countDownTimerUseCaseImpl: CountDownTimerUseCaseImpl,
    ): CountDownTimerUseCase

    @Binds
    fun bindsFilterSportEventsUseCase(
        filterSportEventsUseCaseImpl: FilterSportEventsUseCaseImpl,
    ): FilterSportEventsUseCase

    @Binds
    fun bindsLoadSportsEventsUseCase(
        loadSportsEventsUseCaseImpl: LoadSportsEventsUseCaseImpl,
    ): LoadSportsEventsUseCase

    @Binds
    fun bindsDateFormatter(
        dateFormatter: DateFormatterImpl,
    ): DateFormatter
}
