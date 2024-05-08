package edu.uoc.avalldeperas.eatsafe.explore.domain.model

import edu.uoc.avalldeperas.eatsafe.profile.domain.model.Allergen
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class PlaceTest {

    @Test
    fun doesMatchSearchQuery_whenNameContainsQuery_thenReturnsTrue() {
        val place = Place(name = "A long name")
        assertTrue(place.doesMatchSearchQuery("name"))
    }

    @Test
    fun doesMatchSearchQuery_whenAddressContainsQuery_thenReturnsTrue() {
        val place = Place(address = "address")
        assertTrue(place.doesMatchSearchQuery("add"))
    }

    @Test
    fun doesMatchSearchQuery_whenCuisineContainsQuery_thenReturnsTrue() {
        val place = Place(cuisine = "cuisine")
        assertTrue(place.doesMatchSearchQuery("cui"))
    }

    @Test
    fun doesMatchSearchQuery_whenInputDoesNotMatchAny_thenReturnsFalse() {
        val place = Place(cuisine = "cuisine", name = "name", address = "address")
        assertFalse(place.doesMatchSearchQuery("not matching"))
    }

    @Test
    fun doesMatchFilter_whenAllFiltersMatch_thenReturnsTrue() {
        val place = Place(allergens = mutableListOf(Allergen.Gluten, Allergen.Lactose))
        val filters = Filters(intolerances = mutableListOf("Gluten"))
        assertTrue(place.doesMatchFilter(filters))
    }

    @Test
    fun doesMatchFilter_whenNoneOfFiltersMatch_thenReturnsFalse() {
        val place = Place(allergens = mutableListOf(Allergen.Gluten))
        val filters = Filters(intolerances = mutableListOf("Lactose"))
        assertFalse(place.doesMatchFilter(filters))
    }

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