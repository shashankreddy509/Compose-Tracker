package com.shashank.expense.tracker.domain.model

import kotlinx.datetime.LocalDateTime

data class Budget(
    val id: String,
    val amount: Double,
    val spent: Double,
    val remaining: Double,
    val category: String,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

enum class RecurringPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
} 