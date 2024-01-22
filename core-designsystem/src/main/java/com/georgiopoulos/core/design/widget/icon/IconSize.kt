package com.georgiopoulos.core.design.widget.icon

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class IconSize(
    private val id: Int,
    val size: Dp,
    val padding: Dp,
) {
    NORMAL(0, 24.dp, 0.dp),
    SMALL(1, 24.dp, 0.dp),
    BIG(1, 28.dp, 0.dp),
    ;
}
