package edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaceTest {

    @Test
    fun whenEmptyRatings_thenCalculatesExpectedAverageRatings() {
        val place = Place()
        val newAvgRating = place.calculateNewAverageRating(4)
        val newAvgSafety = place.calculateNewAverageSafety(3)

        assertEquals(4.0, newAvgRating)
        assertEquals(3.0, newAvgSafety)
    }

    @Test
    fun whenPlaceHasRatings_thenCalculatesExpectedAverageSafetyWithDecimals() {
        val place = Place(averageRating = 4.5, averageSafety = 4.3, totalReviews = 20)
        val newAvgRating = place.calculateNewAverageRating(1)
        val newAvgSafety = place.calculateNewAverageSafety(2)

        assertEquals(4.3, newAvgRating)
        assertEquals(4.2, newAvgSafety)
    }
}