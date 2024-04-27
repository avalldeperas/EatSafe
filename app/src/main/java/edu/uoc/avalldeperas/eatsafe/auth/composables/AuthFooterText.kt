package edu.uoc.avalldeperas.eatsafe.auth.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@Composable
fun AuthFooterText(
    @StringRes firstText: Int,
    @StringRes secondText: Int,
    onClick: () -> Unit,
    contentDescription: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(firstText),
            modifier = Modifier
                .semantics { this.contentDescription = contentDescription },
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.padding(horizontal = 4.dp))
        Text(
            text = stringResource(secondText),
            modifier = Modifier
                .semantics { this.contentDescription = contentDescription },
            fontSize = 16.sp,
            color = MAIN_GREEN,
            fontWeight = FontWeight.Bold
        )
    }
}