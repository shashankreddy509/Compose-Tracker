package com.shashank.expense.tracker.presentation.screens.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shashank.expense.tracker.presentation.components.BottomNavItem
import org.jetbrains.compose.resources.painterResource

@Composable
fun NavItemButton(
    item: BottomNavItem,
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
            painter = painterResource( item.icon),
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