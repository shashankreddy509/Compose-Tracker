package com.shashank.expense.tracker.util


actual fun extractUserMessage(e: Exception): String {
    // For iOS, parse the NSError description string
    val errorString = e.toString()

    // Extract just the NSLocalizedDescription part
    val localizedDescriptionRegex = "NSLocalizedDescription=([^,}]+)".toRegex()
    val match = localizedDescriptionRegex.find(errorString)

    return match?.groups?.get(1)?.value ?: "Authentication failed"
}