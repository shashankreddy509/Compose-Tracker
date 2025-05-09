package com.shashank.expense.tracker.util

import platform.Foundation.*
import kotlinx.datetime.*

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

    fun getCurrentDate(): LocalDate {
        val now = NSDate()
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitYear or
            NSCalendarUnitMonth or
            NSCalendarUnitDay,
            fromDate = now
        )
        
        return LocalDate(
            year = components.year.toInt(),
            month = Month(components.month.toInt()),
            dayOfMonth = components.day.toInt()
        )
    }

    fun getCurrentTime(): LocalTime {
        val now = NSDate()
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitHour or
            NSCalendarUnitMinute or
            NSCalendarUnitSecond,
            fromDate = now
        )
        
        return LocalTime(
            hour = components.hour.toInt(),
            minute = components.minute.toInt(),
            second = components.second.toInt()
        )
    }

    fun getCurrentDateTime(): LocalDateTime {
        val now = NSDate()
        val calendar = NSCalendar.currentCalendar
        val components = calendar.components(
            NSCalendarUnitYear or
            NSCalendarUnitMonth or
            NSCalendarUnitDay or
            NSCalendarUnitHour or
            NSCalendarUnitMinute or
            NSCalendarUnitSecond,
            fromDate = now
        )
        
        return LocalDateTime(
            year = components.year.toInt(),
            month = Month(components.month.toInt()),
            dayOfMonth = components.day.toInt(),
            hour = components.hour.toInt(),
            minute = components.minute.toInt(),
            second = components.second.toInt()
        )
    }

    actual fun formatDateTime(dateTime: LocalDateTime): String {
        val formatter = NSDateFormatter()
        formatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
        val components = NSDateComponents().apply {
            year = dateTime.year.toLong()
            month = dateTime.monthNumber.toLong()
            day = dateTime.dayOfMonth.toLong()
            hour = dateTime.hour.toLong()
            minute = dateTime.minute.toLong()
            second = dateTime.second.toLong()
        }
        val calendar = NSCalendar.currentCalendar
        val nsDate = calendar.dateFromComponents(components)
        return formatter.stringFromDate(nsDate!!)
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
            year = this@toNSDate.year.toLong()
            month = this@toNSDate.monthNumber.toLong()
            day = this@toNSDate.dayOfMonth.toLong()
            hour = this@toNSDate.hour.toLong()
            minute = this@toNSDate.minute.toLong()
            second = this@toNSDate.second.toLong()
        }
        return NSCalendar.currentCalendar.dateFromComponents(components)!!
    }
} 