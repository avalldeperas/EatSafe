package edu.uoc.avalldeperas.eatsafe.common.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import java.util.Locale

object GeocoderUtil {

    fun getAddressByName(
        locationName: String,
        context: Context,
        locale: Locale = Locale.getDefault()
    ): Address {
        var address = Address(locale)
        val geocoder = Geocoder(context, locale)
        try {
            val addresses: List<Address>? = geocoder.getFromLocationName(locationName, 1)
            if (!addresses.isNullOrEmpty()) {
                address = addresses[0]
                Log.d("avb", "GeocoderUtil:getFromLocationName. Address found $address")
            } else {
                Log.d(
                    "avb",
                    "GeocoderUtil:getFromLocationName. Could not find any address by name: $locationName"
                )
            }
        } catch (e: Exception) {
            Log.d("avb", "GeocoderUtil:getFromLocationName. Exception: ${e.message}")
        }

        return address
    }

    fun getAddressByLatLng(
        lat: Double,
        lng: Double,
        context: Context,
        locale: Locale = Locale.getDefault()
    ): Address {
        var address = Address(locale)
        val geocoder = Geocoder(context, locale)
        try {
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lng, 1)
            if (!addresses.isNullOrEmpty()) {
                address = addresses[0]
                Log.d("avb", "GeocoderUtil:getFromLocation. Address found $address")
            } else {
                Log.d(
                    "avb",
                    "GeocoderUtil:getFromLocation. Could not find any address by latlng: $lat / $lng"
                )
            }
        } catch (e: Exception) {
            Log.d("avb", "GeocoderUtil:getFromLocation. Exception: ${e.message}")
        }

        return address
    }

}