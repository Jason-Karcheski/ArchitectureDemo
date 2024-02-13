package com.jeisundev.architecturedemo.domain.mapper

import com.jeisundev.architecturedemo.data.dto.PostDto
import com.jeisundev.architecturedemo.domain.model.Post

fun PostDto.toModel() : Post = Post(id, body, title, userId)