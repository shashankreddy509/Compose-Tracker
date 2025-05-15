package com.shashank.expense.tracker.presentation.screens.dashboard.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import com.shashank.expense.tracker.presentation.screens.dashboard.transactions.components.TransactionType
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_attachment
import org.jetbrains.compose.resources.painterResource


class TransactionEntryScreen(private val transactionType: TransactionType) : Screen,
    ScreenProvider {
    @Composable
    override fun Content() {
        TransactionEntryScreen(transactionType = transactionType)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEntryScreen(
    transactionType: TransactionType,
    onBackClick: () -> Unit = {},
    onContinueClick: (amount: String, category: String, description: String, wallet: String, repeat: Boolean) -> Unit = { _, _, _, _, _ -> }
) {
    var amountText by remember { mutableStateOf("0") }
    var categoryText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var walletText by remember { mutableStateOf("") }
    var isRepeatEnabled by remember { mutableStateOf(false) }

    val backgroundColor = transactionType.color

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Top app bar with back button and title
        TopAppBar(
            title = {
                Text(
                    text = transactionType.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backgroundColor,
                titleContentColor = Color.White
            )
        )

        // How much section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = "How much?",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$",
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )

                BasicTextField(
                    value = amountText,
                    onValueChange = {
                        // Only allow numeric input
                        if (it.isEmpty() || it.all { char -> char.isDigit() || char == '.' }) {
                            amountText = it
                        }
                    },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.White,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 4.dp)
                )
            }
        }

        // Form content (white card)
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Category selector
                FormField(
                    title = "Category",
                    value = categoryText,
                    onValueChange = { categoryText = it },
                    showDropdown = true,
                    onClick = { /* Show category picker */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Description field
                FormField(
                    title = "Description",
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    showDropdown = false
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Wallet selector
                FormField(
                    title = "Wallet",
                    value = walletText,
                    onValueChange = { walletText = it },
                    showDropdown = true,
                    onClick = { /* Show wallet picker */ }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Add attachment button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* Add attachment action */ }
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_attachment),
                        contentDescription = "Add attachment",
                        tint = Color.Gray
                    )

                    Text(
                        text = "Add attachment",
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Repeat transaction switch
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Repeat",
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Repeat transaction",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    Switch(
                        checked = isRepeatEnabled,
                        onCheckedChange = { isRepeatEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Continue button
                Button(
                    onClick = {
                        onContinueClick(
                            amountText,
                            categoryText,
                            descriptionText,
                            walletText,
                            isRepeatEnabled
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C50F2) // Purple button
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun FormField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    showDropdown: Boolean = false,
    onClick: () -> Unit = {}
) {
    val modifier = if (showDropdown) {
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    } else {
        Modifier.fillMaxWidth()
    }

    Column(modifier = modifier) {
        Text(
            text = title,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showDropdown) {
                Text(
                    text = if (value.isEmpty()) title else value,
                    fontSize = 16.sp,
                    color = if (value.isEmpty()) Color.Gray else Color.Black
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Select $title",
                    tint = Color.Gray
                )
            } else {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = androidx.compose.ui.text.TextStyle(
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Divider()
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun ExpenseEntryPreview() {
//    MaterialTheme {
//        TransactionEntryScreen(transactionType = TransactionType.EXPENSE)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun IncomeEntryPreview() {
//    MaterialTheme {
//        TransactionEntryScreen(transactionType = TransactionType.INCOME)
//    }
//}