package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model

data class Filters(
    val intolerances: MutableList<String> = mutableListOf(),
    val cuisine: String = "",
    val safety: Double = 0.0,
    val rating: Double = 0.0,
    val name: String = "",
    val placeType: String = ""
)