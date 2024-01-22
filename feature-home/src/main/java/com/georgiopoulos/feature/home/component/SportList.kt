package com.georgiopoulos.feature.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateMap
import androidx.compose.ui.Modifier
import com.georgiopoulos.core.domain.model.SportEventsDomainModel
import com.georgiopoulos.feature.home.HomeEvent

@Composable
fun SportList(
    modifier: Modifier = Modifier,
    items: List<SportEventsDomainModel> = emptyList(),
    onEvent: (HomeEvent) -> Unit = {},
) {
    val isExpandedMap = remember {
        List(items.size) { index: Int -> index to true }
            .toMutableStateMap()
    }
    val isFilteredMap = remember {
        List(items.size) { index: Int -> index to false }
            .toMutableStateMap()
    }

    Box(
        modifier = modifier,
    ) {
        LazyColumn {
            items(items.size) { index ->
                SportItem(
                    item = items[index],
                    isExpanded = isExpandedMap[index] ?: true,
                    sportIndex = index,
                    isFiltered = isFilteredMap[index] ?: false,
                    onExpandCollapse = { isExpandedMap[index] = !(isExpandedMap[index] ?: true) },
                    onFilter = { isFilteredMap[index] = !(isFilteredMap[index] ?: false) },
                    onEvent = onEvent,
                )
            }
        }
    }
}