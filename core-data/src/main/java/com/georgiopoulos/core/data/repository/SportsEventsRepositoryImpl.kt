package com.georgiopoulos.core.data.repository

import com.georgiopoulos.core.data.AppDispatchers
import com.georgiopoulos.core.data.Dispatcher
import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel
import com.georgiopoulos.core.data.model.SportEventsMapper
import com.georgiopoulos.core.database.dao.SportEventDao
import com.georgiopoulos.core.network.SportsEventsApi
import com.georgiopoulos.core.network.response.SportEventsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class SportsEventsRepositoryImpl @Inject constructor(
    private val api: SportsEventsApi,
    private val dao: SportEventDao,
    private val transformer: SportEventsMapper,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
) : SportsEventsRepository {

    override suspend fun getSportEventList(): Result<List<SportEventsDataModel>> =
        withContext(ioDispatcher) {
            try {
                // Launch two async operations concurrently
                val sportEventsDeferred = async { api.getSportEventList() }
                val favoriteEventsDeferred = async { dao.getFavoriteEventIds().first() }

                val sportEventsResponse = sportEventsDeferred.await()
                val favoriteEvents = favoriteEventsDeferred.await()

                // Convert list of favorite event IDs to a Set for efficient lookup
                val favoriteEventIdsSet = favoriteEvents.toSet()
                val transformedSportEvents = sportEventsResponse.map { sportEvents ->
                    transformer.map(sportEvents, favoriteEventIdsSet)
                }

                deleteEventsNotInApiResponse(sportEventsResponse)
                Result.success(transformedSportEvents)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun filterFavoriteEvents(
        sportId: String,
        events: List<EventDataModel>,
    ): Result<List<EventDataModel>> =
        withContext(ioDispatcher) {
            try {
                val transformedFavoriteEventsDeferred = async {
                    dao.getFavoriteEventsForSport(sportId).first()
                }

                launch {
                    // Insert only non-favorite events into the database
                    val notFavoriteEventEntities =
                        events.filter { event -> !event.isFavorite }.map { notFavoriteEvent ->
                            transformer.map(notFavoriteEvent)
                        }

                    dao.insertEvents(notFavoriteEventEntities)
                }

                val transformedFavoriteEvents = transformedFavoriteEventsDeferred.await()
                Result.success(
                    transformedFavoriteEvents.map { entity ->
                        transformer.map(entity)
                    }
                )
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun getEventsForSportId(sportId: String): Result<List<EventDataModel>> =
        withContext(ioDispatcher) {
            try {
                val events = dao.getEventsForSport(sportId).first().map { entity ->
                    transformer.map(entity)
                }
                Result.success(events)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    override suspend fun addRemoveEventToFavorites(event: EventDataModel): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                dao.insertEvent(
                    entity = transformer.map(event)
                )
                Result.success(Unit)
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }

    // Private function to delete events not present in the API response
    private suspend fun deleteEventsNotInApiResponse(sportEventsResponse: List<SportEventsResponse>) =
        withContext(ioDispatcher) {
            val eventIdsInApiResponse = sportEventsResponse.flatMap { sportEvent ->
                sportEvent.activeEventResponses.map { response -> response.eventId }
            }
            dao.deleteEventsNotInList(eventIdsInApiResponse)
        }
}
