package com.lambdadigamma.core.geo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface LocationService {

    @Throws(LocationPermissionException::class)
    fun getCurrentLocation(): MutableSharedFlow<Location>

}

class LocationPermissionException : Exception()

class GMSLocationService(
    val context: Context,
    lastKnownLocation: Location? = null
) : LocationService {

    private var fusedLocationProvider: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(context)

    init {
        lastKnownLocation?.let {
            if (!checkFineLocationPermission() && !checkCoarseLocationPermission()) {
                return@let
            }
            fusedLocationProvider.setMockLocation(it)
            fusedLocationProvider.setMockMode(true)
        }
    }

    private var location: MutableSharedFlow<Location> = MutableSharedFlow()

    override fun getCurrentLocation(): MutableSharedFlow<Location> {

        if (!checkFineLocationPermission() && !checkCoarseLocationPermission()) {
            throw LocationPermissionException()
        }

//        fusedLocationProvider.getCurrentLocation()

//        fusedLocationProvider.getCurrentLocation()
//            .addOnSuccessListener { location ->
//                this.location.tryEmit(location)
//            }

        return location

    }

    fun checkFineLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun checkCoarseLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    suspend fun loadLastLocation(): Point? {

        return suspendCoroutine { continuation ->

            if (!checkCoarseLocationPermission()) {
                continuation.resume(null)
            }

//            fusedLocationProvider.getCurrentLocation(
//                LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY,
//                CancellationToken
//            )

            fusedLocationProvider.lastLocation

            fusedLocationProvider.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        val point =
                            Point(latitude = location.latitude, longitude = location.longitude)
                        return@addOnSuccessListener continuation.resume(point)
                    }
                    return@addOnSuccessListener continuation.resume(null)
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }

        }

    }
}