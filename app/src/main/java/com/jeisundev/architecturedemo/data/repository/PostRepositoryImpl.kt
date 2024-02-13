package com.jeisundev.architecturedemo.data.repository

import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import com.jeisundev.architecturedemo.data.dto.PostDto
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val jsonPlaceholderService: JsonPlaceholderService
) : PostRepository {

    override suspend fun getPosts(): List<PostDto> = jsonPlaceholderService.getPosts()

    override suspend fun getPostById(postId: Int): PostDto = jsonPlaceholderService.getPostById(id = postId)

}