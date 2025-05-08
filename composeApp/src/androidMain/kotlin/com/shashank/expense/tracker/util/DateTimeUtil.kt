package com.shashank.expense.tracker.util

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

actual object DateTimeUtil {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val monthYearFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")

    actual fun formatDateTime(dateTime: LocalDateTime): String {
        return dateTime.toJavaLocalDateTime().format(dateTimeFormatter)
    }

    actual fun formatDate(dateTime: LocalDateTime): String {
        return dateTime.toJavaLocalDateTime().format(dateFormatter)
    }

    actual fun formatTime(dateTime: LocalDateTime): String {
        return dateTime.toJavaLocalDateTime().format(timeFormatter)
    }

    actual fun formatMonthYear(dateTime: LocalDateTime): String {
        return dateTime.toJavaLocalDateTime().format(monthYearFormatter)
    }
} 