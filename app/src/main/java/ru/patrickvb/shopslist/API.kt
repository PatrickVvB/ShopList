package ru.patrickvb.shopslist

import retrofit2.Response
import retrofit2.http.*
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.ShopType

interface API {

    @GET("magnit-api/discounts/with-publisher")
    suspend fun getDiscountsById(
        @Header("version") version: String,
        @Query("shopId") shopId: Int,
        @Query("publisher") publisher: String
    ): Response<ArrayList<Promotion>>

    @GET("images/220/{imageName}")
    suspend fun getDiscountImage(
        @Header("version") version: String,
        @Path("imageName") imageName: String
    ): Response<String>

    @Headers("version: 4", "If-Modified-Since: Thu Mar 17 2020 08:45:25 GMT+0300 (MSK)")
    @GET("magnit-api/shops")
    suspend fun getShops(): Response<ArrayList<Shop>>

    @GET("magnit-api/shops/types")
    suspend fun getShopType(
        @Header("version") version: String
    ): Response<ArrayList<ShopType>>
}