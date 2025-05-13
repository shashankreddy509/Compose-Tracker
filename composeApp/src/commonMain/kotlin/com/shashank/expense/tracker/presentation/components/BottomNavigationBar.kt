package com.shashank.expense.tracker.presentation.components

 import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        BottomNavItem.items.forEach { item ->
            if (item is BottomNavItem.Add) {
                FloatingActionButton(
                    onClick = { onItemSelected(item) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(painter = painterResource(item.icon), contentDescription = null)
                }
            } else {
                NavigationBarItem(
                    selected = selectedRoute == item.route,
                    onClick = { onItemSelected(item) },
                    icon = { Icon(painterResource(item.icon), contentDescription = item.label) },
                    label = { androidx.compose.material3.Text(item.label) }
                )
            }
        }
    }
} 