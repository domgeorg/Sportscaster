package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.base.BaseColors
import com.georgiopoulos.core.design.theme.base.TextColors
import com.georgiopoulos.core.design.theme.base.TextModifierColors
import com.georgiopoulos.core.design.theme.base.TextStateColors

@Immutable
data class DesignSystemColors(
    val primaryColors: PrimaryColors,
    val secondaryColors: SecondaryColors,
    val functionalColors: FunctionalColors,
    val neutralColors: NeutralColors,
    val iconButton: IconButtonStateColors,
    val text: TextColors,
) {
    internal companion object {
        @Composable
        fun build(): DesignSystemColors {
            val primaryColors = PrimaryColors()
            val secondaryColors = SecondaryColors()
            val functionalColors = FunctionalColors()
            val neutralColors = NeutralColors()
            return buildLightColors(
                primaryColors = primaryColors,
                secondaryColors = secondaryColors,
                functionalColors = functionalColors,
                neutralColors = neutralColors,
            )
        }

        @Composable
        private fun buildLightColors(
            primaryColors: PrimaryColors,
            secondaryColors: SecondaryColors,
            functionalColors: FunctionalColors,
            neutralColors: NeutralColors,
        ): DesignSystemColors =
            DesignSystemColors(
                primaryColors = primaryColors,
                secondaryColors = secondaryColors,
                functionalColors = functionalColors,
                neutralColors = neutralColors,
                iconButton = IconButtonStateColors(
                    normal = IconButtonStateColors.IconButtonColors(
                        backgroundColor = neutralColors.neutral0,
                        disabledBackgroundColor = neutralColors.neutral0,
                        rippleColor = neutralColors.neutral2,
                    ),
                ),
                text = buildLightTextColors(),
            )

        @Composable
        private fun buildLightTextColors() = TextColors(
            title = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.blue400,
                    disabled = BaseColors.blue100,
                ),
                negative = TextModifierColors(
                    normal = BaseColors.red400,
                    disabled = BaseColors.red200,
                ),
                highlight = TextModifierColors(
                    normal = BaseColors.blue400,
                    disabled = BaseColors.blue200,
                ),
            ),
            body = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.neutral500,
                ),
            ),
            caption = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.neutral500,
                    disabled = BaseColors.blue100,
                ),
                negative = TextModifierColors(
                    normal = BaseColors.red400,
                    disabled = BaseColors.red200,
                ),
            ),
            link = TextStateColors(
                normal = TextModifierColors(
                    normal = BaseColors.blue400,
                    disabled = BaseColors.blue200,
                ),
            ),
        )
    }
}

@Immutable
data class ButtonColors(
    val backgroundColor: Color = Color.Transparent,
    val borderStrokeColor: Color = Color.Transparent,
    val disabledBackgroundColor: Color = Color.Transparent,
    val disabledBorderStrokeColor: Color = Color.Transparent,
    val rippleColor: Color,
    val textColor: Color,
    val disabledTextColor: Color,
    val loadingProgressColor: Color = Color.Transparent,
)

@Immutable
data class IconButtonStateColors(
    val normal: IconButtonColors,
) {

    @Immutable
    data class IconButtonColors(
        val backgroundColor: Color = Color.Transparent,
        val disabledBackgroundColor: Color = Color.Transparent,
        val rippleColor: Color,
    )
}
