package com.shashank.expense.tracker.domain.model

import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: String,
    val amount: Double,
    val description: String,
    val category: String,
    val account: String,
    val date: LocalDateTime,
    val type: TransactionType,
    val paymentMethod: PaymentMethod,
    val receiptUrl: String? = null
) 