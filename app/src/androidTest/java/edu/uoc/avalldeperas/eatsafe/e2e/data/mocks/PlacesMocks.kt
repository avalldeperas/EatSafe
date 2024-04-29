package edu.uoc.avalldeperas.eatsafe.e2e.data.mocks

import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place

object PlacesMocks {
    internal fun dummyPlaces(): List<Place> {
        return MutableList(10) {
            Place(
                placeId = "place$it",
                "Place $it",
                address = "A Place Address $it",
                telephone = "901234567$it",
                website = "www.website$it.com"
            )
        }
    }
}