package com.jeisundev.architecturedemo.data.di

import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import com.jeisundev.architecturedemo.data.repository.PostRepository
import com.jeisundev.architecturedemo.data.repository.PostRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesPostRepository(
        jsonPlaceholderService: JsonPlaceholderService
    ) : PostRepository = PostRepositoryImpl(jsonPlaceholderService = jsonPlaceholderService)

}