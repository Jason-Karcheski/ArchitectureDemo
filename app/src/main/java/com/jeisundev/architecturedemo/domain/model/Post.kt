package com.jeisundev.architecturedemo.domain.model

data class Post(
    val id: Int,
    val body: String,
    val title: String,
    val userId: Int
)
