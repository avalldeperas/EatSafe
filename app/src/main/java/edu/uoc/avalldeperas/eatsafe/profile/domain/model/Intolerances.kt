package edu.uoc.avalldeperas.eatsafe.profile.domain.model

import androidx.annotation.DrawableRes
import edu.uoc.avalldeperas.eatsafe.R

data class Intolerance(
    val label: String = "",
    @DrawableRes val icon:  Int = R.drawable.gluten,
    var enabled: Boolean = false,
) {
    fun intolerances(): List<Intolerance> {
        return listOf(
            Intolerance(
                label = "Gluten",
                icon = R.drawable.gluten,
                enabled = false
            ),
            Intolerance(
                label = "Lactose",
                icon = R.drawable.lactose,
                enabled = false
            )
        )
    }
}
