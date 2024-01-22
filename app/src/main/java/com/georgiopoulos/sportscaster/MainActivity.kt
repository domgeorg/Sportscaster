package com.georgiopoulos.sportscaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.sportscaster.ui.AppBaseUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesignSystemTheme {
                AppBaseUi()
            }
        }
    }
}