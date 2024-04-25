package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_FAVORITES_BUTTON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_VIEW_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_VIEW_PLACE_TYPE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.LIST_VIEW_STAR_ICON
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun PlaceItem(place: Place, onRowClick: (String) -> Unit, distance: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onRowClick(place.placeId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = place.image),
            contentDescription = LIST_VIEW_PLACE_IMAGE + place.placeId,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(5.dp))
                .shadow(1.dp, RoundedCornerShape(1.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                Icon(
                    imageVector = place.type.imageVector,
                    contentDescription = LIST_VIEW_PLACE_TYPE_ICON + place.placeId
                )
                Text(
                    text = place.name,
                    Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = distance)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = FAV_FAVORITES_BUTTON + place.placeId
                )
                Text(
                    text = place.address,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(modifier = Modifier.padding(start = 4.dp)) {
                SafetySectionWithNumber(modifier = Modifier.weight(1f), place.averageSafety)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = place.averageRating.toString())
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = LIST_VIEW_STAR_ICON,
                        tint = MAIN_GREEN
                    )
                }
            }
        }
    }
    HorizontalDivider()
}