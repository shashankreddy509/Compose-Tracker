package com.shashank.expense.tracker.util

actual fun extractUserMessage(e: Exception): String {
    return e.message.orEmpty()
}