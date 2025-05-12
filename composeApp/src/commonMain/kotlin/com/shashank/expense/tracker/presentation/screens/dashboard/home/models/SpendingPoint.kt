package com.shashank.expense.tracker.presentation.screens.dashboard.home.models

import kotlinx.datetime.LocalDateTime

data class SpendingPoint(
    val value: Float,
    val label: String,
    val timestamp: LocalDateTime
)