package ru.patrickvb.shopslist.repository

import retrofit2.Response
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.ShopType

interface AppRepository {

    suspend fun getDiscountsById(shopId: Int): Response<ArrayList<Promotion>>

    suspend fun getDiscountImage(imageName: String): Response<ByteArray>

    suspend fun getShops(): Response<ArrayList<Shop>>

    suspend fun getShopType(): Response<ArrayList<ShopType>>
}