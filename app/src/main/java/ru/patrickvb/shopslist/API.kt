package ru.patrickvb.shopslist

import android.graphics.Bitmap
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.ShopType
import java.util.Date

interface API {

    @GET("magnit-api/discounts/with-publisher")
    suspend fun getDiscountsById(
        @Header("version") version: String,
        @Query("shopId") shopId: Int,
        @Query("publisher") publisher: String
    ): Response<Promotion>

    @GET("images/220/{imageName}")
    suspend fun getDiscountImage(
        @Header("version") version: String,
        @Path("imageName") imageName: String
    ): Response<Bitmap>

    @GET("magnit-api/shops")
    suspend fun getShops(
        @Header("version") version: String,
        @Header("If-Modified-Since") modified: Date
    ): Response<ArrayList<Shop>>

    @GET("magnit-api/shops/types")
    suspend fun getShopType(
        @Header("version") version: String
    ): Response<ArrayList<ShopType>>
}