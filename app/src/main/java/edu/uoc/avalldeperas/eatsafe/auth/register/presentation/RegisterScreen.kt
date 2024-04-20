package edu.uoc.avalldeperas.eatsafe.auth.register.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.auth.composables.AppTextField
import edu.uoc.avalldeperas.eatsafe.auth.composables.AuthFooterText
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CONFIRM_PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.CURRENT_CITY_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LOGIN_LINK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun RegisterScreen(
    toLogin: () -> Unit, toExplore: () -> Unit, registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val email by registerViewModel.email.collectAsStateWithLifecycle()
    val password by registerViewModel.password.collectAsStateWithLifecycle()
    val confirmPassword by registerViewModel.confirmPassword.collectAsStateWithLifecycle()
    val currentCity by registerViewModel.currentCity.collectAsStateWithLifecycle()

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eatsafe_logo),
            contentDescription = ContentDescriptionConstants.EATSAFE_LOGO,
            Modifier.size(70.dp)
        )
        Spacer(modifier = Modifier.padding(vertical = 12.dp))
        Text(
            text = stringResource(R.string.welcome_eatsafe),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        AppTextField(
            value = email,
            onValueChange = { registerViewModel.updateEmail(it) },
            leadingIcon = Icons.Filled.Email,
            contentDescription = EMAIL_TEXT_FIELD,
            label = R.string.email
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        AppTextField(
            value = password,
            onValueChange = { registerViewModel.updatePassword(it) },
            leadingIcon = Icons.Filled.Lock,
            contentDescription = PASSWORD_TEXT_FIELD,
            label = R.string.password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        AppTextField(
            value = confirmPassword,
            onValueChange = { registerViewModel.updateConfirmPassword(it) },
            leadingIcon = Icons.Filled.Lock,
            contentDescription = CONFIRM_PASSWORD_TEXT_FIELD,
            label = R.string.confirm_password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        AppTextField(
            value = currentCity,
            onValueChange = { registerViewModel.updateCurrentCity(it) },
            leadingIcon = Icons.Filled.LocationOn,
            contentDescription = CURRENT_CITY_TEXT_FIELD,
            label = R.string.current_city
        )
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        Button(
            onClick = { registerViewModel.onRegisterClick(toExplore, context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MAIN_GREEN, contentColor = Color.White
            ),
        ) {
            Text(text = stringResource(R.string.signup_button), fontSize = 16.sp)
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        AuthFooterText(
            textRes = R.string.already_account,
            onClick = { toLogin() },
            contentDescription = LOGIN_LINK
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen({}, {})
}