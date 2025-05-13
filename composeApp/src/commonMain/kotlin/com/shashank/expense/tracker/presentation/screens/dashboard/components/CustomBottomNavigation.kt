package com.shashank.expense.tracker.presentation.screens.dashboard.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.shashank.expense.tracker.presentation.components.BottomNavItem

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    navItems: List<BottomNavItem>,
    selectedIndex: MutableState<String>,
    onNavItemClick: (String) -> Unit,
    fabExpanded: Boolean,
    onFabClick: () -> Unit,
    onFabActionClick: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        // FAB Actions (displayed when expanded)
        if (fabExpanded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f)
                    .background(Color.Transparent)
                    .zIndex(1f)
                    .clickable(onClick = onFabClick)
            )

            // Animated FAB menu items
            val fabActions = listOf(
                FabAction(
                    "Food",
                    Icons.Default.Home,
                    Color(0xFFE74C3C),
                    Offset(-60f, -100f)
                ),
                FabAction(
                    "Expense",
                    Icons.Default.Close,
                    Color(0xFF27AE60),
                    Offset(0f, -120f)
                ),
                FabAction(
                    "Income",
                    Icons.Default.PlayArrow,
                    Color(0xFF3498DB),
                    Offset(60f, -100f)
                )
            )

            fabActions.forEach { action ->
                FabActionButton(
                    action = action,
                    expanded = fabExpanded,
                    onClick = { onFabActionClick(action.label) },
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(x = action.offset.x.dp, y = action.offset.y.dp)
                        .zIndex(2f)
                )
            }
        }

        // Bottom Navigation Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    if (index == 2) { // Middle FAB placeholder
                        // Empty space for the FAB
                        Spacer(modifier = Modifier.width(64.dp))
                    } else {
                        // Regular nav item
                        NavItemButton(
                            item = item,
                            isSelected = selectedIndex.value == item.route,
                            onClick = { onNavItemClick(item.route) }
                        )
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onFabClick,
            modifier = Modifier
                .size(56.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-40).dp)
                .zIndex(3f),
            containerColor = Color(0xFF6C50F2) // Purple color
        ) {
            val rotationAngle by animateFloatAsState(
                targetValue = if (fabExpanded) 45f else 0f,
                label = "fabRotation"
            )
            Icon(
                imageVector = if (fabExpanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (fabExpanded) "Close menu" else "Open menu",
                tint = Color.White,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }
}