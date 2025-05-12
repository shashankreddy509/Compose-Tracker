package com.shashank.expense.tracker.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavigationBar(
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        BottomNavItem.items.forEach { item ->
            if (item is BottomNavItem.Add) {
                Spacer(Modifier.weight(1f, true))
                FloatingActionButton(
                    onClick = { onItemSelected(item) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(painter = painterResource(item.icon), contentDescription = null)
                }
                Spacer(Modifier.weight(1f, true))
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