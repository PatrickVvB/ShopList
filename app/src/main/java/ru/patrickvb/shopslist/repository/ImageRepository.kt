package ru.patrickvb.shopslist.repository

import retrofit2.Response

interface ImageRepository {

    suspend fun getDiscountImage(imageName: String): Response<String>
}