package com.shashank.expense.tracker.util

import kotlinx.datetime.LocalDateTime
import platform.Foundation.*

actual object DateTimeUtil {
    private val dateTimeFormatter = NSDateFormatter().apply {
        dateFormat = "dd MMM yyyy, HH:mm"
    }
    private val dateFormatter = NSDateFormatter().apply {
        dateFormat = "dd MMM yyyy"
    }
    private val timeFormatter = NSDateFormatter().apply {
        dateFormat = "HH:mm"
    }
    private val monthYearFormatter = NSDateFormatter().apply {
        dateFormat = "MMMM yyyy"
    }

    actual fun formatDateTime(dateTime: LocalDateTime): String {
        val date = dateTime.toNSDate()
        return dateTimeFormatter.stringFromDate(date)
    }

    actual fun formatDate(dateTime: LocalDateTime): String {
        val date = dateTime.toNSDate()
        return dateFormatter.stringFromDate(date)
    }

    actual fun formatTime(dateTime: LocalDateTime): String {
        val date = dateTime.toNSDate()
        return timeFormatter.stringFromDate(date)
    }

    actual fun formatMonthYear(dateTime: LocalDateTime): String {
        val date = dateTime.toNSDate()
        return monthYearFormatter.stringFromDate(date)
    }

    private fun LocalDateTime.toNSDate(): NSDate {
        val components = NSDateComponents().apply {
            year = this@toNSDate.year
            month = this@toNSDate.monthNumber
            day = this@toNSDate.dayOfMonth
            hour = this@toNSDate.hour
            minute = this@toNSDate.minute
            second = this@toNSDate.second
        }
        return NSCalendar.currentCalendar.dateFromComponents(components)!!
    }
} 