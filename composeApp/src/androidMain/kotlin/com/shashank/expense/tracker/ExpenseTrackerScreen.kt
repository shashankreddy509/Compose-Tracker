package com.shashank.expense.tracker

import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.animation.core.*
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.draw.rotate

@Preview(showBackground = true)
@Composable
fun ExpandableActionButtonPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEDE7F6)),
            contentAlignment = Alignment.Center
        ) {
            ExpandableActionButton()
        }
    }
}

@Composable
fun ExpandableActionButton() {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "rotationAnimation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Food button (red)
        AnimatedVisibility(
            expanded = expanded,
            position = Offset(-80f, -80f),
            backgroundColor = Color(0xFFE74C3C)
        ) {
            // Food icon would go here
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }

        // Expense button (green)
        AnimatedVisibility(
            expanded = expanded,
            position = Offset(0f, -100f),
            backgroundColor = Color(0xFF27AE60)
        ) {
            // Download icon would go here
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Add"
            )
        }

        // Income button (blue)
        AnimatedVisibility(
            expanded = expanded,
            position = Offset(80f, -80f),
            backgroundColor = Color(0xFF3498DB)
        ) {
            // Upload icon would go here
            IconButton(
                onClick = { /* Handle click */ },
                content = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Add"
                    )
                }
            )
        }

        // Main button
        FloatingActionButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(56.dp),
            containerColor = Color(0xFF6C50F2)
        ) {
            Icon(
                imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (expanded) "Close" else "Open",
                tint = Color.White,
                modifier = Modifier.rotate(rotationAngle)
            )
        }
    }
}

@Composable
fun AnimatedVisibility(
    expanded: Boolean,
    position: Offset,
    backgroundColor: Color,
    content: @Composable () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "scaleAnimation"
    )
    val alpha by animateFloatAsState(
        targetValue = if (expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "alphaAnimation"
    )

    Box(
        modifier = Modifier
            .offset(position.x.dp, position.y.dp)
            .scale(scale)
            .alpha(alpha)
            .size(54.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

data class Offset(val x: Float, val y: Float)