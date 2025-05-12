package com.shashank.expense.tracker.util

object Utils {

    fun currencyFormater(value: Float): String {
        return when {
            value >= 1_000_000 -> "${(value / 1_000_000).toInt()}M"
            value >= 1_000 -> "${(value / 1_000).toInt()}K"
            else -> value.toInt().toString()
        }
    }
}