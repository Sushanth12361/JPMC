package com.example.jpmorgantest.util.extension

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.example.jpmorgantest.presentation.deletage.entities.LatLngPresentation
import com.example.jpmorgantest.util.constants.LOCATION_EXPIRATION_TIME
import com.example.jpmorgantest.util.constants.LOCATION_INTERVAL
import com.example.jpmorgantest.util.constants.LOCATION_NUM_UPDATES
import com.example.jpmorgantest.util.constants.LOCATION_REFRESH_TIME
import com.example.jpmorgantest.util.constants.LOCATION_TIME_THRESHOLD
import com.example.jpmorgantest.util.constants.ZERO
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

suspend fun Context.getLocation(): LatLngPresentation? {
    val locationClient = LocationServices.getFusedLocationProviderClient(this)
    return getLastKnownLocation(this) ?: getCurrentLocation(locationClient) ?: getAccurateLocation(
        locationClient
    )
}

@SuppressLint("MissingPermission")
private suspend fun getLastKnownLocation(context: Context) =
    suspendCoroutine { continuation ->
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        val passive = locationManager?.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        val network = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        val gps = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val location = getBestLocation(listOf(passive, network, gps))
        continuation.resume(location?.let { LatLngPresentation(location.latitude, location.longitude) })
    }

@SuppressLint("MissingPermission")
private suspend fun getCurrentLocation(locationClient: FusedLocationProviderClient) =
    suspendCancellableCoroutine { continuation ->
        val cancellationTokenSource = CancellationTokenSource()
        val cancellationToken = object : CancellationToken() {
            override fun onCanceledRequested(listner: OnTokenCanceledListener) =
                cancellationTokenSource.token

            override fun isCancellationRequested() = false
        }

        locationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationToken
        ).apply {
            addOnSuccessListener { location ->
                continuation.resume(location?.let { LatLngPresentation(it.latitude, it.longitude) })
                cancellationTokenSource.cancel()
            }

            addOnCanceledListener {
                continuation.cancel()
            }
        }
    }

@SuppressLint("MissingPermission")
private suspend fun getAccurateLocation(locationClient: FusedLocationProviderClient) =
    suspendCancellableCoroutine { continuation ->
        var attempts = ZERO
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (attempts == LOCATION_NUM_UPDATES) {
                    val location = getBestLocation(locationResult.locations)
                    continuation.resume(location?.let { LatLngPresentation(it.latitude, it.longitude) })
                    locationClient.removeLocationUpdates(this)
                }

                attempts++
            }
        }
        locationClient.requestLocationUpdates(
            LocationRequest.create().apply {
                setExpirationDuration(LOCATION_EXPIRATION_TIME)
                numUpdates = LOCATION_NUM_UPDATES
                interval = LOCATION_INTERVAL
                fastestInterval = LOCATION_REFRESH_TIME
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            },
            locationCallback,
            Looper.getMainLooper()
        ).addOnCanceledListener {
            continuation.cancel()
        }
    }

private fun getBestLocation(locations: List<Location?>): Location? = locations.mapNotNull { it }
    .filter {
        Date().time - it.time <= LOCATION_TIME_THRESHOLD
    }.maxByOrNull {
        it.accuracy
    }

fun Context.areLocationPermissionsAlreadyGranted(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.openApplicationSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also {
        startActivity(it)
    }
}
