package com.shashank.expense.tracker.presentation.components

import androidx.compose.ui.graphics.Color
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_add
import expense_tracker_compose.composeapp.generated.resources.ic_home
import expense_tracker_compose.composeapp.generated.resources.ic_pie_chart
import expense_tracker_compose.composeapp.generated.resources.ic_transaction
import expense_tracker_compose.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavItem(
    val route: String,
    val icon: DrawableResource,
    val label: String,
    val activeColor: Color = Color(0xFF5E35B1)
) {
    object Home : BottomNavItem("home", Res.drawable.ic_home, "Home")
    object Transaction : BottomNavItem("transaction", Res.drawable.ic_transaction, "Transaction")
    object Add : BottomNavItem("add", Res.drawable.ic_add, "")
    object Budget : BottomNavItem("budget", Res.drawable.ic_pie_chart, "Budget")
    object Profile : BottomNavItem("profile", Res.drawable.ic_user, "Profile")
    companion object {
        val items = listOf(Home, Transaction, Add, Budget, Profile)
    }
} 