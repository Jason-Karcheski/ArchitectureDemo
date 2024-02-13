package com.jeisundev.architecturedemo.presentation.posts.state

import com.jeisundev.architecturedemo.domain.model.Post

data class PostsScreenState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val posts: List<Post>? = null
)
