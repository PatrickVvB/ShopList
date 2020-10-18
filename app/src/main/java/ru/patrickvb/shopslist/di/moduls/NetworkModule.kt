package ru.patrickvb.shopslist.di.moduls

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.patrickvb.shopslist.API
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIMEOUT = 10

@Module
class NetworkModule {

    private val gson = GsonBuilder()
        .setLenient().serializeNulls()
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .callTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun provideAPI(): API {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(API::class.java)
    }

    companion object{
        const val BASE_URL = "http://mobiapp.tander.ru/"
    }
}