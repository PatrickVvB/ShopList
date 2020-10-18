package ru.patrickvb.shopslist

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat

const val REQUEST_GEO = 5
fun getLocation(context: Context, locationListener: LocationListener) : Location? {

    val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == 0) {
                manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0, 10f, locationListener)
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0, 10f, locationListener)
                manager.getProvider(LocationManager.NETWORK_PROVIDER)
                val location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (location == null) {
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                return location
            } else
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_GEO)
        } else
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_GEO)
    } else return null
    return null
}