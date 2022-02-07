package com.lambdadigamma.moers.data.geo

import android.location.Location
import kotlinx.coroutines.flow.MutableSharedFlow

interface LocationService {

    @Throws(LocationPermissionException::class)
    fun getCurrentLocation(): MutableSharedFlow<Location>

}

class LocationPermissionException : Exception()

//class GMSLocationService(val context: Context) : LocationService {
//
//    private var fusedLocationProvider: FusedLocationProviderClient = LocationServices
//        .getFusedLocationProviderClient(context)
//
//    private var location: MutableSharedFlow<Location> = MutableSharedFlow()
//
//    override fun getCurrentLocation(): MutableSharedFlow<Location> {
//
//        if (!checkFineLocationPermission() && !checkCoarseLocationPermission()) {
//            throw LocationPermissionException()
//        }
//
//        fusedLocationProvider.getCurrentLocation()
//
//        fusedLocationProvider.getCurrentLocation()
//            .addOnSuccessListener { location ->
//                this.location.tryEmit(location)
//            }
//
//        return location
//
//    }
//
//    private fun checkFineLocationPermission(): Boolean {
//        return ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun checkCoarseLocationPermission(): Boolean {
//        return ActivityCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//}