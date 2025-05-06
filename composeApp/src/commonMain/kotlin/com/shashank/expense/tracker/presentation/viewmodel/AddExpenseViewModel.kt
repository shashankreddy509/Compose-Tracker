package com.shashank.expense.tracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shashank.expense.tracker.domain.model.Expense
import com.shashank.expense.tracker.domain.model.ExpenseType
import com.shashank.expense.tracker.domain.model.PaymentMethod
import com.shashank.expense.tracker.domain.usecase.AddExpenseUseCase
import com.shashank.expense.tracker.domain.usecase.GetCategoriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class AddExpenseState(
    val amount: String = "",
    val description: String = "",
    val categoryId: Long? = null,
    val type: ExpenseType = ExpenseType.EXPENSE,
    val paymentMethod: PaymentMethod = PaymentMethod.CASH,
    val location: String = "",
    val receiptImageUrl: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class AddExpenseViewModel(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddExpenseState())
    val state: StateFlow<AddExpenseState> = _state.asStateFlow()

    fun onAmountChange(amount: String) {
        _state.value = _state.value.copy(amount = amount)
    }

    fun onDescriptionChange(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun onCategoryChange(categoryId: Long) {
        _state.value = _state.value.copy(categoryId = categoryId)
    }

    fun onTypeChange(type: ExpenseType) {
        _state.value = _state.value.copy(type = type)
    }

    fun onPaymentMethodChange(paymentMethod: PaymentMethod) {
        _state.value = _state.value.copy(paymentMethod = paymentMethod)
    }

    fun onLocationChange(location: String) {
        _state.value = _state.value.copy(location = location)
    }

    fun onReceiptImageUrlChange(url: String?) {
        _state.value = _state.value.copy(receiptImageUrl = url)
    }

    fun addExpense() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                
                val amount = _state.value.amount.toDoubleOrNull()
                if (amount == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Invalid amount"
                    )
                    return@launch
                }

                val categoryId = _state.value.categoryId
                if (categoryId == null) {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Please select a category"
                    )
                    return@launch
                }

                val expense = Expense(
                    id = 0, // Let the database generate the ID
                    amount = amount,
                    description = _state.value.description,
                    categoryId = categoryId,
                    date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    type = _state.value.type,
                    paymentMethod = _state.value.paymentMethod,
                    location = _state.value.location,
                    receiptImageUrl = _state.value.receiptImageUrl
                )

                addExpenseUseCase(expense)
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }
} 