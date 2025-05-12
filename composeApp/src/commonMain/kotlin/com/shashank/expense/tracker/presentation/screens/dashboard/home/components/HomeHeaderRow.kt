package com.shashank.expense.tracker.presentation.screens.dashboard.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_notification
import expense_tracker_compose.composeapp.generated.resources.ic_user
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeHeaderRow(
    viewModel: HomeViewModel,
) {
    val selectedMonth by viewModel.selectedMonth.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            modifier = Modifier.size(40.dp),
            color = Color(0xFFF1F1F1)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_user),
                contentDescription = "Profile",
                modifier = Modifier.padding(8.dp),
                colorFilter = ColorFilter.tint(Color(0xFF7F3DFF))
            )
        }
        MonthSelector(
            selectedMonth = selectedMonth,
            onMonthSelected = { viewModel.selectMonth(selectedMonth) })
        IconButton(
            onClick = { /* TODO: Show notifications */ }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_notification),
                contentDescription = "Notifications",
                tint = Color(0xFF212224)
            )
        }
    }
}