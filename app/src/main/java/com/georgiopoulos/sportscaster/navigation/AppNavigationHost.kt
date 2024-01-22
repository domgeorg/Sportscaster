package com.georgiopoulos.sportscaster.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.georgiopoulos.core.navigation.AppComposeNavigator
import com.georgiopoulos.core.navigation.SportscasterScreens
import com.georgiopoulos.sportscaster.navigation.superHeroNavigation

@Composable
fun SuperHeroNavigationHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = SportscasterScreens.Home.name,
    ) {
        superHeroNavigation()
    }
}
