package edu.uoc.avalldeperas.eatsafe.auth.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp

@Composable
fun AuthFooterText(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    contentDescription: String,
) {
    Text(
        text = stringResource(textRes),
        modifier = Modifier
            .clickable { onClick() }
            .semantics { this.contentDescription = contentDescription },
        fontSize = 16.sp
    )
}