package com.georgiopoulos.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.georgiopoulos.core.database.entity.EventEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SportEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(entity: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(entities: List<EventEntity>)

    @Query("DELETE FROM events WHERE id LIKE :eventId")
    suspend fun removeEventById(eventId: String)

    @Query("DELETE FROM events WHERE id NOT IN (:eventIds)")
    suspend fun deleteEventsNotInList(eventIds: List<String>)

    @Query("SELECT * FROM events WHERE sportId LIKE :sportId AND isFavorite = 1")
    fun getFavoriteEventsForSport(sportId: String): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE sportId LIKE :sportId")
    fun getEventsForSport(sportId: String): Flow<List<EventEntity>>

    @Query("SELECT id FROM events WHERE isFavorite = 1")
    fun getFavoriteEventIds(): Flow<List<String>>
}
