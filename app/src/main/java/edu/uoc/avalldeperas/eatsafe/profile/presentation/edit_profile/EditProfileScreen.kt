package edu.uoc.avalldeperas.eatsafe.profile.presentation.edit_profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.auth.composables.AppTextField
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_BACK_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_EMAIL
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_FULL_NAME
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LOGOUT_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.USER_LOCATION_TEXT_FIELD
import edu.uoc.avalldeperas.eatsafe.common.composables.CenteredCircularProgressIndicator
import edu.uoc.avalldeperas.eatsafe.profile.composables.AllergyButton
import edu.uoc.avalldeperas.eatsafe.profile.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun EditProfileScreen(
    backToProfile: () -> Unit,
    onLogout: () -> Unit,
    context: Context = LocalContext.current,
    editProfileViewModel: EditProfileViewModel = hiltViewModel()
) {
    val uiState by editProfileViewModel.uiState.collectAsStateWithLifecycle()

    EditProfileContent(
        uiState = uiState,
        backToProfile = backToProfile,
        onLogout = { editProfileViewModel.onLogoutClick(onLogout) },
        onAllergyClick = editProfileViewModel::onAllergyClick,
        onUpdateDisplayName = editProfileViewModel::updateDisplayName,
        onUpdateCity = editProfileViewModel::updateCurrentCity,
        onSave = { editProfileViewModel.onSaveEdit(context) }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditProfileContent(
    uiState: EditProfileState,
    backToProfile: () -> Unit,
    onLogout: () -> Unit,
    onAllergyClick: (Intolerance) -> Unit,
    onUpdateDisplayName: (String) -> Unit,
    onUpdateCity: (String) -> Unit,
    onSave: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.edit_profile_header),
                        color = DARK_GREEN,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { backToProfile() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = EDIT_PROFILE_BACK_ICON,
                            tint = DARK_GREEN
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onLogout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = LOGOUT_ICON,
                            tint = MAIN_GREEN
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_account),
                contentDescription = EDIT_PROFILE_IMAGE,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.padding(top = 24.dp))
            SectionHeader(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.intolerances)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            FlowRow(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Intolerance().intolerances().forEach {
                    AllergyButton(
                        icon = it.icon,
                        text = it.label,
                        onClick = { onAllergyClick(it) },
                        enabled = uiState.intolerances.contains(it.label)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(top = 24.dp))
            SectionHeader(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.personal_details_header)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            AppTextField(
                value = uiState.email,
                onValueChange = {},
                leadingIcon = Icons.Filled.Email,
                contentDescription = EDIT_PROFILE_EMAIL,
                label = R.string.email,
                enabled = false
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            AppTextField(
                value = uiState.displayName,
                onValueChange = { onUpdateDisplayName(it) },
                leadingIcon = Icons.Filled.Face,
                contentDescription = EDIT_PROFILE_FULL_NAME,
                label = R.string.user_full_name
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            AppTextField(
                value = uiState.currentCity,
                onValueChange = { onUpdateCity(it) },
                leadingIcon = Icons.Filled.LocationOn,
                contentDescription = USER_LOCATION_TEXT_FIELD,
                label = R.string.current_city
            )
            Spacer(modifier = Modifier.padding(top = 32.dp))

            if (uiState.isLoading) {
                CenteredCircularProgressIndicator()
            } else {
                Button(
                    onClick = { onSave() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MAIN_GREEN,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(R.string.save), fontSize = 16.sp)
                }
                Spacer(modifier = Modifier.padding(top = 32.dp))
            }
        }
    }
}

@Composable
fun SectionHeader(modifier: Modifier, text: String) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EditProfileContentPreview() {
    val state = EditProfileState(
        intolerances = mutableListOf("Gluten", "Lactose"),
        email = "anemail@gmail.com",
        currentCity = "A city name",
        displayName = "A display name",
        isLoading = true
    )
    EditProfileContent(state, {}, {}, {}, {}, {}, {})
}