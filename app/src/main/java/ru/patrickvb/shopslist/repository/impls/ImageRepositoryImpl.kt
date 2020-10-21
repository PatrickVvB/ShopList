package ru.patrickvb.shopslist.repository.impls

import retrofit2.Response
import ru.patrickvb.shopslist.api.ImageAPI
import ru.patrickvb.shopslist.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val imageApi: ImageAPI) : ImageRepository {

    override suspend fun getDiscountImage(imageName: String): Response<String> {
        return imageApi.getDiscountImage(API_VERSION, imageName)
    }
}