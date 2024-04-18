package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

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
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun DetailViewScreen(
    navigateBack: () -> Unit,
    toAddReview: () -> Unit,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
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
                    contentDescription = ContentDescriptionConstants.FORGOT_BACK,
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
                contentDescription = ContentDescriptionConstants.EATSAFE_LOGO,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .shadow(1.dp, RoundedCornerShape(1.dp)),
                contentScale = ContentScale.Crop,
            )
            DetailHeader(
                navigateBack = navigateBack,
                paddingValues = paddingValues,
                placeId = detailViewModel.placeId
            )
            AppHorizontalDivider(top = 16.dp)
            DetailAbout(modifier = Modifier)
            AppHorizontalDivider(top = 16.dp)
            DetailReviews(modifier = Modifier, toAddReview = toAddReview)
        }
    }
}

@Composable
fun AppHorizontalDivider(top: Dp, color: Color = Color.Gray) {
    HorizontalDivider(
        color = color,
        thickness = 1.dp,
        modifier = Modifier.padding(top = top)
    )
}

@Composable
fun DetailReviews(modifier: Modifier, toAddReview: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 8.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ReviewHeader(toAddReview = toAddReview)
        repeat(3) {
            ReviewItem()
        }
    }
}

@Composable
fun ReviewItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column() {
            Image(
                painter = painterResource(id = R.drawable.default_account),
                contentDescription = "",
                Modifier.size(70.dp)
            )
        }
        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row() {
                Text(
                    text = "Jane doe",
                    Modifier.weight(1f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "25/02/2024")
            }
            Row() {
                SafetySection(modifier = Modifier.weight(1f))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "5")
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "",
                        tint = MAIN_GREEN
                    )
                }
            }
            Text(text = "Ho tenen tot molt controlat. No puc demanar haver tingut un millor tracte! Les pizzes...")
            Row() {
                repeat(2) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                }
            }
        }
    }
    AppHorizontalDivider(top = 0.dp, color = Color.LightGray)
}

@Composable
fun ReviewHeader(toAddReview: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Reviews",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Button(
            onClick = { toAddReview() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = MAIN_GREEN
            )
        ) {
            Icon(imageVector = Icons.Filled.Edit, contentDescription = "", tint = MAIN_GREEN)
            Text(
                text = "Add a Review",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun DetailAbout(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "About",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        InfoElement(imageVector = Icons.Filled.Phone, text = "+34 900 12 24 25")
        InfoElement(imageVector = Icons.Filled.Info, text = "www.racodelpla.com")
        InfoElement(
            imageVector = Icons.Filled.LocationOn,
            text = "Carrer Llacuna, 26, Barcelona, 08005"
        )
    }
}

@Composable
fun InfoElement(imageVector: ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(imageVector = imageVector, contentDescription = "", tint = MAIN_GREEN)
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun DetailHeader(navigateBack: () -> Unit, paddingValues: PaddingValues, placeId: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(imageVector = Icons.Default.Build, contentDescription = "")
        Text(
            text = "Racó del Plà (id = $placeId)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.7f),
        )
        IconButton(
            onClick = { navigateBack() },
            modifier = Modifier
                .padding(paddingValues)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = ContentDescriptionConstants.FORGOT_BACK,
                tint = Color.Red
            )
        }
    }
    Spacer(modifier = Modifier.padding(vertical = 4.dp))
    SafetySection(
        modifier = Modifier.padding(start = 24.dp)
    )
    Spacer(modifier = Modifier.padding(vertical = 4.dp))
    AllergensHeader()
}

@Composable
fun AllergensHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Allergens", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        repeat(2) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun SafetySection(modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = "Safety", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        repeat(5) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "",
                tint = MAIN_GREEN
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun DetailViewScreenPreview() {
    DetailViewScreen({}, {})
}