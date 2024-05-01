package edu.uoc.avalldeperas.eatsafe.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun CenteredCircularProgressIndicator(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.testTag(CIRCULAR_PROGRESS_TAG))
    }
}

@Composable
fun CenteredCircularProgressIndicatorWithText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag(CIRCULAR_PROGRESS_TAG),
            trackColor = MAIN_GREEN
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        Text(text = stringResource(R.string.loading_label))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CenteredCircularProgressIndicatorPreview() {
    CenteredCircularProgressIndicator()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CenteredCircularProgressIndicatorWithTextPreview() {
    CenteredCircularProgressIndicatorWithText()
}