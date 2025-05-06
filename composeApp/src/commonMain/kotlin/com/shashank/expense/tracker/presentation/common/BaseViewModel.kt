package com.shashank.expense.tracker.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event> : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<State>>(UiState.Loading)
    val uiState: StateFlow<UiState<State>> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    abstract fun handleEvent(event: Event)

    protected fun updateState(state: UiState<State>) {
        _uiState.value = state
    }

    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    protected fun <T> Flow<Result<T>>.collectResult(
        onSuccess: (T) -> Unit,
        onError: (Exception) -> Unit,
        onLoading: () -> Unit
    ) {
        viewModelScope.launch {
            collect { result ->
                when (result) {
                    is Result.Success -> onSuccess(result.data)
                    is Result.Error -> onError(result.exception)
                    Result.Loading -> onLoading()
                }
            }
        }
    }
} 