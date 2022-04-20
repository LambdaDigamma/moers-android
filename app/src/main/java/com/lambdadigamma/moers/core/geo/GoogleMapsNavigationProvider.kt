package com.lambdadigamma.moers.core.geo

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import com.lambdadigamma.moers.Application

class GoogleMapsNavigationProvider : NavigationProvider {

    override fun startNavigation(
        latitude: Double,
        longitude: Double,
        travelMode: TravelMode
    ) {
        val uri = buildUri(latitude = latitude, longitude = longitude, travelMode = travelMode)
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        return ContextCompat.startActivity(Application.instance, mapIntent, null)
    }

    override fun startNavigation(destination: Point, travelMode: TravelMode) {
        this.startNavigation(destination.latitude, destination.longitude, travelMode)
    }

    fun buildUri(latitude: Double, longitude: Double, travelMode: TravelMode): Uri {
        return Uri.parse("https://www.google.com/maps/dir/")
            .buildUpon()
            .appendQueryParameter("api", "1")
            .appendQueryParameter("destination", "$latitude,$longitude")
            .appendQueryParameter("travelmode", travelMode.toGoogleMapsMode())
            .appendQueryParameter("dir_action", "navigate")
            .build()
    }

}