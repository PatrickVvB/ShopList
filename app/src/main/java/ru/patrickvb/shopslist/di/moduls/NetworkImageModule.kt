package ru.patrickvb.shopslist.di.moduls

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ru.patrickvb.shopslist.api.ImageAPI
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkImageModule {

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .callTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    @Provides
    fun provideAPIFromString(): ImageAPI {
        return Retrofit.Builder()
            .baseUrl(NetworkModule.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ImageAPI::class.java)
    }
}