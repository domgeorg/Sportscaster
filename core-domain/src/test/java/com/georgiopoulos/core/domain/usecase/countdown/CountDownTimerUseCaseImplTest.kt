package com.georgiopoulos.core.domain.usecase.countdown

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.core.domain.utils.formater.TimeFormatter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CountDownTimerUseCaseImplTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val timeFormatter: TimeFormatter = mockk()
    private lateinit var useCase: CountDownTimerUseCaseImpl

    private val event1 = EventDomainModel(
        id = "eventId1",
        sportId = "sportId1",
        firstCompetitor = "Team A",
        secondCompetitor = "Team B",
        eventStartTime = System.currentTimeMillis() + 5000L, // 5 seconds in the future
        isFavorite = true,
        eventDate = "23/12/23 22:54",
    )

    private val event2 = EventDomainModel(
        id = "eventId2",
        sportId = "sportId2",
        firstCompetitor = "Team X",
        secondCompetitor = "Team Y",
        eventStartTime = System.currentTimeMillis() - 5000L, // 5 seconds in the past
        isFavorite = false,
        eventDate = "23/12/23 22:54",
    )

    private val sportEventsList = listOf(
        SportEventsDomainModel("sportId1", "Sport1", listOf(event1)),
        SportEventsDomainModel("sportId2", "Sport2", listOf(event2))
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        useCase = CountDownTimerUseCaseImpl(timeFormatter)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given startTimer is called with valid sport events list, When timer updates, Then sport events list is updated`() =
        runTest {
            // Given
            every { timeFormatter.formatTime(any()) } returns "00:05" // 5 seconds

            // When
            useCase.starTimer(sportEventsList)

            // Then
            val updatedList = useCase.sportEventsList.first()
            assertEquals("00:05", updatedList[0].activeEvents[0].countDown)
        }

    @Test
    fun `Given startTimer is called with empty sport events list, When timer updates, Then sport events list remains empty`() =
        runTest {
            // Given
            every { timeFormatter.formatTime(any()) } returns "00:05" // 5 seconds

            // When
            useCase.starTimer(emptyList())

            // Then
            val updatedList = useCase.sportEventsList.first()
            assertEquals(emptyList<SportEventsDomainModel>(), updatedList)
        }

    @Test
    fun `Given startTimer is called with past event, When timer updates, Then countDown is empty`() = runTest {
        // Given
        every { timeFormatter.formatTime(any()) } returns ""

        // When
        useCase.starTimer(listOf(SportEventsDomainModel("sportId", "Sport", listOf(event2))))

        // Then
        val updatedList = useCase.sportEventsList.first()
        assertEquals("", updatedList[0].activeEvents[0].countDown)
    }
}