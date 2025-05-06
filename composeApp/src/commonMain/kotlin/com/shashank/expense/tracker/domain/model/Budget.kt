package com.shashank.expense.tracker.domain.model

import kotlinx.datetime.LocalDateTime

data class Budget(
    val id: Long = 0,
    val categoryId: Long,
    val amount: Double,
    val startDate: Long,
    val endDate: Long
)

enum class RecurringPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
} 