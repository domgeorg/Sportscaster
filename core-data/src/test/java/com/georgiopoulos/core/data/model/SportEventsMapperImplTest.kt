package com.georgiopoulos.core.data.model

import com.georgiopoulos.core.database.entity.EventEntity
import com.georgiopoulos.core.network.response.EventResponse
import com.georgiopoulos.core.network.response.SportEventsResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class SportEventsMapperImplTest {

    private val mapper = SportEventsMapperImpl()

    @Test
    fun `Given SportEventsResponse and favoriteEventIdsSet, When map is called, Then return SportEventsDataModel`() {
        // Given
        val sportId = "1"
        val sportName = "Football"
        val eventId = "101"
        val eventName = "Match 1"
        val eventStartTime = System.currentTimeMillis()

        val response = SportEventsResponse(
            sportId = sportId,
            sportName = sportName,
            activeEventResponses = listOf(
                EventResponse(
                    eventId = eventId,
                    sportId = sportId,
                    eventName = eventName,
                    eventStartTime = eventStartTime
                )
            )
        )

        val favoriteEventIdsSet = setOf(eventId)

        // When
        val result = mapper.map(response, favoriteEventIdsSet)

        // Then
        assertEquals(sportId, result.sportId)
        assertEquals(sportName, result.sportName)
        assertEquals(1, result.activeEvents.size)
        with(result.activeEvents[0]) {
            assertEquals(eventId, id)
            assertEquals(sportId, sportId)
            assertEquals(eventName, eventName)
            assertEquals(eventStartTime, eventStartTime)
            assertEquals(true, isFavorite)
        }
    }

    @Test
    fun `Given EventEntity, When map is called, Then return EventDataModel`() {
        // Given
        val eventId = "101"
        val sportId = "1"
        val eventName = "Match 1"
        val eventStartTime = System.currentTimeMillis()

        val entity = EventEntity(
            id = eventId,
            sportId = sportId,
            eventName = eventName,
            eventStartTime = eventStartTime,
            isFavorite = true
        )

        // When
        val result = mapper.map(entity)

        // Then
        assertEquals(eventId, result.id)
        assertEquals(sportId, result.sportId)
        assertEquals(eventName, result.eventName)
        assertEquals(eventStartTime, result.eventStartTime)
        assertEquals(true, result.isFavorite)
    }

    @Test
    fun `Given EventDataModel, When map is called, Then return EventEntity`() {
        // Given
        val eventId = "101"
        val sportId = "1"
        val eventName = "Match 1"
        val eventStartTime = System.currentTimeMillis()

        val dataModel = EventDataModel(
            id = eventId,
            sportId = sportId,
            eventName = eventName,
            eventStartTime = eventStartTime,
            isFavorite = true
        )

        // When
        val result = mapper.map(dataModel)

        // Then
        assertEquals(eventId, result.id)
        assertEquals(sportId, result.sportId)
        assertEquals(eventName, result.eventName)
        assertEquals(eventStartTime, result.eventStartTime)
        assertEquals(true, result.isFavorite)
    }
}