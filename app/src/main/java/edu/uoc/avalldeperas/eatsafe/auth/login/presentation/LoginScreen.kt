package edu.uoc.avalldeperas.eatsafe.auth.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.auth.composables.AppTextField
import edu.uoc.avalldeperas.eatsafe.auth.composables.AuthFooterText
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EATSAFE_LOGO
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FORGOT_PASSWORD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PASSWORD_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.REGISTER_LINK
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun LoginScreen(
    toForgotPassword: () -> Unit,
    onSubmit: () -> Unit,
    toRegister: () -> Unit,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.eatsafe_logo),
            contentDescription = EATSAFE_LOGO,
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
            value = loginState.email,
            onValueChange = loginViewModel::updateEmail,
            leadingIcon = Icons.Filled.Email,
            contentDescription = EMAIL_TEXT_FIELD,
            label = R.string.email
        )
        Spacer(modifier = Modifier.padding(vertical = 4.dp))
        AppTextField(
            value = loginState.password,
            onValueChange = loginViewModel::updatePassword,
            leadingIcon = Icons.Filled.Lock,
            contentDescription = PASSWORD_TEXT_FIELD,
            label = R.string.password,
            visualTransformation = if (loginState.isPasswordShown) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = Icons.Default.RemoveRedEye,
            onTrailingIconClick = loginViewModel::onToggleVisualTransformationPassword
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(
            text = stringResource(R.string.forgot_password),
            modifier = Modifier
                .clickable { toForgotPassword() }
                .semantics { this.contentDescription = FORGOT_PASSWORD }
                .align(Alignment.End)
                .padding(horizontal = 24.dp),
            color = MAIN_GREEN,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(vertical = 24.dp))
        if (loginState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { loginViewModel.onLoginClick(onSubmit, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MAIN_GREEN,
                    contentColor = Color.White
                ),
            ) {
                Text(text = stringResource(R.string.log_in), fontSize = 16.sp)
            }
        }
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        AuthFooterText(
            firstText = R.string.no_account_yet,
            secondText = R.string.sign_up,
            onClick = { toRegister() },
            contentDescription = REGISTER_LINK
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen({}, {}, {})
}