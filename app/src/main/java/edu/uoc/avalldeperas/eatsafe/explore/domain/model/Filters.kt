package edu.uoc.avalldeperas.eatsafe.explore.domain.model

data class Filters(
    val intolerances: MutableList<String> = mutableListOf(),
    val cuisine: String = "",
    val safety: Double = 0.0,
    val rating: Double = 0.0,
    val name: String = "",
    val placeType: String = ""
) {
    fun hasAnyValue(): Boolean {
        return intolerances.isNotEmpty() || cuisine.isNotEmpty() || safety > 0.0
                || rating > 0.0 || placeType.isNotEmpty()
    }
}