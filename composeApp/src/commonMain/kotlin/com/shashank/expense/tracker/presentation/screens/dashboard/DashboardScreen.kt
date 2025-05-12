package com.shashank.expense.tracker.presentation.screens.dashboard

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.presentation.components.BottomNavItem
import com.shashank.expense.tracker.presentation.components.BottomNavigationBar
import com.shashank.expense.tracker.presentation.screens.dashboard.addtransaction.AddTransactionScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.budget.BudgetScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.home.HomeScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.profile.ProfileScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.TransactionScreen
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

class DashboardScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Dashboard.toString()

    @Composable
    override fun Content() {
        DashboardScreen()
    }

    @Composable
    fun DashboardScreen() {
        var selectedRoute = remember { mutableStateOf(BottomNavItem.Home.route) }
        val viewModel: HomeViewModel = koinViewModel()
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedRoute = selectedRoute.value,
                    onItemSelected = { item -> selectedRoute.value = item.route }
                )
            }
        ) { innerPadding ->
            when (selectedRoute.value) {
                BottomNavItem.Home.route -> HomeScreen(viewModel,selectedRoute)
                BottomNavItem.Transaction.route -> TransactionScreen(viewModel)
                BottomNavItem.Budget.route -> BudgetScreen()
                BottomNavItem.Profile.route -> ProfileScreen()
                BottomNavItem.Add.route -> AddTransactionScreen()
            }
        }
    }
}
