package edu.uoc.avalldeperas.eatsafe.reviews.presentation

import android.content.Context
import androidx.annotation.StringRes
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.ADD_REVIEW_DESCRIPTION_FIELD
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_BACK_ICON
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ADD_REVIEW_RATE_BTN
import edu.uoc.avalldeperas.eatsafe.common.composables.CenteredCircularProgressIndicator
import edu.uoc.avalldeperas.eatsafe.reviews.presentation.state.AddReviewState
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun AddReviewScreen(
    backToDetail: () -> Unit,
    context: Context = LocalContext.current,
    addReviewViewModel: AddReviewViewModel = hiltViewModel()
) {
    val addReviewState by addReviewViewModel.addReviewState.collectAsStateWithLifecycle()

    AddReviewContent(
        backToDetail = backToDetail,
        addReviewState = addReviewState,
        updateSafety = addReviewViewModel::updateSafety,
        updateRating = addReviewViewModel::updateRating,
        updateDescription = addReviewViewModel::updateDescription,
        onSubmitReview = { addReviewViewModel.onSubmit(context, backToDetail) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewContent(
    addReviewState: AddReviewState,
    backToDetail: () -> Unit,
    updateSafety: (Int) -> Unit,
    updateRating: (Int) -> Unit,
    updateDescription: (String) -> Unit,
    onSubmitReview: () -> Unit,
) {
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
                            contentDescription = ADD_REVIEW_BACK_ICON,
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
                Text(
                    text = addReviewState.review.placeName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            RateButtonsRow(
                onClick = { updateSafety(it + 1) },
                value = addReviewState.safety,
                stringResource = R.string.safety_label,
                imageVector = Icons.Default.CheckCircle
            )
            RateButtonsRow(
                onClick = { updateRating(it + 1) },
                value = addReviewState.rating,
                stringResource = R.string.rating_label,
                imageVector = Icons.Default.Star
            )
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
                    value = addReviewState.description,
                    minLines = 8,
                    maxLines = 8,
                    onValueChange = { updateDescription(it) },
                    placeholder = { Text(text = stringResource(R.string.add_review_placeholder)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .testTag(ADD_REVIEW_DESCRIPTION_FIELD)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                if (addReviewState.isLoading) {
                    CenteredCircularProgressIndicator()
                } else {
                    Button(
                        onClick = { onSubmitReview() },
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
}

@Composable
fun RateButtonsRow(
    onClick: (Int) -> Unit,
    value: Int,
    @StringRes stringResource: Int,
    imageVector: ImageVector
) {
    val label = stringResource(stringResource)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        repeat(5) {
            IconButton(onClick = { onClick(it) }) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = ADD_REVIEW_RATE_BTN + label + it,
                    tint = if (it + 1 <= value) MAIN_GREEN else Color.Gray
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddReviewContentPreview() {
    AddReviewContent(AddReviewState(), {}, {}, {}, {}, {})
}