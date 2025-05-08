package com.shashank.expense.tracker.util

import kotlinx.datetime.LocalDateTime

expect object DateTimeUtil {
    fun formatDateTime(dateTime: LocalDateTime): String
    fun formatDate(dateTime: LocalDateTime): String
    fun formatTime(dateTime: LocalDateTime): String
    fun formatMonthYear(dateTime: LocalDateTime): String
} 