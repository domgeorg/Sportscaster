package com.georgiopoulos.core.domain.usecase.filter

import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsMapper
import com.georgiopoulos.core.domain.model.error.ErrorMapper
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.model.error.NetworkErrorModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterSportEventsUseCaseImplTest {

    private val repository: SportsEventsRepository = mockk()
    private val errorMapper: ErrorMapper = mockk(relaxed = true)
    private val sportsEventsMapper: SportEventsMapper = mockk()
    private val useCase = FilterSportEventsUseCaseImpl(repository, errorMapper, sportsEventsMapper)

    private val sportId = "sportId"
    private val eventDomainModel = EventDomainModel(
        id = "eventId",
        sportId = "sportId",
        firstCompetitor = "Team A",
        secondCompetitor = "Team B",
        eventStartTime = System.currentTimeMillis(),
        isFavorite = true,
        eventDate = "23/12/23 22:54",
    )

    @Test
    fun `Given successful filterFavoriteEvents call, When use case is invoked, Then return Outcome Success`() =
        runTest {
            // Given
            val eventsDataModel = mockk<EventDataModel>()
            val eventDomainModel = mockk<EventDomainModel>()
            val eventDataList = listOf(eventsDataModel)
            coEvery { repository.filterFavoriteEvents(sportId, any()) } returns Result.success(eventDataList)
            every { sportsEventsMapper.mapEvent(any() as EventDomainModel) } returns eventsDataModel
            every { sportsEventsMapper.mapEvent(any() as EventDataModel) } returns eventDomainModel

            // When
            val result = useCase.getFavoriteEvents(sportId, listOf(eventDomainModel))

            // Then
            coVerify { repository.filterFavoriteEvents(sportId, any()) }
            assertTrue(result is Outcome.Success)
            assertEquals(listOf(eventDomainModel), (result as Outcome.Success).value)
        }

    @Test
    fun `Given repository returns failure for filterFavoriteEvents, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = NetworkErrorModel.Unknown
            coEvery { repository.filterFavoriteEvents(sportId, any()) } returns Result.failure(Exception())
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.getFavoriteEvents(sportId, listOf(eventDomainModel))

            // Then
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }

    @Test
    fun `Given an exception is thrown during filterFavoriteEvents, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = ErrorModel.UnknownErrorModel
            coEvery { repository.filterFavoriteEvents(sportId, any()) } throws Exception()
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.getFavoriteEvents(sportId, listOf(eventDomainModel))

            // Then
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }

    @Test
    fun `Given successful getEventsForSportId call, When use case is invoked, Then return Outcome Success`() =
        runTest {
            // Given
            val eventsDataModel = mockk<EventDataModel>()
            val eventDomainModel = mockk<EventDomainModel>()
            val eventDataList = listOf(eventsDataModel)
            coEvery { repository.getEventsForSportId(sportId) } returns Result.success(eventDataList)
            every { sportsEventsMapper.mapEvent(any() as EventDomainModel) } returns eventsDataModel
            every { sportsEventsMapper.mapEvent(any() as EventDataModel) } returns eventDomainModel

            // When
            val result = useCase.getAllEventsById(sportId)

            // Then
            coVerify { repository.getEventsForSportId(sportId) }
            assertTrue(result is Outcome.Success)
            assertEquals(listOf(eventDomainModel), (result as Outcome.Success).value)
        }

    @Test
    fun `Given repository returns failure for getEventsForSportId, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = NetworkErrorModel.Unknown
            coEvery { repository.getEventsForSportId(sportId) } returns Result.failure(Exception())
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.getAllEventsById(sportId)

            // Then
            coVerify { repository.getEventsForSportId(sportId) }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }

    @Test
    fun `Given an exception is thrown during getEventsForSportId, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = ErrorModel.UnknownErrorModel
            coEvery { repository.getEventsForSportId(sportId) } throws Exception()
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.getAllEventsById(sportId)

            // Then
            coVerify { repository.getEventsForSportId(sportId) }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }
}