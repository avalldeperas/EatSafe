package edu.uoc.avalldeperas.eatsafe.explore.detail_view.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.uoc.avalldeperas.eatsafe.navigation.Constants
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val placeId: String = checkNotNull(savedStateHandle[Constants.PLACE_ID_PARAM])
}