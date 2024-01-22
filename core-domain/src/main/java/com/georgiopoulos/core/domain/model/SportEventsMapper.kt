package com.georgiopoulos.core.domain.model

import com.georgiopoulos.core.data.model.EventDataModel
import com.georgiopoulos.core.data.model.SportEventsDataModel

interface SportEventsMapper {

    fun map(dataModel: SportEventsDataModel): SportEventsDomainModel

    fun map(domainModel: SportEventsDomainModel): SportEventsDataModel

    fun mapEvent(eventDataModel: EventDataModel): EventDomainModel

    fun mapEvent(eventDomainModel: EventDomainModel): EventDataModel
}
