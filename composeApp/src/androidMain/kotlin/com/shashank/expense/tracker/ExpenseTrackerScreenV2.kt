package com.shashank.expense.tracker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ExpenseTrackerScreen() {
    val navItems = listOf(
        NavItem("Home", Icons.Outlined.Home, Color(0xFF5E35B1)),
        NavItem("Transaction", Icons.Outlined.Star, Color(0xFF5E35B1)),
        NavItem("", Icons.Default.Add, Color.Transparent), // Placeholder for FAB
        NavItem("Budget", Icons.Outlined.AccountBox, Color(0xFF5E35B1)),
        NavItem("Profile", Icons.Outlined.Person, Color(0xFF5E35B1))
    )

    var selectedNavIndex by remember { mutableStateOf(0) }
    var fabExpanded by remember { mutableStateOf(false) }

    @Composable
    fun buildTheUI(text: String) {
        LazyColumn {
            items(26) {
                Column {
                    Text(
                        text = "$text - ${it + 1}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6)) // Light purple background
                .padding(bottom = 80.dp), // Add padding for the navbar
            contentAlignment = Alignment.Center
        ) {
            // Your main app content would go here
            when(selectedNavIndex) {
                0 -> buildTheUI("Home Screen")
                1 -> buildTheUI("Transaction Screen")
                3 -> buildTheUI("Budget Screen")
                4 -> buildTheUI("Profile Screen")
            }
//            LazyColumn {
//                items(26) {
//                    Column {
//                        Text(
//                            text = "Expense Tracker Content - ${it + 1}",
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                        Spacer(modifier = Modifier.height(16.dp))
//                    }
//                }
//            }
        }

        // Bottom navigation with FAB
        CustomBottomNavigation(
            modifier = Modifier.align(Alignment.BottomCenter),
            navItems = navItems,
            selectedIndex = selectedNavIndex,
            onNavItemClick = { index ->
                if (index != 2) { // Not the FAB placeholder
                    selectedNavIndex = index
                    fabExpanded = false
                }
            },
            fabExpanded = fabExpanded,
            onFabClick = { fabExpanded = !fabExpanded },
            onFabActionClick = { action ->
                println("FAB action clicked: $action")
                fabExpanded = false
            }
        )
    }
}

@Composable
fun CustomBottomNavigation(
    modifier: Modifier = Modifier,
    navItems: List<NavItem>,
    selectedIndex: Int,
    onNavItemClick: (Int) -> Unit,
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
                            isSelected = selectedIndex == index,
                            onClick = { onNavItemClick(index) }
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

@Composable
fun NavItemButton(
    item: NavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            tint = if (isSelected) item.activeColor else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        if (item.label.isNotEmpty()) {
            Text(
                text = item.label,
                color = if (isSelected) item.activeColor else Color.Gray,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FabActionButton(
    action: FabAction,
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        label = "fabActionScale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        label = "fabActionAlpha"
    )

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .alpha(alpha)
            .size(48.dp),
        containerColor = action.backgroundColor
    ) {
        Icon(
            imageVector = action.icon,
            contentDescription = action.label,
            tint = Color.White
        )
    }
}

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val activeColor: Color
)

data class FabAction(
    val label: String,
    val icon: ImageVector,
    val backgroundColor: Color,
    val offset: Offset
)

//data class Offset(val x: Float, val y: Float)

//@Composable
//private fun clickable(onClick: () -> Unit) = this.then(
//    Modifier.fillMaxSize().clickable(onClick = onClick)
//)

@Preview(showBackground = true)
@Composable
fun ExpenseTrackerScreenPreview() {
    MaterialTheme {
        ExpenseTrackerScreen()
    }
}