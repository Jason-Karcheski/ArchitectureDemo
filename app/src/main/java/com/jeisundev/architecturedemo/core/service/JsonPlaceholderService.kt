package com.jeisundev.architecturedemo.core.service

import com.jeisundev.architecturedemo.data.dto.PostDto
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonPlaceholderService {

    @GET("posts")
    suspend fun getPosts() : List<PostDto>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int) : PostDto

}