package com.georgiopoulos.sportscaster.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.georgiopoulos.core.design.AppBackground
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.sportscaster.navigation.SuperHeroNavigationHost

@Composable
fun AppBaseUi() {
    DesignSystemTheme {
        val navHostController = rememberNavController()

        AppBackground {
            SuperHeroNavigationHost(
                navHostController = navHostController,
            )
        }
    }
}
