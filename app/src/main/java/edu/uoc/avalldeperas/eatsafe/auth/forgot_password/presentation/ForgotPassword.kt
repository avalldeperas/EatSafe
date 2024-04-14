package edu.uoc.avalldeperas.eatsafe.auth.forgot_password.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.auth.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.auth.common.ContentDescriptionConstants.FORGOT_BACK
import edu.uoc.avalldeperas.eatsafe.auth.common.ContentDescriptionConstants.FORGOT_EMAIL_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.auth.composables.AuthTextField
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordScreen(
    navigateBack: () -> Unit, forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val email by forgotPasswordViewModel.email.collectAsStateWithLifecycle()
    val message = stringResource(R.string.forgot_password_email_sent)

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = FORGOT_BACK
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.eatsafe_logo),
                contentDescription = ContentDescriptionConstants.EATSAFE_LOGO,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 12.dp))
            Text(
                text = stringResource(R.string.forgot_password),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(vertical = 32.dp))
            Text(
                text = stringResource(R.string.forgot_password_info),
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(vertical = 24.dp))
            AuthTextField(
                value = email,
                onValueChange = { forgotPasswordViewModel.updateEmail(it) },
                leadingIcon = Icons.Filled.Email,
                contentDescription = FORGOT_EMAIL_TEXT_FIELD,
                label = R.string.email
            )
            Spacer(modifier = Modifier.padding(vertical = 32.dp))
            Button(
                onClick = {
                    forgotPasswordViewModel.onForgotClick {
                        scope.launch { snackbarHostState.showSnackbar(message) }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MAIN_GREEN, contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.send_email_button), fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen({})
}