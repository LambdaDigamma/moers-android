package com.lambdadigamma.core.geo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
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
                Log.i("GMSLocationService", "No coarse location permission")
                continuation.resume(null)
            }

            val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(1)
                .setInterval(1)
                .setExpirationDuration(1000)

            val listener = object : LocationCallback() {
                override fun onLocationResult(p0: LocationResult) {
                    continuation.resume(
                        Point(
                            latitude = p0.lastLocation.latitude,
                            longitude = p0.lastLocation.longitude
                        )
                    )
                }
            }

            val task = fusedLocationProvider.requestLocationUpdates(
                locationRequest,
                listener,
                Looper.getMainLooper()
            )

            task.addOnFailureListener { continuation.resumeWithException(it) }

        }

    }
}