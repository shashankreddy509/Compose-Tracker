package com.shashank.expense.tracker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableMenu(
    modifier: Modifier = Modifier,
    mainButtonColor: Color = Color(0xFF6C50F2), // Purple color from the image
    items: List<ExpandableMenuItem> = emptyList()
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "rotationAnimation"
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Display menu items when expanded
        if (expanded) {
            items.forEachIndexed { index, item ->
                val angle = 360f / items.size * index - 90 // Calculate angle for positioning
                val radius = 80f // Distance from center - reduced to match the image

                // Calculate position using polar coordinates
                val x = (radius * kotlin.math.cos(Math.toRadians(angle.toDouble()))).toFloat()
                val y = (radius * kotlin.math.sin(Math.toRadians(angle.toDouble()))).toFloat()

                FloatingActionButton(
                    onClick = {
                        item.onClick()
                        expanded = false
                    },
                    modifier = Modifier
                        .size(48.dp) // Slightly smaller size to match image
                        .offset(x.dp, y.dp)
                        .alpha(animateFloatAsState(targetValue = if (expanded) 1f else 0f, label = "alphaAnimation").value)
                        .scale(animateFloatAsState(targetValue = if (expanded) 1f else 0f, label = "scaleAnimation").value),
                    containerColor = item.backgroundColor
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                        tint = Color.White
                    )
                }
            }
        }

        // Main button
        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = mainButtonColor,
            modifier = Modifier.size(56.dp) // Adjusted size to match image
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = if (expanded) "Close menu" else "Open menu",
                tint = Color.White,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }
}

data class ExpandableMenuItem(
    val icon: ImageVector,
    val contentDescription: String,
    val backgroundColor: Color,
    val onClick: () -> Unit
)

@Preview(showBackground = true)
@Composable
fun ExpandableMenuPreview() {
    val items = listOf(
        ExpandableMenuItem(
            icon = Icons.Filled.Add,
            contentDescription = "Food",
            backgroundColor = Color(0xFFE74C3C), // Red
            onClick = {}
        ),
        ExpandableMenuItem(
            icon = Icons.Filled.Close,
            contentDescription = "Expense",
            backgroundColor = Color(0xFF27AE60), // Green
            onClick = {}
        ),
        ExpandableMenuItem(
            icon = Icons.Filled.Home,
            contentDescription = "Income",
            backgroundColor = Color(0xFF3498DB), // Blue
            onClick = {}
        )
    )

    Box(
        modifier = Modifier
            .size(300.dp)
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        ExpandableMenu(items = items)
    }
}