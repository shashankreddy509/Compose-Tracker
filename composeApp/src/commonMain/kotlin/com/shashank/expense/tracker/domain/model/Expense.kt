package com.shashank.expense.tracker.domain.model

import kotlinx.datetime.LocalDateTime

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val categoryId: Long,
    val date: LocalDateTime,
    val type: ExpenseType,
    val paymentMethod: PaymentMethod,
    val location: String? = null,
    val receiptImageUrl: String? = null
)

enum class ExpenseType {
    INCOME,
    EXPENSE
}

enum class PaymentMethod {
    CASH,
    CREDIT_CARD,
    DEBIT_CARD,
    BANK_TRANSFER,
    UPI,
    OTHER
} 