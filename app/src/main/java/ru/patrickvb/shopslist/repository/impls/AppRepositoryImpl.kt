package ru.patrickvb.shopslist.repository.impls

import retrofit2.Response
import ru.patrickvb.shopslist.api.API
import ru.patrickvb.shopslist.models.Promotion
import ru.patrickvb.shopslist.models.Shop
import ru.patrickvb.shopslist.models.ShopType
import ru.patrickvb.shopslist.repository.AppRepository
import javax.inject.Inject

const val API_VERSION = "4"
const val PUBLISHER = "МПМ"

class AppRepositoryImpl @Inject constructor(private val api: API) : AppRepository {

    override suspend fun getDiscountsById(shopId: Int): Response<ArrayList<Promotion>> {
        return api.getDiscountsById(API_VERSION, shopId, PUBLISHER)
    }

    override suspend fun getShops(): Response<ArrayList<Shop>> {
        return api.getShops()
    }

    override suspend fun getShopType(): Response<ArrayList<ShopType>> {
        return api.getShopType(API_VERSION)
    }
}