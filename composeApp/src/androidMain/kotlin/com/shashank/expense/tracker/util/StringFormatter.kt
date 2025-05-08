package com.shashank.expense.tracker.util

actual object StringFormatter {
    actual fun format(format: String, vararg args: Any?): String {
        return String.format(format, *args)
    }
} 