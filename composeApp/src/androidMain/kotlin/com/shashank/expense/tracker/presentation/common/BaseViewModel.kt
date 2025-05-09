package com.shashank.expense.tracker.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual abstract class BaseViewModel : ViewModel(), CommonViewModel {
    override val viewModelScope: CoroutineScope
        get() = super.viewModelScope

    override fun onCleared() {
        super.onCleared()
    }
} 