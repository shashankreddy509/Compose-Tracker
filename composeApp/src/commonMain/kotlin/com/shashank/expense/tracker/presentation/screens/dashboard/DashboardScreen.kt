package com.shashank.expense.tracker.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.core.navigation.navigateToScreen
import com.shashank.expense.tracker.presentation.components.BottomNavItem
import com.shashank.expense.tracker.presentation.screens.dashboard.budget.BudgetScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.components.CustomBottomNavigation
import com.shashank.expense.tracker.presentation.screens.dashboard.home.HomeScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.profile.ProfileScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.TransactionEntryScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.TransactionScreen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.components.TransactionType
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.viewmodel.koinViewModel

class DashboardScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Dashboard.toString()

    @Composable
    override fun Content() {
        DashboardScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    fun DashboardScreen() {
        val navigator = LocalNavigator.current
        var selectedRoute = remember { mutableStateOf(BottomNavItem.Home.route) }
        val viewModel: HomeViewModel = koinViewModel()
        var fabExpanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEDE7F6)) // Light purple background
                    .padding(bottom = 80.dp), // Add padding for the navbar
                contentAlignment = Alignment.Center
            ) {
                when (selectedRoute.value) {
                    BottomNavItem.Home.route -> HomeScreen(viewModel,selectedRoute)
                    BottomNavItem.Transaction.route -> TransactionScreen(viewModel)
                    BottomNavItem.Budget.route -> BudgetScreen()
                    BottomNavItem.Profile.route -> ProfileScreen()
                }
            }
            // Bottom navigation with FAB
            CustomBottomNavigation(
                modifier = Modifier.align(Alignment.BottomCenter),
                navItems = BottomNavItem.items,
                selectedIndex = selectedRoute,
                onNavItemClick = { index ->
                    if (index != BottomNavItem.Add.route) { // Not the FAB placeholder
                        selectedRoute.value = index
                        fabExpanded = false
                    }
                },
                fabExpanded = fabExpanded,
                onFabClick = { fabExpanded = !fabExpanded },
                onFabActionClick = { action ->
                    println("FAB action clicked: $action")
                    selectedRoute.value = BottomNavItem.Transaction.route
                    fabExpanded = false
                    navigateToScreen(navigator, ScreenRoute.TransactionEntry,action)
//                    when (action) {
//                        TransactionType.INCOME.title -> {
//
//                        }
//                        TransactionType.EXPENSE.title -> {
//                            selectedRoute.value = BottomNavItem.Transaction.route
//                            fabExpanded = false
//                            navigateToScreen(navigator, ScreenRoute.TransactionEntry,action)
//                        }
//                        TransactionType.TRANSFER.title -> {
//                            selectedRoute.value = BottomNavItem.Transaction.route
//                            fabExpanded = false
//                            navigateToScreen(navigator, ScreenRoute.TransactionEntry,TransactionType.TRANSFER.title)
//                        }
//                    }
                }
            )
        }
    }
}
