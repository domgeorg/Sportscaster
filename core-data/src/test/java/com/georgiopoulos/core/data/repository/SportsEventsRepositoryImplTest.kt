package com.georgiopoulos.core.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel
import com.georgiopoulos.core.data.model.SportEventsMapper
import com.georgiopoulos.core.database.dao.SportEventDao
import com.georgiopoulos.core.database.entity.EventEntity
import com.georgiopoulos.core.network.SportsEventsApi
import com.georgiopoulos.core.network.response.EventResponse
import com.georgiopoulos.core.network.response.SportEventsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SportsEventsRepositoryImplTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val api = mockk<SportsEventsApi>(relaxed = true)
    private val dao = mockk<SportEventDao>(relaxed = true)
    private val transformer = mockk<SportEventsMapper>(relaxed = true)

    private val repository = SportsEventsRepositoryImpl(
        api = api,
        dao = dao,
        transformer = transformer,
        ioDispatcher = Dispatchers.Unconfined,
    )

    @Test
    fun `Given valid API response and favorite event IDs, When getSportEventList is called, Then return transformed sport events`() =
        runTest {
            // Given
            coEvery { api.getSportEventList() } returns listOf(mockSportEventsResponse)
            coEvery { dao.getFavoriteEventIds() } returns flowOf(listOf("eventId1", "eventId2"))
            coEvery { transformer.map(any(), any()) } returns mockSportEventsDataModel

            // When
            val result = repository.getSportEventList()

            // Then
            coVerify { dao.deleteEventsNotInList(any()) }
            assert(result.isSuccess)
            assert(result.getOrNull() == listOf(mockSportEventsDataModel))
        }

    @Test(expected = Exception::class)
    fun `Given an exception during API call, When getSportEventList is called, Then propagates the exception`() =
        runTest {
            // Given
            coEvery { api.getSportEventList() } throws Exception()

            // When
            repository.getSportEventList()

            // Then
        }

    @Test
    fun `Given valid favorite events in the database, When filterFavoriteEvents is called, Then return transformed favorite events`() =
        runTest {
            // Given
            coEvery { dao.getFavoriteEventsForSport(any()) } returns flowOf(listOf(mockEventEntity))
            coEvery { transformer.map(any() as EventEntity) } returns mockEventDataModel

            // When
            val result = repository.filterFavoriteEvents("sportId", listOf(mockEventDataModel))

            // Then
            coVerify { dao.insertEvents(any()) }
            assert(result.isSuccess)
            assert(result.getOrNull() == listOf(mockEventDataModel))
        }

    @Test(expected = Exception::class)
    fun `Given an exception during database query, When filterFavoriteEvents is called, Then propagates the exception`() =
        runTest {
            // Given
            coEvery { dao.getFavoriteEventsForSport(any()) } throws Exception()

            // When
            repository.filterFavoriteEvents("sportId", listOf(mockEventDataModel))
        }

    @Test
    fun `Given valid events in the database, When getEventsForSportId is called, Then return transformed events for sportId`() =
        runTest {
            // Given
            coEvery { dao.getEventsForSport(any()) } returns flowOf(listOf(mockEventEntity))
            coEvery { transformer.map(any() as EventEntity) } returns mockEventDataModel

            // When
            val result = repository.getEventsForSportId("sportId")

            // Then
            assert(result.isSuccess)
            assert(result.getOrNull() == listOf(mockEventDataModel))
        }

    @Test
    fun `Given an exception during database query, When getEventsForSportId is called, Then return failure`() =
        runTest {
            // Given
            coEvery { dao.getEventsForSport(any()) } throws Exception()

            // When
            val result = repository.getEventsForSportId("sportId")

            // Then
            assert(result.isFailure)
        }

    @Test
    fun `Given a valid event, When addRemoveEventToFavorites is called, Then insert event into the database`() =
        runTest {
            // Given

            // When
            val result = repository.addRemoveEventToFavorites(mockEventDataModel)

            // Then
            coVerify { dao.insertEvent(any()) }
            assert(result.isSuccess)
        }

    @Test
    fun `Given an exception during database insert, When addRemoveEventToFavorites is called, Then return failure`() =
        runTest {
            // Given
            coEvery { dao.insertEvent(any()) } throws Exception()

            // When
            val result = repository.addRemoveEventToFavorites(mockEventDataModel)

            // Then
            assert(result.isFailure)
        }

    private val mockEventEntity = EventEntity("eventId", "sportId", "eventName", 1234567890L, true)
    private val mockEventResponse = EventResponse("eventId", "sportId", "eventName", 1234567890L)
    private val mockEventDataModel = EventDataModel("eventId", "sportId", "eventName", 1234567890L, false)
    private val mockSportEventsResponse = SportEventsResponse("sportId", "sportName", listOf(mockEventResponse))
    private val mockSportEventsDataModel = SportEventsDataModel("sportId", "sportName", listOf(mockEventDataModel))
}
