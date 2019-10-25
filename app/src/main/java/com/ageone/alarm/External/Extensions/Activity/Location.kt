package com.ageone.alarm.External.Extensions.Activity

import android.location.Location
import android.widget.Toast
import com.ageone.alarm.Application.AppActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

val locationBase = LatLng(56.838607, 60.605514)
var currentLocation: Location? = null


fun AppActivity.fetchLastLocation(){
    /*fusedLocationClient?.
        lastLocation?.addOnSuccessListener{ location ->
        if (location != null) {
            currentLocation = location
        } else {
            Toast.makeText(this, "No Location recorded", Toast.LENGTH_SHORT).show()
        }
    }*/

    getLocationUpdates()
}


private fun AppActivity.getLocationUpdates() {

    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    locationRequest = LocationRequest().apply {
        interval = 1 * 1000
        fastestInterval = 1 * 1000
        smallestDisplacement = 100f // 100 m
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return

            if (locationResult.locations.isNotEmpty()) {
                // get latest location
                val location = locationResult.lastLocation
                // use your location object
                // get latitude , longitude and other info from this
                Timber.i("Newest location: ${location.latitude}")
                currentLocation  = location
            }
        }
    }
}

//start location updates
fun AppActivity.startLocationUpdates() {
    fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
    )
}

// stop location updates
fun AppActivity.stopLocationUpdates() {
    fusedLocationClient?.removeLocationUpdates(locationCallback)
}

var startLocation: LatLng = locationBase
    get() {
        return if (isLocationGranted) {
            LatLng(
                    currentLocation?.latitude ?: locationBase.latitude,
                    currentLocation?.longitude ?: locationBase.longitude
            )
        } else {
            locationBase
        }

    }