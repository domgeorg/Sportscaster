package com.georgiopoulos.feature.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextAlign.Companion
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.widget.icon.DesignSystemIcon
import com.georgiopoulos.core.design.widget.icon.IconSize.BIG
import com.georgiopoulos.core.design.widget.icon.IconType
import com.georgiopoulos.core.design.widget.text.DesignSystemText
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core_resources.R as Resources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog() {
    BasicAlertDialog(
        onDismissRequest = {},
    ) {
        Box(
            modifier = Modifier
                .testTag("homeScreenErrorDialog")
                .clip(RoundedCornerShape(DesignSystemTheme.spacings.spacing8))
                .background(DesignSystemTheme.colors.neutralColors.neutral0)
                .padding(DesignSystemTheme.spacings.spacing16),
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
                    iconType = IconType.INFO,
                )

                DesignSystemText(
                    modifier = Modifier.padding(DesignSystemTheme.spacings.spacing8),
                    alignment = TextAlign.Center,
                    textType = TITLE_3_INFORMATIVE,
                    text = stringResource(id = Resources.string.error_unknown),
                )
            }
        }
    }
}