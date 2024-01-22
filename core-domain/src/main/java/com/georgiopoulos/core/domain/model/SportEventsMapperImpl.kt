package com.georgiopoulos.core.domain.model

import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel
import com.georgiopoulos.core.domain.utils.formater.DateFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SportEventsMapperImpl @Inject constructor(
    private val dateFormatter: DateFormatter,
) : SportEventsMapper {

    override fun map(dataModel: SportEventsDataModel): SportEventsDomainModel {
        return SportEventsDomainModel(
            sportId = dataModel.sportId,
            sportName = dataModel.sportName,
            activeEvents = dataModel.activeEvents.map { mapEvent(it) }
        )
    }

    override fun map(domainModel: SportEventsDomainModel): SportEventsDataModel {
        return SportEventsDataModel(
            sportId = domainModel.sportId,
            sportName = domainModel.sportName,
            activeEvents = domainModel.activeEvents.map { mapEvent(it) }
        )
    }

    override fun mapEvent(eventDataModel: EventDataModel): EventDomainModel {
        val competitors = splitEventNameIntoCompetitors(eventDataModel.eventName)

        return EventDomainModel(
            id = eventDataModel.id,
            sportId = eventDataModel.sportId,
            firstCompetitor = competitors.first,
            secondCompetitor = competitors.second,
            eventStartTime = eventDataModel.eventStartTime,
            eventDate = dateFormatter.formatUnixTime(eventDataModel.eventStartTime),
            isFavorite = eventDataModel.isFavorite
        )
    }

    override fun mapEvent(eventDomainModel: EventDomainModel): EventDataModel {
        return EventDataModel(
            id = eventDomainModel.id,
            sportId = eventDomainModel.sportId,
            eventName = "${eventDomainModel.firstCompetitor} - ${eventDomainModel.secondCompetitor}",
            eventStartTime = eventDomainModel.eventStartTime,
            isFavorite = eventDomainModel.isFavorite
        )
    }

    private fun splitEventNameIntoCompetitors(eventName: String): Pair<String, String> {
        val competitors = eventName.split(" - ")
        return Pair(
            first = competitors.getOrElse(0) { "" },
            second = competitors.getOrElse(1) { "" }
        )
    }
}
