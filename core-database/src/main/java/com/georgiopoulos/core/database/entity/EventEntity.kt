package com.georgiopoulos.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "events",
    indices = [
        Index("id", unique = true)
    ],
)
data class EventEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "sportId") val sportId: String,
    @ColumnInfo(name = "eventName") val eventName: String,
    @ColumnInfo(name = "eventStartTime") val eventStartTime: Long,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false,
)
