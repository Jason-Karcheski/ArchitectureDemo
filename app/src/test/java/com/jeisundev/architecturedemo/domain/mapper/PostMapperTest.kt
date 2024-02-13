package com.jeisundev.architecturedemo.domain.mapper

import com.google.common.truth.Truth.assertThat
import com.jeisundev.architecturedemo.data.dto.PostDto
import com.jeisundev.architecturedemo.domain.model.Post
import org.junit.Test

class PostMapperTest {

    @Test
    fun `given a PostDto, when mapped to a Post model, should return a Post object`() {
        val dto = getPostDto()
        val actual = dto.toModel()

        assertThat(actual).isInstanceOf(Post::class.java)
    }

    @Test
    fun `given a PostDto, when mapped to a Post model, should return correct title`() {
        val dto = getPostDto()
        val actual = dto.toModel()

        assertThat(actual.title).isEqualTo(dto.title)
    }

    @Test
    fun `given a PostDto, when mapped to a Post model, should return correct body`() {
        val dto = getPostDto()
        val actual = dto.toModel()

        assertThat(actual.body).isEqualTo(dto.body)
    }

    @Test
    fun `given a PostDto, when mapped to a Post model, should return correct id`() {
        val dto = getPostDto()
        val actual = dto.toModel()

        assertThat(actual.id).isEqualTo(dto.id)
    }

    @Test
    fun `given a PostDto, when mapped to a Post model, should return correct user id`() {
        val dto = getPostDto()
        val actual = dto.toModel()

        assertThat(actual.userId).isEqualTo(dto.userId)
    }

    // ========== HELPERS ========== //

    private fun getPostDto(): PostDto = PostDto(
        body = "body",
        title = "title",
        id = 0,
        userId = 0
    )

}