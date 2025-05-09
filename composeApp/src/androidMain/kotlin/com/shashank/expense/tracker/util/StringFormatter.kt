package com.shashank.expense.tracker.util

actual object StringFormatter {
    actual fun format(format: String, vararg args: Any?): String {
        return String.format(format, *args)
    }

    actual fun formatCurrency(amount: Double): String {
        TODO("Not yet implemented")
    }

    actual fun formatPercentage(value: Double): String {
        TODO("Not yet implemented")
    }
} 