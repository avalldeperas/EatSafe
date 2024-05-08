package edu.uoc.avalldeperas.eatsafe.explore.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.uoc.avalldeperas.eatsafe.R
import edu.uoc.avalldeperas.eatsafe.explore.domain.model.Filters
import edu.uoc.avalldeperas.eatsafe.profile.composables.AllergyButton
import edu.uoc.avalldeperas.eatsafe.profile.domain.model.Intolerance
import edu.uoc.avalldeperas.eatsafe.profile.presentation.edit_profile.SectionHeader

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    filters: Filters,
    onDismiss: () -> Unit,
    onIntoleranceClick: (String) -> Unit
) {
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
                        AllergyButton(
                            icon = it.icon,
                            text = it.label,
                            enabled = filters.intolerances.contains(it.label),
                            onClick = { onIntoleranceClick(it.label) }
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}
