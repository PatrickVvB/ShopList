package ru.patrickvb.shopslist.models

import com.google.gson.annotations.SerializedName

data class Shop(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("address")
    val address: String,
    @SerializedName("lng")
    val lng: Double,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("opening")
    val opening: String?,
    @SerializedName("closing")
    val closing: String?
)