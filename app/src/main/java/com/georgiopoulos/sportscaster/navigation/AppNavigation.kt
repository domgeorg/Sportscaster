package com.georgiopoulos.sportscaster.navigation

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.georgiopoulos.core.navigation.SportscasterScreens
import com.georgiopoulos.feature.home.HomeScreen
import com.georgiopoulos.feature.home.HomeViewModel

fun NavGraphBuilder.superHeroNavigation() {
    composable(route = SportscasterScreens.Home.name) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(
            state = homeViewModel.uiState.collectAsState(),
            onEvent = { event -> homeViewModel.triggerEvent(event) },
        )
    }
}
