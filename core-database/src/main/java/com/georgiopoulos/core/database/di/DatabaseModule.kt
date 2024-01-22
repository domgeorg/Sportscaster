package com.georgiopoulos.core.database.di

import android.content.Context
import androidx.room.Room
import com.georgiopoulos.core.database.SportEventDatabase
import com.georgiopoulos.core.database.dao.SportEventDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module providing dependencies related to the Room database.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    /**
     * Provides the [SportEventDatabase] instance.
     *
     * @param context The application context.
     * @return The instance of [SportEventDatabase].
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): SportEventDatabase {
        return Room.databaseBuilder(context, SportEventDatabase::class.java, "SportsEvents.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides the [SportEventDao] instance.
     *
     * @param database The [SportEventDatabase] instance.
     * @return The instance of [SportEventDao].
     */
    @Provides
    fun provideEventDao(database: SportEventDatabase): SportEventDao {
        return database.eventDao()
    }
}