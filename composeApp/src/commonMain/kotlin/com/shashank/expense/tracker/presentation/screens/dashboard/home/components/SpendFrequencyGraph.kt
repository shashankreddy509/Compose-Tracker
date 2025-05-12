package com.shashank.expense.tracker.presentation.screens.dashboard.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shashank.expense.tracker.presentation.screens.dashboard.home.models.SpendingPoint
import com.shashank.expense.tracker.presentation.viewmodel.HomeViewModel
import com.shashank.expense.tracker.util.StringFormatter.formatCurrency
import com.shashank.expense.tracker.util.Utils.currencyFormater

@Composable
fun SpendFrequencyGraph(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onTimeFrameSelected: (String) -> Unit
) {
    val spendingData by viewModel.spendingData.collectAsState()
    val selectedTimeFrame by viewModel.selectedTimeFrame.collectAsState()
    var selectedPoint by remember { mutableStateOf<SpendingPoint?>(null) }

    Column(modifier = modifier) {
        // Value indicator
        selectedPoint?.let { point ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$${currencyFormater(point.value)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF7F3DFF),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = point.label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF91919F)
                )
            }
        } ?: run {
            // Show total when no point is selected
            val total = spendingData.sumOf { it.value.toDouble() }.toFloat()
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF91919F)
                )
                Text(
                    text = "$${currencyFormater(total)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF7F3DFF),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Graph
        if (spendingData.isNotEmpty()) {
            val points = spendingData.map { it.value }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures { offset ->
                                val width = size.width
                                val xStep = width / (points.size - 1)
                                val index = (offset.x / xStep).toInt().coerceIn(0, points.size - 1)
                                selectedPoint = spendingData.getOrNull(index)
                            }
                        }
                ) {
                    val width = size.width
                    val height = size.height
                    val maxPoint = points.maxOrNull() ?: 0f

                    val xStep = width / (points.size - 1)
                    val yStep = height / maxPoint

                    // Draw grid lines
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.3f),
                        start = Offset(0f, height / 2),
                        end = Offset(width, height / 2),
                        strokeWidth = 1.dp.toPx()
                    )

                    val path = Path()
                    val fillPath = Path()

                    points.forEachIndexed { index, point ->
                        val x = index * xStep
                        val y = height - (point * yStep)

                        if (index == 0) {
                            path.moveTo(x, y)
                            fillPath.moveTo(x, height)
                            fillPath.lineTo(x, y)
                        } else {
                            val prevX = (index - 1) * xStep
                            val prevY = height - (points[index - 1] * yStep)

                            val controlX1 = prevX + (x - prevX) / 2f
                            val controlX2 = prevX + (x - prevX) / 2f

                            path.cubicTo(
                                controlX1, prevY,
                                controlX2, y,
                                x, y
                            )

                            fillPath.cubicTo(
                                controlX1, prevY,
                                controlX2, y,
                                x, y
                            )
                        }
                    }

                    fillPath.lineTo(width, height)
                    fillPath.close()

                    drawPath(
                        path = fillPath,
                        color = Color(0xFF7F3DFF).copy(alpha = 0.2f)
                    )

                    drawPath(
                        path = path,
                        color = Color(0xFF7F3DFF),
                        style = Stroke(
                            width = 4.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    )

                    // Draw points and highlight selected point
                    points.forEachIndexed { index, point ->
                        val x = index * xStep
                        val y = height - (point * yStep)
                        val isSelected = selectedPoint == spendingData[index]

                        if (isSelected) {
                            drawCircle(
                                color = Color(0xFF7F3DFF).copy(alpha = 0.2f),
                                radius = 12.dp.toPx(),
                                center = Offset(x, y)
                            )
                        }

                        drawCircle(
                            color = Color.White,
                            radius = 6.dp.toPx(),
                            center = Offset(x, y)
                        )

                        drawCircle(
                            color = Color(0xFF7F3DFF),
                            radius = 4.dp.toPx(),
                            center = Offset(x, y)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Time Period Selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Today", "Week", "Month", "Year").forEach { period ->
                Surface(
                    shape = RoundedCornerShape(32.dp),
                    color = if (period == selectedTimeFrame) Color(0xFF7F3DFF) else Color.Transparent,
                    modifier = Modifier
                        .clickable { onTimeFrameSelected(period) }
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = period,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        color = if (period == selectedTimeFrame) Color.White else Color(0xFF91919F)
                    )
                }
            }
        }
    }
}