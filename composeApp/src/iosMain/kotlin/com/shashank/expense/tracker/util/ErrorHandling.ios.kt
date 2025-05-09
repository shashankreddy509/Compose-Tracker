package com.shashank.expense.tracker.util

import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.UserInfo
import dev.gitlive.firebase.auth.ios
import platform.Foundation.NSError

actual fun extractUserMessage(e: Exception): String {

//    val nsError = (e as UserInfo)
////    val nsError = (e as NSError)
//    return e.message.orEmpty()
    // For iOS, parse the NSError description string
    val errorString = e.toString()

    // Extract just the NSLocalizedDescription part
    val localizedDescriptionRegex = "NSLocalizedDescription=([^,}]+)".toRegex()
    val match = localizedDescriptionRegex.find(errorString)

    return match?.groups?.get(1)?.value ?: "Authentication failed"
}