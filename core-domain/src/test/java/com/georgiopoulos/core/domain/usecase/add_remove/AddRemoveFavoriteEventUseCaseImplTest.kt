package com.georgiopoulos.core.domain.usecase.add_remove

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
class AddRemoveFavoriteEventUseCaseImplTest {

    private val repository: SportsEventsRepository = mockk()
    private val errorMapper: ErrorMapper = mockk(relaxed = true)
    private val sportsEventsMapper: SportEventsMapper = mockk()
    private val useCase = AddRemoveFavoriteEventUseCaseImpl(repository, errorMapper, sportsEventsMapper)

    private val eventDomainModel = EventDomainModel(
        id = "eventId",
        sportId = "sportId",
        firstCompetitor = "Team A",
        secondCompetitor = "Team B",
        eventStartTime = System.currentTimeMillis(),
        isFavorite = true,
        eventDate = "23/12/23 22:54",
    )

    private val eventDataModel = EventDataModel(
        id = "101",
        sportId = "1",
        eventName = "Team A - Team B",
        eventStartTime = System.currentTimeMillis(),
        isFavorite = true
    )

    @Test
    fun `Given successful addRemoveEventToFavorites call, When use case is invoked, Then return Outcome Success`() =
        runTest {
            // Given
            every { sportsEventsMapper.mapEvent(eventDomainModel) } returns eventDataModel
            coEvery { repository.addRemoveEventToFavorites(any()) } returns Result.success(Unit)

            // When
            val result = useCase.addRemoveEventToFavorites(eventDomainModel)

            // Then
            coVerify { repository.addRemoveEventToFavorites(any()) }
            assertTrue(result is Outcome.Success)
            assertEquals(Unit, (result as Outcome.Success).value)
        }

    @Test
    fun `Given repository returns failure, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            every { sportsEventsMapper.mapEvent(eventDomainModel) } returns eventDataModel
            val expectedError = NetworkErrorModel.Unknown
            coEvery { repository.addRemoveEventToFavorites(any()) } returns Result.failure(Exception())
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.addRemoveEventToFavorites(eventDomainModel)

            // Then
            coVerify { repository.addRemoveEventToFavorites(any()) }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }

    @Test
    fun `Given an exception is thrown, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            every { sportsEventsMapper.mapEvent(eventDomainModel) } returns eventDataModel
            val expectedError = ErrorModel.UnknownErrorModel
            coEvery { repository.addRemoveEventToFavorites(any()) } throws Exception()
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.addRemoveEventToFavorites(eventDomainModel)

            // Then
            coVerify { repository.addRemoveEventToFavorites(any()) }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }
}