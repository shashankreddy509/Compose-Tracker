package com.shashank.expense.tracker.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.shashank.expense.tracker.core.navigation.ScreenRoute
import com.shashank.expense.tracker.core.navigation.navigateToScreen
import expense_tracker_compose.composeapp.generated.resources.Res
import expense_tracker_compose.composeapp.generated.resources.ic_google
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalResourceApi
@ExperimentalMaterial3Api
class RegisterScreen : Screen, ScreenProvider {
    override val key: String = ScreenRoute.Register.toString()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        RegisterScreen({ route ->
            navigateToScreen(navigator, route)
        }, navigator)
    }

    @Composable
    fun RegisterScreen(
        onNavigateToScreen: (ScreenRoute) -> Unit,
        navigator: Navigator?
    ) {
//        val viewModel: AuthViewModel = koinViewModel()
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPasswordVisible by remember { mutableStateOf(false) }

        var nameError by remember { mutableStateOf<String?>(null) }
        var emailError by remember { mutableStateOf<String?>(null) }
        var passwordError by remember { mutableStateOf<String?>(null) }
        var confirmPasswordError by remember { mutableStateOf<String?>(null) }
        var isLoading by remember { mutableStateOf(false) }
        var authError by remember { mutableStateOf<String?>(null) }

        fun validateName(name: String): Boolean {
            return if (name.trim().isEmpty()) {
                nameError = "Name is required"
                false
            } else {
                nameError = null
                true
            }
        }

        fun validateEmail(email: String): Boolean {
            return if (email.isEmpty()) {
                emailError = "Email is required"
                false
            } else if (!email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"))) {
                emailError = "Please enter a valid email"
                false
            } else {
                emailError = null
                true
            }
        }

        fun validatePassword(password: String): Boolean {
            return if (password.isEmpty()) {
                passwordError = "Password is required"
                false
            } else if (password.length < 6) {
                passwordError = "Password must be at least 6 characters"
                false
            } else {
                passwordError = null
                true
            }
        }

        fun validateConfirmPassword(confirmPassword: String): Boolean {
            return if (confirmPassword.isEmpty()) {
                confirmPasswordError = "Confirm password is required"
                false
            } else if (confirmPassword.length < 6) {
                confirmPasswordError = "Password must be at least 6 characters"
                false
            } else if (confirmPassword != password) {
                confirmPasswordError = "Passwords do not match"
                false
            } else {
                confirmPasswordError = null
                true
            }
        }

        fun validateAndSignUp() {
            val isNameValid = validateName(name)
            val isEmailValid = validateEmail(email)
            val isPasswordValid = validatePassword(password)
            val isConfirmPasswordValid = validateConfirmPassword(confirmPassword)

            if (isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid) {
//                viewModel.signUp(email, password)
                onNavigateToScreen(ScreenRoute.Dashboard)
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sign Up") },
                    navigationIcon = {
                        IconButton(onClick = { navigator?.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFCFCFC))
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Create Account",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(vertical = 32.dp)
                )

                // Name field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { newValue ->
                            name = newValue
                            if (nameError != null) validateName(newValue)
                        },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp
                        ),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (nameError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (nameError != null) Color.Red else Color(
                                0xFF6B4EFF
                            )
                        ),
                        isError = nameError != null
                    )
                    if (nameError != null) {
                        Text(
                            text = nameError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                // Email field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = { newValue ->
                            email = newValue
                            if (emailError != null) validateEmail(newValue)
                        },
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
                            unfocusedBorderColor = if (emailError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (emailError != null) Color.Red else Color(
                                0xFF6B4EFF
                            )
                        ),
                        isError = emailError != null
                    )
                    if (emailError != null) {
                        Text(
                            text = emailError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                // Password field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = password,
                        onValueChange = { newValue ->
                            password = newValue
                            if (passwordError != null) validatePassword(newValue)
                            if (confirmPassword.isNotEmpty()) validateConfirmPassword(
                                confirmPassword
                            )
                        },
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
                            unfocusedBorderColor = if (passwordError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (passwordError != null) Color.Red else Color(
                                0xFF6B4EFF
                            )
                        ),
                        isError = passwordError != null
                    )
                    if (passwordError != null) {
                        Text(
                            text = passwordError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                // Confirm Password field
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { newValue ->
                            confirmPassword = newValue
                            if (confirmPasswordError != null) validateConfirmPassword(newValue)
                        },
                        label = { Text("Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp
                        ),
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                confirmPasswordVisible = !confirmPasswordVisible
                            }) {
                                Text(
                                    if (confirmPasswordVisible) "Hide" else "Show",
                                    color = Color(0xFF6B4EFF)
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = if (confirmPasswordError != null) Color.Red else Color.LightGray,
                            focusedBorderColor = if (confirmPasswordError != null) Color.Red else Color(
                                0xFF6B4EFF
                            )
                        ),
                        isError = confirmPasswordError != null
                    )
                    if (confirmPasswordError != null) {
                        Text(
                            text = confirmPasswordError!!,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                        )
                    }
                }

                authError?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { validateAndSignUp() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6B4EFF)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(
                            "Sign Up",
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
                    onClick = { },
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
                        "Sign up with Google",
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
                        "Already have an account? ",
                        color = Color.Gray
                    )
                    TextButton(onClick = { onNavigateToScreen(ScreenRoute.Login) }) {
                        Text(
                            "Login",
                            color = Color(0xFF6B4EFF),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

    }
} 