package ru.patrickvb.shopslist.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Promotion(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("startDate")
    val startDate: Date,
    @SerializedName("endDate")
    val endDate: Date,
    @SerializedName("price")
    val price: Double,
    @SerializedName("oldPrice")
    val oldPrice: Double
)