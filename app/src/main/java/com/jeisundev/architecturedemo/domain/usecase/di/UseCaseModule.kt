package com.jeisundev.architecturedemo.domain.usecase.di

import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import com.jeisundev.architecturedemo.data.repository.PostRepository
import com.jeisundev.architecturedemo.domain.usecase.GetPostByIdUseCase
import com.jeisundev.architecturedemo.domain.usecase.GetPostsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun providesGetPostsUseCase(
        postRepository: PostRepository
    ) : GetPostsUseCase = GetPostsUseCase(postRepository = postRepository)

    @Provides
    @ViewModelScoped
    fun providesGetPostByIdUseCase(
        postRepository: PostRepository
    ) : GetPostByIdUseCase = GetPostByIdUseCase(postRepository = postRepository)

}