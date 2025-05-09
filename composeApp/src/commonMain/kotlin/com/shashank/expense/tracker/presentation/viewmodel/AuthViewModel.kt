package com.shashank.expense.tracker.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.shashank.expense.tracker.domain.model.AuthEvent
import com.shashank.expense.tracker.domain.model.AuthUiState
import com.shashank.expense.tracker.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    private val _eventFlow = MutableSharedFlow<AuthEvent>()
    val eventFlow: SharedFlow<AuthEvent> = _eventFlow.asSharedFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    fun onEmailChange(email: String) {
        uiState = uiState.copy(email = email, emailError = null, authError = null)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password, passwordError = null, authError = null)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        uiState = uiState.copy(confirmPassword = confirmPassword, confirmPasswordError = null, authError = null)
    }

    fun login() {
        // Validate
        val emailValid = validateEmail(uiState.email)
        val passwordValid = validatePassword(uiState.password)
        if (!emailValid || !passwordValid) return

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = authRepository.login(uiState.email, uiState.password)
                if (result.isSuccess) {
                    _eventFlow.emit(AuthEvent.LoginSuccess)
                } else {
                    uiState = uiState.copy(authError = result.errorMessage)
                    _eventFlow.emit(AuthEvent.Error(result.errorMessage ?: "Login failed"))
                }
            } catch (e: Exception) {
                uiState = uiState.copy(authError = e.message)
                _eventFlow.emit(AuthEvent.Error(e.message ?: "An unexpected error occurred"))
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun register() {
        // Validate
        val emailValid = validateEmail(uiState.email)
        val passwordValid = validatePassword(uiState.password)
        val confirmPasswordValid = uiState.password == uiState.confirmPassword
        if (!emailValid || !passwordValid || !confirmPasswordValid) {
            uiState = uiState.copy(
                confirmPasswordError = if (!confirmPasswordValid) "Passwords do not match" else null
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val result = authRepository.register(uiState.email, uiState.password)
                if (result.isSuccess) {
                    _eventFlow.emit(AuthEvent.RegisterSuccess)
                } else {
                    uiState = uiState.copy(authError = result.errorMessage)
                    _eventFlow.emit(AuthEvent.Error(result.errorMessage ?: "Registration failed"))
                }
            } catch (e: Exception) {
                uiState = uiState.copy(authError = e.message)
                _eventFlow.emit(AuthEvent.Error(e.message ?: "An unexpected error occurred"))
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            uiState = uiState.copy(emailError = "Email is required")
            false
        } else if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) {
            uiState = uiState.copy(emailError = "Please enter a valid email")
            false
        } else {
            uiState = uiState.copy(emailError = null)
            true
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            uiState = uiState.copy(passwordError = "Password is required")
            false
        } else if (password.length < 6) {
            uiState = uiState.copy(passwordError = "Password must be at least 6 characters")
            false
        } else {
            uiState = uiState.copy(passwordError = null)
            true
        }
    }
}
