package edu.uoc.avalldeperas.eatsafe.favorites.composables

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAVORITE_PLACE_IMAGE
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_LOCATION_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_PLACE_TYPE_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.FAV_VIEW_HEART_ICON
import edu.uoc.avalldeperas.eatsafe.common.util.StringUtils
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.PlaceType
import edu.uoc.avalldeperas.eatsafe.favorites.domain.model.FavoritePlace
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun FavoriteItem(favorite: FavoritePlace, onRowClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onRowClick(favorite.placeId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = favorite.image),
            contentDescription = FAVORITE_PLACE_IMAGE + favorite.placeId,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(5.dp))
                .shadow(1.dp, RoundedCornerShape(1.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Text(
                    text = favorite.name,
                    Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = FAV_VIEW_HEART_ICON + favorite.placeId,
                    tint = Color.Red
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = favorite.type.imageVector,
                    contentDescription = FAV_PLACE_TYPE_ICON + favorite.placeId
                )
                Text(
                    text = favorite.type.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = StringUtils.getParsedDate(favorite.date),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = FAV_LOCATION_ICON + favorite.placeId,
                    tint = MAIN_GREEN
                )
                Text(
                    text = favorite.address,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
    HorizontalDivider()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoriteItemPreview() {
    val fav = FavoritePlace(
        name = "Racó del Plà",
        address = "Carrer de llacuna, poblenou, 185",
        type = PlaceType.Restaurant
    )
    FavoriteItem(fav, {})
}