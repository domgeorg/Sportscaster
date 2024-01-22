package com.georgiopoulos.core.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.georgiopoulos.core.design.theme.base.BaseColors
import com.georgiopoulos.core.design.theme.base.BaseFonts.fontFamily
import com.georgiopoulos.core.design.theme.util.withDesignSystemFontCorrection
import com.georgiopoulos.core.design.widget.text.TextSize
import com.georgiopoulos.core.design.widget.text.TextType
import com.georgiopoulos.core.design.widget.text.TextType.BODY_1
import com.georgiopoulos.core.design.widget.text.TextType.BODY_1_HIGHLIGHT
import com.georgiopoulos.core.design.widget.text.TextType.BODY_2
import com.georgiopoulos.core.design.widget.text.TextType.BODY_2_HIGHLIGHT
import com.georgiopoulos.core.design.widget.text.TextType.CAPTION
import com.georgiopoulos.core.design.widget.text.TextType.CAPTION_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_1_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_2_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_3_INFORMATIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4_DESTRUCTIVE
import com.georgiopoulos.core.design.widget.text.TextType.TITLE_4_INFORMATIVE

@Immutable
data class Typography(
    val text: Text,
) {
    internal companion object {
        @ReadOnlyComposable
        @Composable
        private fun TextType.asTextStyle(): TextStyle =
            TextStyle(
                fontFamily = fontFamily,
                fontSize = textSizeSp.sp,
                fontWeight = fontWeight,
                color = textColor,
                lineHeight = lineHeightSp.sp,
            ).withDesignSystemFontCorrection()

        @Suppress("LongMethod")
        @Composable
        fun build(): Typography =
            Typography(
                text = Text(
                    title1 = TITLE_1.asTextStyle(),
                    title2 = TITLE_2.asTextStyle(),
                    title3 = TITLE_3.asTextStyle(),
                    title4 = TITLE_4.asTextStyle(),
                    title1Destructive = TITLE_1_DESTRUCTIVE.asTextStyle(),
                    title2Destructive = TITLE_2_DESTRUCTIVE.asTextStyle(),
                    title3Destructive = TITLE_3_DESTRUCTIVE.asTextStyle(),
                    title4Destructive = TITLE_4_DESTRUCTIVE.asTextStyle(),
                    title1Informative = TITLE_1_INFORMATIVE.asTextStyle(),
                    title2Informative = TITLE_2_INFORMATIVE.asTextStyle(),
                    title3Informative = TITLE_3_INFORMATIVE.asTextStyle(),
                    title4Informative = TITLE_4_INFORMATIVE.asTextStyle(),
                    body1 = BODY_1.asTextStyle(),
                    body1Highlight = BODY_1_HIGHLIGHT.asTextStyle(),
                    body2 = BODY_2.asTextStyle(),
                    body2Highlight = BODY_2_HIGHLIGHT.asTextStyle(),
                    caption = CAPTION.asTextStyle(),
                    captionDestructive = CAPTION_DESTRUCTIVE.asTextStyle(),
                ),
            )

        private val TextType.fontWeight
            get() = if (isBold) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
    }

    @Immutable
    data class Text(
        val title1: TextStyle,
        val title2: TextStyle,
        val title3: TextStyle,
        val title4: TextStyle,
        val title1Destructive: TextStyle,
        val title2Destructive: TextStyle,
        val title3Destructive: TextStyle,
        val title4Destructive: TextStyle,
        val title1Informative: TextStyle,
        val title2Informative: TextStyle,
        val title3Informative: TextStyle,
        val title4Informative: TextStyle,
        val body1: TextStyle,
        val body1Highlight: TextStyle,
        val body2: TextStyle,
        val body2Highlight: TextStyle,
        val caption: TextStyle,
        val captionDestructive: TextStyle,
    )
}
