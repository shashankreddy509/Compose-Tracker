package com.shashank.expense.tracker.presentation.screens.dashboard.components

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource

data class FabAction(
    val label: String,
    val icon: DrawableResource,
    val backgroundColor: Color,
    val offset: Offset
)

data class Offset(val x: Float, val y: Float)