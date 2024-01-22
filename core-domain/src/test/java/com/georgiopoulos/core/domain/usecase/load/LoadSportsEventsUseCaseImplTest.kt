package com.georgiopoulos.core.domain.usecase.load


import com.georgiopoulos.core.data.model.SportEventsDataModel
import com.georgiopoulos.core.data.repository.SportsEventsRepository
import com.georgiopoulos.core.domain.Outcome
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
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
class LoadSportsEventsUseCaseImplTest {

    private val repository: SportsEventsRepository = mockk()
    private val errorMapper: ErrorMapper = mockk(relaxed = true)
    private val sportsEventsMapper: SportEventsMapper = mockk()
    private val useCase = LoadSportsEventsUseCaseImpl(repository, errorMapper, sportsEventsMapper)

    @Test
    fun `Given successful getSportEventList call with non-empty list, When use case is invoked, Then return Outcome Success`() =
        runTest {
            // Given
            val sportEventsDataModel = mockk<SportEventsDataModel>()
            val sportEventsDomainModel = mockk<SportEventsDomainModel>()
            val sportEventsList = listOf(sportEventsDataModel)
            coEvery { repository.getSportEventList() } returns Result.success(sportEventsList)
            every { sportsEventsMapper.map(any() as SportEventsDataModel) } returns sportEventsDomainModel

            // When
            val result = useCase.loadSportEventList()

            // Then
            coVerify { repository.getSportEventList() }
            assertTrue(result is Outcome.Success)
            assertEquals(listOf(sportEventsDomainModel), (result as Outcome.Success).value)
        }

    @Test
    fun `Given successful getSportEventList call with empty list, When use case is invoked, Then return Outcome Error with NoResultsErrorModel`() =
        runTest {
            // Given
            coEvery { repository.getSportEventList() } returns Result.success(emptyList())

            // When
            val result = useCase.loadSportEventList()

            // Then
            coVerify { repository.getSportEventList() }
            assertTrue(result is Outcome.Error)
            assertEquals(ErrorModel.NoResultsErrorModel, (result as Outcome.Error).value)
        }

    @Test
    fun `Given repository returns failure, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = NetworkErrorModel.Unknown
            coEvery { repository.getSportEventList() } returns Result.failure(Exception())
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.loadSportEventList()

            // Then
            coVerify { repository.getSportEventList() }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }

    @Test
    fun `Given an exception is thrown, When use case is invoked, Then return Outcome Error`() =
        runTest {
            // Given
            val expectedError = ErrorModel.UnknownErrorModel
            coEvery { repository.getSportEventList() } throws Exception()
            every { errorMapper.mapError(any()) } returns expectedError

            // When
            val result = useCase.loadSportEventList()

            // Then
            coVerify { repository.getSportEventList() }
            assertTrue(result is Outcome.Error)
            assertEquals(expectedError, (result as Outcome.Error).value)
        }
}