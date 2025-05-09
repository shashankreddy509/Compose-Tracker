package com.shashank.expense.tracker.domain.repository

data class AuthResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)

interface AuthRepository {
    suspend fun login(email: String, password: String): AuthResult
    suspend fun register(email: String, password: String): AuthResult
    suspend fun logout()
    suspend fun isUserLoggedIn(): Boolean
}