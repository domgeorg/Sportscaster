package com.georgiopoulos.core.domain.model

data class SportEventsDomainModel(
    val sportId: String,
    val sportName: String,
    val activeEvents: List<EventDomainModel> = emptyList(),
)

data class EventDomainModel(
    val id: String,
    val sportId: String,
    val firstCompetitor: String,
    val secondCompetitor: String,
    val eventStartTime: Long,
    val countDown: String = DEFAULT_COUNTDOWN,
    val eventDate: String,
    val isFavorite: Boolean = false,
) {
    companion object {
        const val DEFAULT_COUNTDOWN = "00:00:00"
    }
}
