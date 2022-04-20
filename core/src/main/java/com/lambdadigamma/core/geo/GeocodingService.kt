package com.lambdadigamma.core.geo

import android.content.Context
import android.location.Geocoder

interface GeocodingService {

    fun reverseGeocode(point: Point): List<GeocodedAddress>

    fun geocodeBestPosition(search: String): Point?

}

class DefaultGeocodingService(val context: Context) : GeocodingService {

    private var geocoder: Geocoder = Geocoder(context)

    override fun reverseGeocode(point: Point): List<GeocodedAddress> {

        return geocoder
            .getFromLocation(point.latitude, point.longitude, 1)
            .map {
                it.toGeocodedAddress()
            }

    }

    override fun geocodeBestPosition(search: String): Point? {
        return this
            .geocodeIntoPointList(search, maxResults = 1)
            .firstOrNull()
    }

    private fun geocodeIntoPointList(text: String, maxResults: Int = 1): List<Point> {
        return geocoder.getFromLocationName(text, maxResults)
            .filterNotNull()
            .map {
                Point(it.latitude, it.longitude)
            }
    }

}