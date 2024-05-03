package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_AVG_RATING
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_AVG_SAFETY
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.RATING_ITEM_TOTAL_REVIEWS
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.AVERAGE_RATING_SECTION_STAR_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.SAFETY_SECTION_CHECK_ICON
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun SafetySectionWithNumber(modifier: Modifier, averageSafety: Double, fontSize: TextUnit = 16.sp) {
    val isInt = averageSafety.rem(1) == (0.0)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = stringResource(R.string.safety_label),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (isInt) averageSafety.toInt().toString() else averageSafety.toString(),
            fontSize = fontSize
        )
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = SAFETY_SECTION_CHECK_ICON,
            tint = MAIN_GREEN
        )
    }
}

@Composable
fun RatingItem(
    modifier: Modifier = Modifier,
    value: Number,
    imageVector: ImageVector,
    fontSize: TextUnit = 16.sp,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(text = value.toString(), fontSize = fontSize)
        Icon(
            imageVector = imageVector,
            contentDescription = SAFETY_SECTION_CHECK_ICON,
            tint = MAIN_GREEN
        )
    }
}

@Composable
fun RatingsSection(
    modifier: Modifier = Modifier,
    place: Place,
) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.ratings_header),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            RatingItem(
                modifier = Modifier.testTag(RATING_ITEM_AVG_SAFETY),
                value = place.averageSafety,
                imageVector = Icons.Default.CheckCircle
            )
            RatingItem(
                modifier = Modifier.testTag(RATING_ITEM_AVG_RATING),
                value = place.averageRating,
                imageVector = Icons.Default.Star
            )
            RatingItem(
                modifier = Modifier.testTag(RATING_ITEM_TOTAL_REVIEWS),
                value = place.totalReviews,
                imageVector = Icons.Default.Person
            )
        }
    }
}

@Composable
fun AverageRatingSection(
    modifier: Modifier = Modifier,
    averageRating: Double,
    fontSize: TextUnit = 16.sp,
) {
    val isInt = averageRating.rem(1) == (0.0)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (isInt) averageRating.toInt().toString() else averageRating.toString(),
            fontSize = fontSize
        )
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = AVERAGE_RATING_SECTION_STAR_ICON,
            tint = MAIN_GREEN
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RatingsSectionPreview() {
    RatingsSection(modifier = Modifier.fillMaxWidth(), Place())
}