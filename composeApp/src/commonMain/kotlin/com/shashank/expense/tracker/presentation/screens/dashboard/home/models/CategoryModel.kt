package com.shashank.expense.tracker.presentation.screens.dashboard.home.models

data class CategoryModel(
    val id: Long,
    val title: String,
    val isFavorite: Boolean = false,
    val icon: String
)