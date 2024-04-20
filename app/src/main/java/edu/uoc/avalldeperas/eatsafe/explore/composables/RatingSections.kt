package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.AVERAGE_RATING_SECTION_STAR_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.SAFETY_SECTION_CHECK_ICON
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun SafetySectionWithNumber(modifier: Modifier, averageSafety: Double, fontSize: TextUnit = 16.sp) {
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
        Text(text = averageSafety.toString(), fontSize = fontSize)
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = SAFETY_SECTION_CHECK_ICON,
            tint = MAIN_GREEN
        )
    }
}

@Composable
fun AverageRatingSection(
    modifier: Modifier = Modifier,
    averageRating: Double,
    fontSize: TextUnit = 16.sp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = averageRating.toString(), fontSize = fontSize)
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = AVERAGE_RATING_SECTION_STAR_ICON,
            tint = MAIN_GREEN
        )
    }
}