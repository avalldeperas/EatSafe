package edu.uoc.avalldeperas.eatsafe.common.util

import org.junit.Assert.assertEquals
import org.junit.Test

class StringUtilsTest {

    @Test
    fun getParsedDistance_whenLessThan1000m_thenReturnsMeters() {
        val result = StringUtils.getParsedDistance(356.45f)
        assertEquals("356m", result)
    }
    @Test
    fun getParsedDistance_whenBiggerThan1000m_thenReturnsKmInOneDecimal() {
        val result = StringUtils.getParsedDistance(5423.5522f)
        assertEquals("5.4km", result)
    }
}