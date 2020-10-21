package ru.patrickvb.shopslist.models

import kotlin.math.*

class SortShops(private val currentLat: Double, private val currentLng: Double)
    : Comparator<Shop> {

    override fun compare(p0: Shop?, p1: Shop?): Int {
        var lat1 = 0.0
        var lng1 = 0.0
        var lat2 = 0.0
        var lng2 = 0.0

        p0?.let {
            lat1 = it.lat
            lng1 = it.lng
        }

        p1?.let {
            lat2 = it.lat
            lng2 = it.lng
        }

        val distanceToShop1 = distance(currentLat, currentLng, lat1, lng1)
        val distanceToShop2 = distance(currentLat, currentLng, lat2, lng2)

        return (distanceToShop1 - distanceToShop2).toInt()
    }

    private fun distance(fromLat: Double, fromLon: Double, toLat: Double, toLon: Double): Double {
        val radius = 6378137.0
        val deltaLat = toLat - fromLat
        val deltaLon = toLon - fromLon
        val angle = 2 * asin(
            sqrt(
                sin(deltaLat / 2)
                    .pow(2.0) + cos(fromLat) * cos(toLat) * sin(deltaLon / 2)
                            .pow(2.0)
            )
        )
        return radius * angle
    }
}