package com.shashank.expense.tracker.util

import platform.Foundation.*

actual object StringFormatter {
    actual fun format(format: String, vararg args: Any?): String {
        return when (args.size) {
            0 -> NSString.stringWithFormat(format)
            1 -> NSString.stringWithFormat(format, args[0] ?: "")
            2 -> NSString.stringWithFormat(format, args[0] ?: "", args[1] ?: "")
            else -> throw IllegalArgumentException("StringFormatter.format on iOS supports up to 2 arguments.")
        }
    }

    actual fun formatCurrency(amount: Double): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterCurrencyStyle
        formatter.currencySymbol = "$"
        return formatter.stringFromNumber(NSNumber.numberWithDouble(amount)) ?: "$0.00"
    }

    actual fun formatPercentage(value: Double): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterPercentStyle
        formatter.minimumFractionDigits = 1u
        formatter.maximumFractionDigits = 1u
        return formatter.stringFromNumber(NSNumber.numberWithDouble(value / 100.0)) ?: "0.0%"
    }
} 