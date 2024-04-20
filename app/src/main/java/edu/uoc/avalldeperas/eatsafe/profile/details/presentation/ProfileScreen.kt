package edu.uoc.avalldeperas.eatsafe.profile.details.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.profile.composables.AllergyButton
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.LIGHT_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN
import java.time.ZoneId

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    toEditProfile: () -> Unit,
    toLogin: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val email by profileViewModel.email.collectAsStateWithLifecycle()
    val displayName by profileViewModel.displayName.collectAsStateWithLifecycle()
    val user by profileViewModel.user.collectAsStateWithLifecycle()
    val date = user.dateJoined.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

    Column(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(LIGHT_GREEN)
                .padding(vertical = 16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.default_account),
                    contentDescription = PROFILE_IMAGE,
                    modifier = Modifier.size(70.dp)
                )
                Column() {
                    Text(
                        text = "Hello $displayName!",
                        color = DARK_GREEN,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Person, contentDescription = ""
                        )
                        Text(
                            text = "User since ${date.year}",
                            color = DARK_GREEN,
                            fontSize = 12.sp,
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                        Text(
                            text = user.currentCity,
                            color = DARK_GREEN,
                            fontSize = 10.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                IconButton(
                    onClick = { toEditProfile() },
                    modifier = Modifier.align(Alignment.Top)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit, contentDescription = EDIT_PROFILE_ICON
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.intolerances),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                FlowRow(Modifier.fillMaxWidth()) {
                    Intolerance().intolerances().forEach {
                        AllergyButton(
                            imageVector = it.icon,
                            text = it.label,
                            enabled = user.intolerances.contains(it.label)
                        )
                    }
                }
            }
            Text(
                text = email,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { profileViewModel.onLogoutClick(toLogin) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, contentColor = MAIN_GREEN
                ),
                border = BorderStroke(1.dp, MAIN_GREEN)
            ) {
                Text(text = stringResource(R.string.logout_button), fontSize = 16.sp)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen({}, {})
}