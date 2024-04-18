package edu.uoc.avalldeperas.eatsafe.common.composables

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import edu.uoc.avalldeperas.eatsafe.ui.theme.DARK_GREEN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    header: Int,
    color: Color = DARK_GREEN,
    fontWeight: FontWeight = FontWeight.Bold
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(header),
                color = color,
                fontWeight = fontWeight
            )
        }
    )
}