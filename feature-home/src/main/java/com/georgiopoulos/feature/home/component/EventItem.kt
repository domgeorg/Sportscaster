package com.georgiopoulos.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconType.STAR_SELECTED
import com.georgiopoulos.core.design.widget.icon.IconType.STAR_UNSELECTED
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.MaxLinesConfig.ForcedMaxLines
import com.georgiopoulos.core.design.widget.text.TextType.BODY_1
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4_DESTRUCTIVE
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.feature.home.HomeEvent
import com.georgiopoulos.feature.home.HomeEvent.UpdateFavoriteEvent
import com.georgiopoulos.core_resources.R as Resources

@Composable
fun EventItem(
    item: EventDomainModel,
    index: Int,
    sportIndex: Int,
    onEvent: (HomeEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .height(100.dp)
            .width(150.dp)
            .padding(DesignSystemTheme.spacings.spacing8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DesignSystemText(
            text = item.countDown.ifEmpty { item.eventDate },
            textType = BODY_1,
        )
        DesignSystemIcon(
            modifier = Modifier
                .padding(DesignSystemTheme.spacings.spacing2)
                .clickable {
                    onEvent.invoke(
                        UpdateFavoriteEvent(
                            sportEvent = item,
                            sportIndex = sportIndex,
                            sportEventIndex = index,
                            addToFavorites = item.isFavorite.not()
                        )
                    )
                },
            iconType = if (item.isFavorite) STAR_SELECTED else STAR_UNSELECTED,
        )
        DesignSystemText(
            text = item.firstCompetitor,
            alignment = TextAlign.Center,
            maxLines = ForcedMaxLines(1),
        )
        DesignSystemText(
            text = stringResource(id = Resources.string.vs),
            alignment = TextAlign.Center,
            textType = TITLE_4_DESTRUCTIVE,
        )
        DesignSystemText(
            text = item.secondCompetitor,
            alignment = TextAlign.Center,
            maxLines = ForcedMaxLines(1),
        )
    }
}
