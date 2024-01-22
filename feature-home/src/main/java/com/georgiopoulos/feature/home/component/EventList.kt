package com.georgiopoulos.feature.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.domain.model.EventDomainModel
import com.georgiopoulos.feature.home.HomeEvent

const val ANIMATION_DURATION_MILLS = 400

@Composable
fun EventList(
    items: List<EventDomainModel>,
    sportIndex: Int,
    isExpanded: Boolean = false,
    onEvent: (HomeEvent) -> Unit,
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(ANIMATION_DURATION_MILLS),
        ) +
        fadeIn(
            initialAlpha = .3f,
            animationSpec = tween(ANIMATION_DURATION_MILLS),
        )
    }

    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(ANIMATION_DURATION_MILLS),
        ) +
        fadeOut(
            animationSpec = tween(ANIMATION_DURATION_MILLS),
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        LazyHorizontalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 260.dp)
                .background(DesignSystemTheme.colors.neutralColors.neutral4),
            rows = Fixed(2)
        ) {
            items(items.size) { index ->
                EventItem(
                    item = items[index],
                    index = index,
                    sportIndex = sportIndex,
                    onEvent = onEvent
                )
            }
        }
    }
}
