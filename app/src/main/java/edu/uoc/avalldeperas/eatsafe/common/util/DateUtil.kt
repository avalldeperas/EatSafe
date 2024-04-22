package edu.uoc.avalldeperas.eatsafe.common.util

import com.google.firebase.Timestamp
import java.time.ZoneId

object DateUtil {

    fun getParsedDate(timestamp: Timestamp): String {
        val date =
            timestamp.toDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        return "${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }
}