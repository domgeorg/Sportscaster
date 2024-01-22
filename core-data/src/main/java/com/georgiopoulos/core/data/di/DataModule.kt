package com.georgiopoulos.core.data.di

import com.georgiopoulos.core.data.model.SportEventsMapper
import com.georgiopoulos.core.data.model.SportEventsMapperImpl
import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.data.repository.SportsEventsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun bindsSportEventsMapper(
        sportEventsMapperImpl: SportEventsMapperImpl
    ): SportEventsMapper

    @Binds
    fun bindsSportsEventsRepository(
        sportsEventsRepositoryImpl: SportsEventsRepositoryImpl
    ): SportsEventsRepository
}
