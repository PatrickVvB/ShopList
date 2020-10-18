package ru.patrickvb.shopslist.models

import com.google.gson.annotations.SerializedName

data class ShopType(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)