package edu.uoc.avalldeperas.eatsafe.reviews.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    backToDetail: () -> Unit
) {
    val restaurantName = "El Racó del pla"
    val typeIcon = Icons.Default.Home
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.add_review_header),
                        color = DARK_GREEN,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { backToDetail() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ContentDescriptionConstants.EDIT_PROFILE_BACK_ICON,
                            tint = DARK_GREEN
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = typeIcon, contentDescription = "")
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = restaurantName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.safety_label),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                repeat(5) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "",
                            tint = Color.Gray
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.rating_label),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                repeat(5) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = MAIN_GREEN
                        )
                    }
                }
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.add_review_description_header),
                    color = DARK_GREEN,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.padding(8.dp))
                TextField(
                    value = "",
                    minLines = 10,
                    maxLines = 10,
                    onValueChange = {},
                    placeholder = { Text(text = stringResource(R.string.add_review_placeholder)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MAIN_GREEN,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = stringResource(R.string.save), fontSize = 16.sp)
                }

            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddReviewScreenPreview() {
    AddReviewScreen({})
}