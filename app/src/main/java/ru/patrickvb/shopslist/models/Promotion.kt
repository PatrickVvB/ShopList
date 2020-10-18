package ru.patrickvb.shopslist.models

import java.util.Date

data class Promotion(
    val id: Int,
    val name: String,
    val image: String,
    val startDate: Date,
    val endDate: Date,
    val price: Double,
    val oldPrice: Double
)