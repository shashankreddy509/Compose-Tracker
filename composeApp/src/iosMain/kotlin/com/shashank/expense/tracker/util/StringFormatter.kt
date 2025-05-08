package com.shashank.expense.tracker.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual object StringFormatter {
    actual fun format(format: String, vararg args: Any?): String {
        return NSString.stringWithFormat(format, args = args)
    }
} 