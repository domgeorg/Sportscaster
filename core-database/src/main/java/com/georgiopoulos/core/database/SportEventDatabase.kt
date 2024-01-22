package com.georgiopoulos.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.georgiopoulos.core.database.dao.SportEventDao
import com.georgiopoulos.core.database.entity.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
    exportSchema = true,
)
internal abstract class SportEventDatabase : RoomDatabase() {

    abstract fun eventDao(): SportEventDao
}