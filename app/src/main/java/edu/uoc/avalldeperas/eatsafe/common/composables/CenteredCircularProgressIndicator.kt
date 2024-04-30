package edu.uoc.avalldeperas.eatsafe.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import edu.uoc.avalldeperas.eatsafe.common.ComponentTagsConstants.CIRCULAR_PROGRESS_TAG

@Composable
fun CenteredCircularProgressIndicator() {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.testTag(CIRCULAR_PROGRESS_TAG))
    }
}