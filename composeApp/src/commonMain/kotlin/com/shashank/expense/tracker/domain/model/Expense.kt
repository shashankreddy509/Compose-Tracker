package com.shashank.expense.tracker.domain.model

import kotlinx.datetime.LocalDateTime

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val categoryId: Long,
    val accountId: Long,
    val date: LocalDateTime,
    val type: TransactionType,
    val paymentMethod: PaymentMethod,
    val location: String? = null,
    val receiptImageUrl: String? = null
) 