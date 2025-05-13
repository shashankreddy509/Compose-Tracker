package com.shashank.expense.tracker.presentation.screens.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FabActionButton(
    action: FabAction,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        label = "fabActionScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        label = "fabActionAlpha"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .alpha(alpha)
            .size(48.dp),
        containerColor = action.backgroundColor
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.label,
            tint = Color.White
        )
    }
}