package edu.uoc.avalldeperas.eatsafe.common.util

import com.google.firebase.Timestamp
import java.math.RoundingMode
import java.time.ZoneId

object StringUtils {

    fun getParsedDate(timestamp: Timestamp): String {
        val date =
            timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        return "${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }

    fun getParsedDistance(distanceInM: Float): String {
        return if (distanceInM >= 1000)
            "${(distanceInM / 1000).toBigDecimal().setScale(1, RoundingMode.HALF_EVEN)}km"
        else "${distanceInM.toInt()}m"
    }
}