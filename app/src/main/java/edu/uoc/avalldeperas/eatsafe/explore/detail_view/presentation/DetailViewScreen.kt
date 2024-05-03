package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.PLACE_INFO_ELEMENT
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_FAVORITE_BUTTON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ALLERGEN_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.DETAIL_BACK
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.USER_IMAGE_REVIEW
import edu.uoc.avalldeperas.eatsafe.common.composables.CenteredCircularProgressIndicator
import edu.uoc.avalldeperas.eatsafe.common.composables.EmptyListMessage
import edu.uoc.avalldeperas.eatsafe.common.util.StringUtils
import edu.uoc.avalldeperas.eatsafe.explore.composables.AverageRatingSection
import edu.uoc.avalldeperas.eatsafe.explore.composables.RatingsSection
import edu.uoc.avalldeperas.eatsafe.explore.composables.SafetySectionWithNumber
import edu.uoc.avalldeperas.eatsafe.explore.detail_view.state.DetailViewState
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Allergen
import edu.uoc.avalldeperas.eatsafe.reviews.domain.model.Review
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun DetailViewScreen(
    navigateBack: () -> Unit,
    toAddReview: (Place) -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val detailState by detailViewModel.detailState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarHostState() }

    DetailViewContent(
        detailState = detailState,
        navigateBack = navigateBack,
        toAddReview = toAddReview,
        snackbarState = snackbarState,
        onAddFav = { detailViewModel.addFavourite(context) }
    )
}

@Composable
fun DetailViewContent(
    detailState: DetailViewState,
    navigateBack: () -> Unit,
    toAddReview: (Place) -> Unit,
    snackbarState: SnackbarHostState,
    onAddFav: () -> Unit,
) {
    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarState) }) { paddingValues ->
        if (detailState.isLoading) {
            CenteredCircularProgressIndicator()
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(1f)
            ) {
                IconButton(
                    onClick = { navigateBack() },
                    modifier = Modifier.padding(paddingValues)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = DETAIL_BACK,
                        tint = MAIN_GREEN
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.restaurant_detail),
                    contentDescription = PLACE_IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .shadow(1.dp, RoundedCornerShape(1.dp)),
                    contentScale = ContentScale.Crop,
                )
                DetailHeader(
                    onFavClick = { onAddFav() },
                    paddingValues = paddingValues,
                    place = detailState.place,
                    isFav = detailState.userFav != null
                )
                AppHorizontalDivider(top = 16.dp)
                DetailAbout(modifier = Modifier, place = detailState.place)
                AppHorizontalDivider(top = 16.dp)
                DetailReviews(
                    modifier = Modifier,
                    toAddReview = toAddReview,
                    place = detailState.place,
                    showAddReviewBtn = !detailState.isUserReview
                )
            }
        }
    }
}

@Composable
fun AppHorizontalDivider(top: Dp, bottom: Dp = 0.dp, color: Color = Color.Gray) {
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier.padding(top = top, bottom = bottom)
    )
}

@Composable
fun DetailReviews(
    modifier: Modifier,
    toAddReview: (Place) -> Unit,
    place: Place,
    showAddReviewBtn: Boolean,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 8.dp, top = 20.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ReviewHeader(toAddReview = toAddReview, place = place, showButton = showAddReviewBtn)
        if (place.reviews.isEmpty()) {
            EmptyListMessage(R.string.no_reviews_yet)
        } else {
            place.reviews.forEach {
                ReviewItem(it)
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.default_account),
                contentDescription = USER_IMAGE_REVIEW,
                modifier = Modifier.size(70.dp)
            )
        }
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row {
                Text(
                    text = review.userName,
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
    AppHorizontalDivider(top = 0.dp, color = Color.LightGray)
}

@Composable
fun ReviewHeader(toAddReview: (Place) -> Unit, place: Place, showButton: Boolean = true) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.reviews_header),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        if (showButton) {
            Button(
                onClick = { toAddReview(place) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = MAIN_GREEN
                ),
                border = BorderStroke(0.5.dp, MAIN_GREEN)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "", tint = MAIN_GREEN)
                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                Text(
                    text = stringResource(R.string.add_review_header),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun DetailAbout(modifier: Modifier, place: Place) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp, bottom = 14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "About",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        InfoElement(
            imageVector = Icons.Filled.Phone,
            text = place.telephone
        )
        InfoElement(
            imageVector = Icons.Filled.Link,
            text = place.website
        )
        InfoElement(
            imageVector = Icons.Filled.LocationOn,
            text = place.address
        )
    }
}

@Composable
fun InfoElement(imageVector: ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(imageVector = imageVector, contentDescription = "", tint = MAIN_GREEN)
        Text(text = text, fontSize = 14.sp, modifier = Modifier.testTag(PLACE_INFO_ELEMENT + text))
    }
}

@Composable
fun DetailHeader(
    onFavClick: () -> Unit,
    paddingValues: PaddingValues,
    place: Place,
    isFav: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(imageVector = place.type.imageVector, contentDescription = "")
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = place.name,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = place.cuisine,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.weight(0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        IconButton(
            onClick = { onFavClick() },
            modifier = Modifier.padding(paddingValues)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = ADD_FAVORITE_BUTTON,
                tint = if (isFav) Color.Red else Color.Gray
            )
        }
    }
    RatingsSection(modifier = Modifier.fillMaxWidth(), place = place)
    AllergensHeader(modifier = Modifier.fillMaxWidth(), place = place)
}

@Composable
fun AllergensHeader(modifier: Modifier = Modifier, place: Place) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.allergens_detail_header),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        if (place.allergens.isEmpty()) {
            Text(text = stringResource(R.string.no_allergens_yet))
        } else {
            place.allergens.forEach {
                Icon(
                    painterResource(id = it.iconResource),
                    contentDescription = ALLERGEN_ICON + it.displayName
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DetailViewContentPreview() {
    val place = Place(
        name = "Can Culleres",
        telephone = "900 00 00 00",
        cuisine = "Mediterranean",
        website = "www.webisite.com",
        address = "Street name, 01, City",
        allergens = mutableListOf(Allergen.Lactose, Allergen.Gluten),
        reviews = listOf(Review(description = "tester", placeName = "pepe", userName = "Pepito"))
    )
    DetailViewContent(
        detailState = DetailViewState(place = place),
        navigateBack = {},
        toAddReview = {},
        snackbarState = SnackbarHostState(),
        onAddFav = {}
    )
}