package edu.uoc.avalldeperas.eatsafe.profile.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import edu.uoc.avalldeperas.eatsafe.auth.domain.model.User
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.EDIT_PROFILE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PROFILE_REVIEW_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.composables.CenteredCircularProgressIndicatorWithText
import edu.uoc.avalldeperas.eatsafe.common.composables.EmptyListMessageIcon
import edu.uoc.avalldeperas.eatsafe.common.util.StringUtils
import edu.uoc.avalldeperas.eatsafe.explore.composables.AverageRatingSection
import edu.uoc.avalldeperas.eatsafe.explore.composables.SafetySectionWithNumber
import edu.uoc.avalldeperas.eatsafe.explore.presentation.detail_view.AppHorizontalDivider
import edu.uoc.avalldeperas.eatsafe.profile.composables.AllergyButton
import edu.uoc.avalldeperas.eatsafe.profile.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.LIGHT_GREEN
import java.time.ZoneId

@Composable
fun ProfileScreen(
    toEditProfile: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsStateWithLifecycle()

    ProfileContent(
        profileState = profileState,
        toEditProfile = toEditProfile,
        getProfileName = { profileViewModel.getDisplayName() }
    )
}

@Composable
fun ProfileContent(
    profileState: ProfileState,
    toEditProfile: () -> Unit,
    getProfileName: () -> String
) {
    if (profileState.isLoading) {
        CenteredCircularProgressIndicatorWithText()
    } else {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LIGHT_GREEN)
            ) {
                ProfileHeader(profileState, toEditProfile, getProfileName)
            }
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(Modifier.padding(16.dp)) {
                    IntolerancesSection(user = profileState.user)
                    Spacer(modifier = Modifier.padding(vertical = 12.dp))
                    ReviewsSection(reviews = profileState.reviews)
                }
            }
        }
    }
}

@Composable
fun ReviewsSection(reviews: List<Review>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.my_reviews_header),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(8.dp))
        if (reviews.isEmpty()) {
            EmptyListMessageIcon(R.string.no_reviews_yet)
        } else {
            reviews.forEach {
                MyReviewItem(it)
            }
        }
    }
}

@Composable
fun MyReviewItem(review: Review) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.restaurant_detail),
                contentDescription = PROFILE_REVIEW_PLACE_IMAGE + review.placeId,
                Modifier.size(70.dp)
            )
        }
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Text(
                    text = review.placeName,
                    Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = StringUtils.getParsedDate(review.date), fontSize = 12.sp)
            }
            Row {
                Row {
                    SafetySectionWithNumber(
                        modifier = Modifier.weight(0.7f),
                        averageSafety = review.safety.toDouble(),
                        fontSize = 12.sp
                    )
                    AverageRatingSection(
                        modifier = Modifier,
                        averageRating = review.rating.toDouble(),
                        fontSize = 12.sp
                    )
                }
            }
            Text(
                text = review.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )
        }
    }
    AppHorizontalDivider(top = 4.dp, bottom = 4.dp, color = Color.LightGray)
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IntolerancesSection(user: User) {
    Text(
        text = stringResource(R.string.intolerances),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.padding(4.dp))
    FlowRow(Modifier.fillMaxWidth()) {
        Intolerance().intolerances().forEach {
            AllergyButton(
                icon = it.icon,
                text = it.label,
                enabled = user.intolerances.contains(it.label)
            )
        }
    }
}

@Composable
fun ProfileHeader(
    profileState: ProfileState,
    onEditClick: () -> Unit,
    getDisplayName: () -> String
) {
    val date = profileState.user.dateJoined.toDate().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LIGHT_GREEN)
            .padding(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.default_account),
                contentDescription = PROFILE_IMAGE,
                modifier = Modifier
                    .size(70.dp)
                    .padding()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = "Hello ${getDisplayName()}!",
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
                        text = profileState.user.currentCity,
                        color = DARK_GREEN,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            IconButton(
                onClick = onEditClick,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit, contentDescription = EDIT_PROFILE_ICON
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileContentPreview() {
    val reviews = listOf(Review(placeName = "name", description = "A description"))
    ProfileContent(ProfileState(reviews = reviews), {}, { "User" })
}