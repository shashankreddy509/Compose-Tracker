package com.shashank.expense.tracker.domain.model

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "", // for register
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val authError: String? = null,
    val isLoading: Boolean = false
)