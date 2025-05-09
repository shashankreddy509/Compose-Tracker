package com.shashank.expense.tracker.data.repository

import com.shashank.expense.tracker.domain.repository.AuthRepository
import com.shashank.expense.tracker.domain.repository.AuthResult
import com.shashank.expense.tracker.util.extractUserMessage
import dev.gitlive.firebase.auth.FirebaseAuth
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository, KoinComponent {

    override suspend fun login(email: String, password: String): AuthResult {
        return try {
            auth.signInWithEmailAndPassword(email, password)
            AuthResult(isSuccess = true)
        } catch (e: Exception) {
            AuthResult(isSuccess = false, errorMessage = extractUserMessage(e))
        }
    }

    override suspend fun register(email: String, password: String): AuthResult {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
            AuthResult(isSuccess = true)
        } catch (e: Exception) {
            AuthResult(isSuccess = false, errorMessage = extractUserMessage(e))
        }
    }

    override suspend fun logout() {
        auth.signOut()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
