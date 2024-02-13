package com.jeisundev.architecturedemo.presentation.postDetails.state

import com.jeisundev.architecturedemo.domain.model.Post

data class PostDetailsScreenState(
    val isLoading: Boolean = true,
    val error: Throwable? = null,
    val post: Post? = null
)
