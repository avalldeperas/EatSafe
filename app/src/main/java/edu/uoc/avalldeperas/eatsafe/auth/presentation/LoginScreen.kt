package edu.uoc.avalldeperas.eatsafe.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    toForgotPassword: () -> Unit,
    onSubmit: () -> Unit,
    toRegister: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = "Forgot password?", modifier = Modifier.clickable { toForgotPassword() })
        Button(onClick = { onSubmit() }) {
            Text(text = "Log in")
        }
        Text(
            text = "Don't have an account? Sign up",
            modifier = Modifier.clickable { toRegister() }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen({}, {}, {})
}