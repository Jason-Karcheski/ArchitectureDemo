package com.jeisundev.architecturedemo.domain.usecase

import android.util.Log
import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import com.jeisundev.architecturedemo.core.status.Resource
import com.jeisundev.architecturedemo.data.dto.PostDto
import com.jeisundev.architecturedemo.data.repository.PostRepository
import com.jeisundev.architecturedemo.domain.mapper.toModel
import com.jeisundev.architecturedemo.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetPostsUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke() : Flow<Resource<List<Post>>> = flow {
        emit(Resource.Loading())

        val response = postRepository.getPosts()
            .map { postDto: PostDto -> postDto.toModel() }

        emit(Resource.Success(response = response))
    }.catch { throwable: Throwable ->
        emit(Resource.Error(error = throwable))
        Log.e(TAG, "invoke: ${throwable.localizedMessage}", throwable)
    }

    private companion object {
        private const val TAG = "GetPostsUseCase"
    }

}