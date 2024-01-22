package com.georgiopoulos.core.domain.model


import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel
import com.georgiopoulos.core.domain.utils.formater.DateFormatter
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SportEventsMapperImplTest {

    private lateinit var dateFormatter: DateFormatter
    private lateinit var mapper: SportEventsMapperImpl

    @Before
    fun setup() {
        dateFormatter = mockk(relaxed = true)
        every { dateFormatter.formatUnixTime(any(), any()) } answers { "formattedDate" }
        mapper = SportEventsMapperImpl(dateFormatter)
    }

    @Test
    fun `Given SportEventsDataModel, When map is called, Then return SportEventsDomainModel`() {
        // Given
        val dataModel = SportEventsDataModel(
            sportId = "1",
            sportName = "Football",
            activeEvents = listOf(
                EventDataModel(
                    id = "101",
                    sportId = "1",
                    eventName = "Team A - Team B",
                    eventStartTime = System.currentTimeMillis(),
                    isFavorite = true
                )
            )
        )

        // When
        val result = mapper.map(dataModel)

        // Then
        assertEquals("1", result.sportId)
        assertEquals("Football", result.sportName)
        assertEquals(1, result.activeEvents.size)
        assertEquals("101", result.activeEvents[0].id)
        assertEquals("1", result.activeEvents[0].sportId)
        assertEquals("Team A", result.activeEvents[0].firstCompetitor)
        assertEquals("Team B", result.activeEvents[0].secondCompetitor)
        assertEquals(true, result.activeEvents[0].isFavorite)
        assertEquals("formattedDate", result.activeEvents[0].eventDate)
    }

    @Test
    fun `Given SportEventsDomainModel, When map is called, Then return SportEventsDataModel`() {
        // Given
        val domainModel = SportEventsDomainModel(
            sportId = "1",
            sportName = "Football",
            activeEvents = listOf(
                EventDomainModel(
                    id = "101",
                    sportId = "1",
                    firstCompetitor = "Team A",
                    secondCompetitor = "Team B",
                    eventStartTime = System.currentTimeMillis(),
                    isFavorite = true,
                    eventDate = "23/12/23 22:54",
                )
            )
        )

        // When
        val result = mapper.map(domainModel)

        // Then
        assertEquals("1", result.sportId)
        assertEquals("Football", result.sportName)
        assertEquals(1, result.activeEvents.size)
        assertEquals("101", result.activeEvents[0].id)
        assertEquals("1", result.activeEvents[0].sportId)
        assertEquals("Team A - Team B", result.activeEvents[0].eventName)
        assertEquals(true, result.activeEvents[0].isFavorite)
    }

    @Test
    fun `Given EventDataModel, When mapEvent is called, Then return EventDomainModel`() {
        // Given
        val eventDataModel = EventDataModel(
            id = "101",
            sportId = "1",
            eventName = "Team A - Team B",
            eventStartTime = System.currentTimeMillis(),
            isFavorite = true
        )

        // When
        val result = mapper.mapEvent(eventDataModel)

        // Then
        assertEquals("101", result.id)
        assertEquals("1", result.sportId)
        assertEquals("Team A", result.firstCompetitor)
        assertEquals("Team B", result.secondCompetitor)
        assertEquals(true, result.isFavorite)
        assertEquals("formattedDate", result.eventDate)
    }

    @Test
    fun `Given EventDomainModel, When mapEvent is called, Then return EventDataModel`() {
        // Given
        val eventDomainModel = EventDomainModel(
            id = "101",
            sportId = "1",
            firstCompetitor = "Team A",
            secondCompetitor = "Team B",
            eventStartTime = System.currentTimeMillis(),
            isFavorite = true,
            eventDate = "23/12/23 22:54",
        )

        // When
        val result = mapper.mapEvent(eventDomainModel)

        // Then
        assertEquals("101", result.id)
        assertEquals("1", result.sportId)
        assertEquals("Team A - Team B", result.eventName)
        assertEquals(true, result.isFavorite)
    }
}