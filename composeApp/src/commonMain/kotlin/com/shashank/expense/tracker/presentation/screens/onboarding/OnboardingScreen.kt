package com.shashank.expense.tracker.presentation.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.core.navigation.navigateToScreen
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_onboarding_1
import expense_tracker_compose.composeapp.generated.resources.ic_onboarding_2
import expense_tracker_compose.composeapp.generated.resources.ic_onboarding_3
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi

class OnboardingScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Onboarding.toString()

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
        ExperimentalResourceApi::class
    )
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        OnboardingScreen(
            onNavigateToScreen = { route ->
                navigateToScreen(navigator, route)
            }
        )
    }

    @OptIn(ExperimentalResourceApi::class)
    @ExperimentalFoundationApi
    @Composable
    fun OnboardingScreen(
        onNavigateToScreen: (ScreenRoute) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { 3 }
        )
        val scope = rememberCoroutineScope()

        LaunchedEffect(pagerState.currentPage) {
            // If we're on the last page and user swipes back, we want to stay on the last page
            if (pagerState.currentPage == 2) {
                pagerState.scrollToPage(2)
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFCFCFC))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // ViewPager
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPage(
                    page = page,
                    title = when (page) {
                        0 -> "Gain total control\nof your money"
                        1 -> "Know where your\nmoney goes"
                        else -> "Planning ahead"
                    },
                    description = when (page) {
                        0 -> "Become your own money manager\nand make every cent count"
                        1 -> "Track your transaction easily,\nwith categories and financial report"
                        else -> "Setup your budget for each category\nso you in control"
                    },
                    image = when (page) {
                        0 -> Res.drawable.ic_onboarding_1
                        1 -> Res.drawable.ic_onboarding_2
                        else -> Res.drawable.ic_onboarding_3
                    }
                )
            }

            // Page Indicator
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { iteration ->
                    val color = if (pagerState.currentPage == iteration) {
                        Color(0xFF6B4EFF)
                    } else {
                        Color(0xFFE5E1FF)
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }

            // Buttons
            if (pagerState.currentPage == 2) {
                // Show Sign Up and Login buttons on last page
                Button(
                    onClick = { onNavigateToScreen(ScreenRoute.Register) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B4EFF)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Sign Up",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onNavigateToScreen(ScreenRoute.Login) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE5E1FF),
                        contentColor = Color(0xFF6B4EFF)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Login",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            } else {
                // Show Next button on first two pages
                Button(
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B4EFF)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        "Next",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun OnboardingPage(
        page: Int,
        title: String,
        description: String,
        image: DrawableResource
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = "Onboarding image $page",
                modifier = Modifier
                    .size(280.dp)
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 35.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                lineHeight = 24.sp
            )
        }
    }
}