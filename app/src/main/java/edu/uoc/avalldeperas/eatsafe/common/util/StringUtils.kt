package edu.uoc.avalldeperas.eatsafe.common.util

import android.location.Location
import com.google.firebase.Timestamp
import edu.uoc.avalldeperas.eatsafe.auth.login.domain.model.User
import edu.uoc.avalldeperas.eatsafe.explore.list_map.domain.model.Place
import java.time.ZoneId

object StringUtils {

    fun getParsedDate(timestamp: Timestamp): String {
        val date =
            timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        return "${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }

    fun getParsedDistance(place: Place, user: User): String {
        val results = FloatArray(1)

        Location.distanceBetween(
            place.latitude,
            place.longitude,
            user.latitude,
            user.longitude,
            results
        )
        
        val distance = results[0]

        return if (distance >= 1000) "${(distance / 1000)}km" else "${distance.toInt()} m"
    }
}