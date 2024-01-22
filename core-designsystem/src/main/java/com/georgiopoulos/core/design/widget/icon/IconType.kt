package com.georgiopoulos.core.design.widget.icon

import com.georgiopoulos.core_resources.R as Resources

enum class IconType(
    private val id: Int,
    val resId: Int,
) {
    NONE(-1, 0),
    STAR_UNSELECTED(0, Resources.drawable.icon_star_unselected),
    STAR_SELECTED(1, Resources.drawable.icon_star_selected),
    CHEVRON_DOWN(2, Resources.drawable.icon_chevron_down),
    CHEVRON_UP(3, Resources.drawable.icon_chevron_up),
    INFO(4, Resources.drawable.icon_info),
    ;
}
