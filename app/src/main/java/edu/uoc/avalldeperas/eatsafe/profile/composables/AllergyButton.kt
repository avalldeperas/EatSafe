package edu.uoc.avalldeperas.eatsafe.profile.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.ui.theme.BLUE_STRONG

@Composable
fun AllergyButton(
    imageVector: ImageVector,
    text: String,
    enabled: Boolean = false,
    onClick: () -> Unit = {}
) {
    val containerColor: Color = if (enabled) BLUE_STRONG else Color.Gray
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor
        ),
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "allergy-icon-$text",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }
}

@Preview
@Composable
fun AllergyButtonEnabledPreview() {
    AllergyButton(imageVector = Icons.Filled.Info, text = "Gluten", enabled = true)
}

@Preview
@Composable
fun AllergyButtonPreview() {
    AllergyButton(imageVector = Icons.Filled.Info, text = "Gluten", enabled = false)
}