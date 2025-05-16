package com.shashank.expense.tracker.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.core.navigation.navigateToScreen
import com.shashank.expense.tracker.domain.model.AuthEvent
import com.shashank.expense.tracker.domain.model.AuthUiState
import com.shashank.expense.tracker.presentation.viewmodel.AuthViewModel
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_google
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@ExperimentalResourceApi
@ExperimentalMaterial3Api
class LoginScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Login.toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: AuthViewModel = koinViewModel()
        val scope = rememberCoroutineScope()
        Dispatchers.IO

        // Collect auth events
        LaunchedEffect(Unit) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AuthEvent.LoginSuccess -> {
                        navigateToScreen(navigator, ScreenRoute.Dashboard)
                    }
                    is AuthEvent.Error -> {
                        // Error is already handled in the UI state
                    }
                    else -> {}
                }
            }
        }

        LoginScreen(
            uiState = viewModel.uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = viewModel::login,
            onGoogleLoginClick = { /* TODO: Implement Google login */ },
            onRegisterClick = { navigateToScreen(navigator, ScreenRoute.Register) },
            onBackClick = { navigator?.pop() }
        )
    }

    @Composable
    fun LoginScreen(
        uiState: AuthUiState,
        onEmailChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onLoginClick: () -> Unit,
        onGoogleLoginClick: () -> Unit,
        onRegisterClick: () -> Unit,
        onBackClick: () -> Unit
    ) {
        var passwordVisible by remember { mutableStateOf(false) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Login") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = onEmailChange,
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (uiState.emailError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (uiState.emailError != null) Color.Red else Color(0xFF6B4EFF)
                        ),
                        isError = uiState.emailError != null
                    )
                    if (uiState.emailError != null) {
                        Text(
                            text = uiState.emailError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = uiState.password,
                        onValueChange = onPasswordChange,
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Text(
                                    if (passwordVisible) "Hide" else "Show",
                                    color = Color(0xFF6B4EFF),
                                    fontSize = 12.sp
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (uiState.passwordError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (uiState.passwordError != null) Color.Red else Color(0xFF6B4EFF)
                        ),
                        isError = uiState.passwordError != null
                    )
                    if (uiState.passwordError != null) {
                        Text(
                            text = uiState.passwordError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                uiState.authError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B4EFF)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            "Login",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Text(
                    "Or with",
                    color = Color.Gray
                )

                OutlinedButton(
                    onClick = onGoogleLoginClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.Black
                    )
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_google),
                        contentDescription = "Google logo",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Login with Google",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account? ",
                        color = Color.Gray
                    )
                    TextButton(onClick = onRegisterClick) {
                        Text(
                            "Sign Up",
                            color = Color(0xFF6B4EFF),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
} 