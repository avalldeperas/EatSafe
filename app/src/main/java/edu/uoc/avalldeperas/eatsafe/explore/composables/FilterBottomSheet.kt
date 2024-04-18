package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.profile.composables.AllergyButton
import edu.uoc.avalldeperas.eatsafe.profile.details.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.profile.edit_profile.presentation.SectionHeader
import edu.uoc.avalldeperas.eatsafe.ui.theme.MAIN_GREEN

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            item {
                SectionHeader(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 16.dp),
                    text = stringResource(R.string.intolerances)
                )
                FlowRow(modifier = Modifier.fillMaxWidth()) {
                    Intolerance().intolerances().forEach {
                        AllergyButton(imageVector = it.icon, text = it.label)
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
            item {
                Button(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MAIN_GREEN, contentColor = Color.White
                    )
                ) {
                    Text(text = stringResource(id = R.string.save), fontSize = 16.sp)
                }
            }
        }
    }
}