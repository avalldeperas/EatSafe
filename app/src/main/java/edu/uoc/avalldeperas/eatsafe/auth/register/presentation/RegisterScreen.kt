package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

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
fun RegisterScreen(toLogin: () -> Unit, onSubmit: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Button(onClick = onSubmit) {
            Text(text = "Submit")
        }
        Text(
            text = "Already have an account? Log In",
            modifier = Modifier.clickable { toLogin() }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen({}, {})
}