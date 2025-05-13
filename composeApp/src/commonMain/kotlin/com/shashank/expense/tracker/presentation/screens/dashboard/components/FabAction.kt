package com.shashank.expense.tracker.presentation.screens.dashboard.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class FabAction(
    val label: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val offset: Offset
)

data class Offset(val x: Float, val y: Float)