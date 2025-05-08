package com.shashank.expense.tracker.util

expect object StringFormatter {
    fun format(format: String, vararg args: Any?): String
} 