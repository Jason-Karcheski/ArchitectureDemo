package com.jeisundev.architecturedemo.core.service.di

import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val gsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun providesJsonPlaceholderService() : JsonPlaceholderService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(JsonPlaceholderService::class.java)
    }

}