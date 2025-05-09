package com.shashank.expense.tracker.presentation.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

interface CommonViewModel {
    val viewModelScope: CoroutineScope
}

// Platform-specific expect class
expect abstract class BaseViewModel() : CommonViewModel {
    override val viewModelScope: CoroutineScope
    protected open fun onCleared()
    fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T>
}

// Generic base viewmodel for shared logic (not expect/actual)
abstract class BaseViewModelWithState<State, Event>(initialState: State) : BaseViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state

    private val _event = MutableSharedFlow<Event>()
    val event: Flow<Event> = _event

    protected fun setState(reducer: State.() -> State) {
        val newState = _state.value.reducer()
        _state.value = newState
    }

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    protected fun <T> Flow<T>.collectInViewModel(action: suspend (T) -> Unit) {
        viewModelScope.launch {
            collect { action(it) }
        }
    }
}