package edu.uoc.avalldeperas.eatsafe.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R

@Composable
fun EmptyListMessage(textResource: Int = R.string.no_elements_yet) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = textResource),
            fontSize = 16.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EmptyListMessagePreview() {
    EmptyListMessage(R.string.no_favorites_yet)
}