package com.georgiopoulos.feature.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.georgiopoulos.core.domain.Outcome.Error
import com.georgiopoulos.core.domain.Outcome.Success
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.core.domain.usecase.add_remove.AddRemoveFavoriteEventUseCase
import com.georgiopoulos.core.domain.usecase.countdown.CountDownTimerUseCase
import com.georgiopoulos.core.domain.usecase.filter.FilterSportEventsUseCase
import com.georgiopoulos.core.domain.usecase.load.LoadSportsEventsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val loadSportsEventsUseCase: LoadSportsEventsUseCase = mockk(relaxed = true)
    private val filterSportEventsUseCase: FilterSportEventsUseCase = mockk(relaxed = true)
    private val addRemoveFavoriteEventUseCase: AddRemoveFavoriteEventUseCase = mockk(relaxed = true)
    private val countDownTimerUseCase: CountDownTimerUseCase = mockk(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    private val event1 = EventDomainModel(
        id = "eventId1",
        sportId = "sportId1",
        firstCompetitor = "Team A",
        secondCompetitor = "Team B",
        eventStartTime = System.currentTimeMillis() + 5000L,
        isFavorite = true,
        eventDate = "23/12/23 22:54"
    )

    private val event2 = EventDomainModel(
        id = "eventId2",
        sportId = "sportId2",
        firstCompetitor = "Team X",
        secondCompetitor = "Team Y",
        eventStartTime = System.currentTimeMillis() - 5000L,
        isFavorite = false,
        eventDate = "23/12/23 22:54"
    )

    private val sportEventsList = listOf(
        SportEventsDomainModel("sportId1", "Sport1", listOf(event1)),
        SportEventsDomainModel("sportId2", "Sport2", listOf(event2))
    )

    private val updatedSportEventsList = listOf(
        SportEventsDomainModel("sportId1", "Sport1", listOf(event1.copy(countDown = "00:04:55"))),
        SportEventsDomainModel("sportId2", "Sport2", listOf(event2.copy(countDown = "00:00:05")))
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        every { countDownTimerUseCase.sportEventsList } coAnswers { MutableStateFlow(updatedSportEventsList) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given loadSportsEventsUseCase returns Success, When viewModel is initialized, Then uiState should be updated`() =
        runTest {
            // Given
            coEvery { loadSportsEventsUseCase.loadSportEventList() } coAnswers { Success(sportEventsList) }

            // When
            createViewModel()

            // Then
            assertEquals(false, viewModel.uiState.value.isLoading)
            coVerify { countDownTimerUseCase.starTimer(sportEventsList) }
        }

    @Test
    fun `Given loadSportsEventsUseCase returns Error, When viewModel is initialized, Then uiState should contain error`() =
        runTest {
            // Given
            val error = ErrorModel.UnknownErrorModel
            coEvery { loadSportsEventsUseCase.loadSportEventList() } returns Error(error)

            // When
            createViewModel()

            // Then
            assertEquals(false, viewModel.uiState.value.isLoading)
            assertEquals(error, viewModel.uiState.value.error)
        }

    @Test
    fun `Given countDownTimer emits updates, When observing countDownTimer, Then uiState should be updated`() =
        runTest {
            // Given
            coEvery { loadSportsEventsUseCase.loadSportEventList() } coAnswers { Success(sportEventsList) }

            // When
            createViewModel()
            delay(1000)

            // Then
            assertEquals(updatedSportEventsList, viewModel.uiState.value.sportEventsList)
        }

    @Test
    fun `Given updateFavoriteEventUseCase returns Success, When updateFavoriteEvent is triggered, Then uiState should be updated`() =
        runTest {
            // Given
            givenSuccessLoading()
            val updatedEvent1 = event1.copy(isFavorite = false)
            val slot = slot<List<SportEventsDomainModel>>()

            val result = listOf(
                SportEventsDomainModel("sportId1", "Sport1", listOf(updatedEvent1)),
                SportEventsDomainModel("sportId2", "Sport2", listOf(event2.copy(countDown = "00:00:05")))
            )

            coEvery { addRemoveFavoriteEventUseCase.addRemoveEventToFavorites(updatedEvent1) } returns Success(Unit)
            coEvery { countDownTimerUseCase.sportEventsList } coAnswers { MutableStateFlow(updatedSportEventsList) }
            coEvery { countDownTimerUseCase.starTimer(capture(slot)) } coAnswers {
                MutableStateFlow(
                    updatedSportEventsList
                )
            }

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.UpdateFavoriteEvent(
                    updatedEvent1,
                    sportIndex = 0,
                    sportEventIndex = 0,
                    addToFavorites = false
                )
            )

            // Then
            coVerifySequence {
                countDownTimerUseCase.sportEventsList
                countDownTimerUseCase.starTimer(sportEventsList)
                countDownTimerUseCase.starTimer(any())
            }
            assertEquals(slot.captured, result)
        }

    @Test
    fun `Given updateFavoriteEventUseCase returns Error, When updateFavoriteEvent is triggered, Then uiState should contain error`() =
        runTest {
            // Given
            val error = ErrorModel.UnknownErrorModel
            val updatedEvent1 = event1.copy(isFavorite = false)
            coEvery { addRemoveFavoriteEventUseCase.addRemoveEventToFavorites(updatedEvent1) } returns Error(error)

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.UpdateFavoriteEvent(
                    updatedEvent1,
                    sportIndex = 0,
                    sportEventIndex = 0,
                    addToFavorites = false
                )
            )

            // Then
            assertEquals(error, viewModel.uiState.value.error)
        }

    @Test
    fun `Given filterSportEventsUseCase returns Success, When filterFavoriteEvents is triggered, Then uiState should be updated`() =
        runTest {
            // Given
            givenSuccessLoading()
            val event3 = EventDomainModel(
                id = "eventId3",
                sportId = "sportId3",
                firstCompetitor = "Team C",
                secondCompetitor = "Team D",
                eventStartTime = System.currentTimeMillis() + 5000L,
                isFavorite = true,
                eventDate = "23/12/23 22:54"
            )
            val favoriteEvents = listOf(event3)
            val slot = slot<List<SportEventsDomainModel>>()

            val result = listOf(
                SportEventsDomainModel("sportId1", "Sport1", listOf(event3)),
                SportEventsDomainModel("sportId2", "Sport2", listOf(event2.copy(countDown = "00:00:05")))
            )

            coEvery {
                filterSportEventsUseCase.getFavoriteEvents("sportId1", listOf(event3, event2))
            } returns Success(favoriteEvents)

            coEvery { countDownTimerUseCase.starTimer(capture(slot)) } coAnswers {
                MutableStateFlow(
                    updatedSportEventsList
                )
            }

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.FilterFavoriteEvents(
                    sportId = "sportId1",
                    sportIndex = 0,
                    activeEvents = listOf(event3, event2)
                )
            )

            // Then
            coVerifySequence {
                countDownTimerUseCase.sportEventsList
                countDownTimerUseCase.starTimer(sportEventsList)
                countDownTimerUseCase.starTimer(any())
            }
            assertEquals(slot.captured, result)
        }

    @Test
    fun `Given filterSportEventsUseCase returns Error, When filterFavoriteEvents is triggered, Then uiState should contain error`() =
        runTest {
            // Given
            val error = ErrorModel.UnknownErrorModel
            coEvery {
                filterSportEventsUseCase.getFavoriteEvents("sportId1", listOf(event1, event2))
            } returns Error(error)

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.FilterFavoriteEvents(
                    sportId = "sportId1",
                    sportIndex = 0,
                    activeEvents = listOf(event1, event2)
                )
            )

            // Then
            assertEquals(error, viewModel.uiState.value.error)
        }

    @Test
    fun `Given filterSportEventsUseCase returns Success, When getAllTheEventsBySportId is triggered, Then uiState should be updated`() =
        runTest {
            // Given
            givenSuccessLoading()
            val event3 = EventDomainModel(
                id = "eventId3",
                sportId = "sportId1",
                firstCompetitor = "Team C",
                secondCompetitor = "Team D",
                eventStartTime = System.currentTimeMillis() + 5000L,
                isFavorite = true,
                eventDate = "23/12/23 22:54"
            )
            val allEvents = listOf(event1, event3)
            val slot = slot<List<SportEventsDomainModel>>()

            val result = listOf(
                SportEventsDomainModel("sportId1", "Sport1", allEvents),
                SportEventsDomainModel("sportId2", "Sport2", listOf(event2.copy(countDown = "00:00:05")))
            )

            coEvery { filterSportEventsUseCase.getAllEventsById("sportId1") } returns Success(allEvents)
            coEvery { countDownTimerUseCase.starTimer(capture(slot)) } coAnswers {
                MutableStateFlow(
                    updatedSportEventsList
                )
            }

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.GetEventsBySportId(
                    sportIndex = 0,
                    sportId = "sportId1"
                )
            )

            // Then
            coVerifySequence {
                countDownTimerUseCase.sportEventsList
                countDownTimerUseCase.starTimer(sportEventsList)
                countDownTimerUseCase.starTimer(any())
            }
            assertEquals(slot.captured, result)
        }

    @Test
    fun `Given filterSportEventsUseCase returns Error, When getAllTheEventsBySportId is triggered, Then uiState should contain error`() =
        runTest {
            // Given
            val error = ErrorModel.UnknownErrorModel
            coEvery { filterSportEventsUseCase.getAllEventsById("sportId1") } returns Error(error)

            // When
            createViewModel()
            viewModel.triggerEvent(
                HomeEvent.GetEventsBySportId(
                    sportIndex = 0,
                    sportId = "sportId1"
                )
            )

            // Then
            assertEquals(error, viewModel.uiState.value.error)
        }

    @Test
    fun `When Dispose is triggered, Then countDownTimerUseCase stopTimer should be called`() =
        runTest {
            // When
            createViewModel()
            viewModel.triggerEvent(HomeEvent.Dispose)

            // Then
            coVerify { countDownTimerUseCase.stopTimer() }
        }

    private fun createViewModel() {
        viewModel = HomeViewModel(
            loadSportsEventsUseCase = loadSportsEventsUseCase,
            filterSportEventsUseCase = filterSportEventsUseCase,
            addRemoveFavoriteEventUseCase = addRemoveFavoriteEventUseCase,
            countDownTimerUseCase = countDownTimerUseCase,
        )
    }

    private fun givenSuccessLoading() {
        coEvery { loadSportsEventsUseCase.loadSportEventList() } coAnswers { Success(sportEventsList) }
    }
}