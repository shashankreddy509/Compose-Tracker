package com.shashank.expense.tracker.presentation.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

actual abstract class BaseViewModel : CommonViewModel {
    actual override val viewModelScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    protected actual open fun onCleared() {
        // Clean up resources if needed
    }

    actual fun <T> Flow<T>.stateInViewModel(initialValue: T): StateFlow<T> {
        val state = MutableStateFlow(initialValue)
        viewModelScope.launch {
            this@stateInViewModel.collect {
                state.value = it
            }
        }
        return state
    }
}
