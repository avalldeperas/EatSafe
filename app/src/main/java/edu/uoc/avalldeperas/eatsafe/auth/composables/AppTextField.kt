package edu.uoc.avalldeperas.eatsafe.auth.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.common.ContentDescriptionConstants.SHOW_PASSWORD_ICON_FIELD

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    contentDescription: String,
    @StringRes label: Int,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit) = {}
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon, contentDescription = contentDescription
            )
        },
        label = { Text(text = stringResource(id = label), style = TextStyle(color = Color.Gray)) },
        singleLine = true,
        trailingIcon = {
            if (trailingIcon != null)
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = SHOW_PASSWORD_ICON_FIELD,
                    modifier = Modifier.clickable { onTrailingIconClick() }
                )
        },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Ascii),
        visualTransformation = visualTransformation,
        enabled = enabled
    )
}