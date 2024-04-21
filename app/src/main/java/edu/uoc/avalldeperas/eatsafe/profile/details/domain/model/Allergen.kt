package edu.uoc.avalldeperas.eatsafe.profile.details.domain.model

import edu.uoc.avalldeperas.eatsafe.R

enum class Allergen(val displayName: String?, val iconResource: Int) {
    Gluten("Gluten", R.drawable.gluten),
    Lactose("Lactose", R.drawable.lactose)
}