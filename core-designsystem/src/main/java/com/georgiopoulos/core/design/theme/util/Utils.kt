package com.georgiopoulos.core.design.theme.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.LineHeightStyle.Alignment
import androidx.compose.ui.text.style.LineHeightStyle.Trim.Companion.None
import com.georgiopoulos.core.design.DesignSystemConstants.OPACITY_DISABLED
import com.georgiopoulos.core.design.DesignSystemConstants.OPACITY_ENABLED

internal fun TextStyle.withDesignSystemFontCorrection(): TextStyle {
    return merge(
        TextStyle(
            platformStyle = PlatformTextStyle(
                includeFontPadding = false,
            ),
            lineHeightStyle = LineHeightStyle(
                alignment = Alignment.Proportional,
                trim = None,
            ),
        ),
    )
}

internal fun Color.withEnabledStateAwareness(enabled: Boolean): Color {
    return this.copy(
        alpha = if (enabled) {
            OPACITY_ENABLED
        } else {
            OPACITY_DISABLED
        },
    )
}
