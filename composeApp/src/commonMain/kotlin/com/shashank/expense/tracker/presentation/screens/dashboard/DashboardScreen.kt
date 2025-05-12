package com.shashank.expense.tracker.presentation.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.presentation.components.BottomNavItem
import com.shashank.expense.tracker.presentation.components.BottomNavigationBar

class DashboardScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Dashboard.toString()

    @Composable
    override fun Content() {
        DashboardScreen()
    }

    @Composable
    fun DashboardScreen() {
        var selectedRoute by remember { mutableStateOf(BottomNavItem.Home.route) }

        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    selectedRoute = selectedRoute,
                    onItemSelected = { item -> selectedRoute = item.route }
                )
            }
        ) { innerPadding ->
            when (selectedRoute) {
                BottomNavItem.Home.route -> DashboardScreenContent(Modifier.padding(innerPadding))
                BottomNavItem.Transaction.route -> TransactionScreen()
                BottomNavItem.Budget.route -> BudgetScreen()
                BottomNavItem.Profile.route -> ProfileScreen()
                BottomNavItem.Add.route -> AddTransactionScreen()
            }
        }
    }
}




@Composable
fun DashboardScreenContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Row: Profile and Notification
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile image placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(16.dp))

        // Month Selector
        Text(
            text = "October",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(8.dp))

        // Account Balance
        Text(
            text = "Account Balance",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "$9400",
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        // Income and Expenses
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1ABC9C)),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Income", color = Color.White)
                    Text("$5000", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.width(16.dp))
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE74C3C)),
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Expenses", color = Color.White)
                    Text("$1200", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        // Spend Frequency (Placeholder)
        Text("Spend Frequency", style = MaterialTheme.typography.titleMedium)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFB39DDB).copy(alpha = 0.2f))
        ) {
            // TODO: Add chart here
        }

        Spacer(Modifier.height(16.dp))

        // Recent Transactions
        Text("Recent Transaction", style = MaterialTheme.typography.titleMedium)
        // TODO: Replace with a LazyColumn and real data
        Column {
            TransactionItem("Shopping", "- $120", "Buy some grocery", "10:00 AM", Color(0xFFFFC107))
            TransactionItem("Subscription", "- $80", "Disney+ Annual..", "03:30 PM", Color(0xFF9C27B0))
            TransactionItem("Food", "- $32", "Buy a ramen", "07:30 PM", Color(0xFFE57373))
        }
    }
}

@Composable
fun TransactionItem(
    title: String,
    amount: String,
    subtitle: String,
    time: String,
    iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(iconColor)
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, fontSize = 12.sp, color = Color.Gray)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(amount, color = Color.Red, fontWeight = FontWeight.Bold)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

// Placeholder screens for navigation
@Composable fun TransactionScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Transaction Screen") } }
@Composable fun AddTransactionScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Add Transaction Screen") } }
@Composable fun BudgetScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Budget Screen") } }
@Composable fun ProfileScreen() { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Profile Screen") } } 