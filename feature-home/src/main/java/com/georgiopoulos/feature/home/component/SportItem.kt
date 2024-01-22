package com.georgiopoulos.feature.home.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconSize.BIG
import com.georgiopoulos.core.design.widget.icon.IconType.CHEVRON_UP
import com.georgiopoulos.core.design.widget.switcher.DesignSystemSwitch
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.feature.home.HomeEvent
import com.georgiopoulos.feature.home.HomeEvent.FilterFavoriteEvents
import com.georgiopoulos.feature.home.HomeEvent.GetEventsBySportId

@Composable
fun SportItem(
    item: SportEventsDomainModel,
    sportIndex: Int,
    isExpanded: Boolean,
    isFiltered: Boolean,
    onFilter: () -> Unit,
    onExpandCollapse: () -> Unit,
    onEvent: (HomeEvent) -> Unit,
) {
    val transition = updateTransition(targetState = isExpanded, label = "transition")
    val iconRotationDegrees by transition.animateFloat(label = "icon rotation") { state ->
        if (state) {
            0f
        } else {
            180f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DesignSystemTheme.spacings.spacing16),
        ) {
            DesignSystemText(
                modifier = Modifier.align(Alignment.CenterStart),
                text = item.sportName,
                textType = TITLE_3_INFORMATIVE,
            )

            Row(modifier = Modifier.align(Alignment.CenterEnd)) {
                DesignSystemSwitch(
                    modifier = Modifier.padding(end = DesignSystemTheme.spacings.spacing8),
                    checked = isFiltered,
                    onCheckedChange = { _ ->
                        if (isFiltered) {
                            onEvent.invoke(GetEventsBySportId(sportIndex, item.sportId))
                        } else {
                            onEvent.invoke(FilterFavoriteEvents(item.sportId, sportIndex, item.activeEvents))
                        }
                        onFilter.invoke()
                    }
                )

                DesignSystemIcon(
                    modifier = Modifier
                        .rotate(iconRotationDegrees)
                        .padding(
                            vertical = DesignSystemTheme.spacings.spacing8,
                            horizontal = DesignSystemTheme.spacings.spacing4,
                        )
                        .clickable { onExpandCollapse.invoke() },
                    iconType = CHEVRON_UP,
                    iconSize = BIG,
                    enabledColor = DesignSystemTheme.colors.neutralColors.neutral8,
                    disabledColor = DesignSystemTheme.colors.neutralColors.neutral8,
                )
            }
        }

        EventList(
            items = item.activeEvents,
            isExpanded = isExpanded,
            sportIndex = sportIndex,
            onEvent = onEvent,
        )
    }
}
