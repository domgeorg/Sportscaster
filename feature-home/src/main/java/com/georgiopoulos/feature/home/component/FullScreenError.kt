package com.georgiopoulos.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconSize.BIG
import com.georgiopoulos.core.design.widget.icon.IconType.INFO
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core.domain.model.error.ErrorModel

@Composable
fun FullScreenError(
    error: ErrorModel,
) {
    Box(
        modifier = Modifier
            .testTag("homeScreenError")
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            DesignSystemIcon(
                modifier = Modifier.padding(DesignSystemTheme.spacings.spacing8),
                iconSize = BIG,
                enabledColor = DesignSystemTheme.colors.neutralColors.neutral8,
                iconType = INFO,
            )

            DesignSystemText(
                modifier = Modifier.padding(DesignSystemTheme.spacings.spacing8),
                alignment = TextAlign.Center,
                textType = TITLE_3_INFORMATIVE,
                text = stringResource(id = error.errorMessageResId),
            )
        }
    }
}