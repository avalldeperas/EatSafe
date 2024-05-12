package edu.uoc.avalldeperas.eatsafe.profile.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.ALLERGY_ICON
import edu.uoc.avalldeperas.eatsafe.ui.theme.BLUE_STRONG

@Composable
fun AllergyButton(
    @DrawableRes icon: Int,
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
            painter = painterResource(id = icon),
            contentDescription = ALLERGY_ICON + text,
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }
}

@Preview
@Composable
fun AllergyButtonEnabledPreview() {
    AllergyButton(icon = R.drawable.gluten, text = "Gluten", enabled = true)
}

@Preview
@Composable
fun AllergyButtonPreview() {
    AllergyButton(icon = R.drawable.lactose, text = "Lactose", enabled = false)
}