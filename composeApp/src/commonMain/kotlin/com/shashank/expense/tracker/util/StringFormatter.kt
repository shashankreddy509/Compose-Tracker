package com.shashank.expense.tracker.util

expect object StringFormatter {
    fun format(format: String, vararg args: Any?): String
    fun formatCurrency(amount: Double): String
    fun formatPercentage(value: Double): String
} 