package com.georgiopoulos.core.design.widget.switcher

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.georgiopoulos.core.design.theme.DesignSystemTheme
import com.georgiopoulos.core.design.theme.util.withEnabledStateAwareness
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun DesignSystemSwitch(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
) {
    Box(
        modifier = modifier,
    ) {
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DesignSystemTheme.colors.primaryColors.blue.withEnabledStateAwareness(enabled),
                uncheckedThumbColor = Color.White.withEnabledStateAwareness(enabled),
                checkedTrackColor = DesignSystemTheme.colors.neutralColors.neutral4.withEnabledStateAwareness(enabled),
                uncheckedTrackColor = DesignSystemTheme.colors.neutralColors.neutral3.withEnabledStateAwareness(enabled),
            ),
            enabled = enabled,
            interactionSource = remember { DisabledInteractionSource() },
        )
    }
}

private class DisabledInteractionSource : MutableInteractionSource {

    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction): Boolean = true
}
