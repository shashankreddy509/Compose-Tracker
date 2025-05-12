package com.shashank.expense.tracker.presentation.screens.dashboard.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.ExpenseModel
import com.shashank.expense.tracker.utils.CategoryUtils
import com.shashank.expense.tracker.utils.DateTimeUtils
import org.jetbrains.compose.resources.painterResource

@Composable
fun TransactionItem(expense: ExpenseModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category Icon with background
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color(0x14000000),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(CategoryUtils.getCategoryIcon(expense.category)),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = CategoryUtils.getCategoryColor(expense.category)
                )
            }

            Column {
                Text(
                    text = expense.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = expense.category,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF91919F)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${if (expense.type == "income") "+" else "-"} $${expense.amount}",
                style = MaterialTheme.typography.titleMedium,
                color = if (expense.type == "income") Color(0xFF00A86B) else Color(0xFFFD3C4A),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = DateTimeUtils.formatTime(expense.date),
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF91919F)
            )
        }
    }
}