package com.jeisundev.architecturedemo.data.repository

import com.jeisundev.architecturedemo.data.dto.PostDto

interface PostRepository {

    suspend fun getPosts() : List<PostDto>

    suspend fun getPostById(postId: Int) : PostDto

}