package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.base.BaseColors

@Immutable
data class PrimaryColors internal constructor(
    val blue: Color = BaseColors.blue400,
    val yellow: Color = BaseColors.yellow400,
    val red: Color = BaseColors.red400,
    val informativeBlue: Color = BaseColors.blue300,
    val highlightBlue: Color = BaseColors.blue300,
    val highlightRed: Color = BaseColors.red400,
)

@Immutable
data class SecondaryColors internal constructor(
    val lightYellow: Color = BaseColors.yellow200,
    val lightBlue: Color = BaseColors.blue200,
    val lightRed: Color = BaseColors.red200,
    val backgroundYellow: Color = BaseColors.yellow100,
    val backgroundBlue: Color = BaseColors.blue100,
    val backgroundRed: Color = BaseColors.red100,
)

@Immutable
data class FunctionalColors internal constructor(
    val destructiveRed: Color = BaseColors.red400,
    val warningYellow: Color = BaseColors.yellow400,
    val informativeBlue: Color = BaseColors.blue400,
)

@Immutable
data class NeutralColors internal constructor(
    val neutral0: Color = BaseColors.neutral100,
    val neutral1: Color = BaseColors.neutral200,
    val neutral2: Color = BaseColors.neutral300,
    val neutral3: Color = BaseColors.neutral400,
    val neutral4: Color = BaseColors.neutral500,
    val neutral5: Color = BaseColors.neutral600,
    val neutral6: Color = BaseColors.neutral700,
    val neutral7: Color = BaseColors.neutral800,
    val neutral8: Color = BaseColors.neutral900,
)
