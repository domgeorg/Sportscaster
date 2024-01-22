package com.georgiopoulos.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.loader.DesignSystemLoader
import com.georgiopoulos.core.domain.model.error.ErrorModel
import com.georgiopoulos.feature.home.HomeEvent.Dispose
import com.georgiopoulos.feature.home.component.AppBar
import com.georgiopoulos.feature.home.component.ErrorDialog
import com.georgiopoulos.feature.home.component.FullScreenError
import com.georgiopoulos.feature.home.component.SportList

@Composable
fun HomeScreen(
    state: State<HomeUiData>,
    onEvent: (HomeEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DesignSystemTheme.colors.neutralColors.neutral0),
    ) {
        AppBar()
        SportList(
            items = state.value.sportEventsList,
            onEvent = onEvent,
        )

        if (state.value.isLoading) {
            DesignSystemLoader(modifier = Modifier.fillMaxSize())
        }

        state.value.error?.let { activeError ->
            if (activeError is ErrorModel.UnknownErrorModel) {
                ErrorDialog()
            } else {
                FullScreenError(activeError)
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                onEvent(Dispose)
            }
        }
    }
}
