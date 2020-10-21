package ru.patrickvb.shopslist.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ImageAPI {

    @GET("images/220/{imageName}")
    suspend fun getDiscountImage(
        @Header("version") version: String,
        @Path("imageName") imageName: String
    ): Response<String>
}