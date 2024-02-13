package com.jeisundev.architecturedemo.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.jeisundev.architecturedemo.core.service.JsonPlaceholderService
import com.jeisundev.architecturedemo.core.status.Resource
import com.jeisundev.architecturedemo.data.dto.PostDto
import com.jeisundev.architecturedemo.data.repository.PostRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPostsUseCaseTest {

    private val mockPostRepository: PostRepository = mockk()
    private lateinit var underTest: GetPostsUseCase

    @Before
    fun setUp() {
        underTest = GetPostsUseCase(postRepository = mockPostRepository)
    }

    @Test
    fun `given GetPostsUseCase, when invoked, should emit loading`() = runTest {
        val actual = underTest().first()
        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
    }

    @Test
    fun `given GetPostsUseCase, when successful, should emit success`() = runTest {
        coEvery { mockPostRepository.getPosts() }.returns(listOf())

        val actual = underTest()
            .dropWhile { resource -> resource is Resource.Loading }
            .first()

        assertThat(actual).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `given GetPostsUseCase, when successful, should emit correct response`() = runTest {
        val response = listOf(getPostDto())
        coEvery { mockPostRepository.getPosts() }.returns(response)

        val actual = underTest()
            .dropWhile { resource -> resource is Resource.Loading }
            .first()

        assertThat(actual).isInstanceOf(Resource.Success::class.java)
        assertThat((actual as Resource.Success).response.first().title).isEqualTo(response.first().title)
    }

    @Test
    fun `given GetPostsUseCase, when error is thrown, should emit error`() = runTest {
        coEvery { mockPostRepository.getPosts() }.throws(Exception())
        val actual = underTest()
            .dropWhile { resource -> resource is Resource.Loading }
            .first()
        assertThat(actual).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `given GetPostsUseCase, when error is thrown, should emit correct error`() = runTest {
        val exception = Exception("TEST EXCEPTION")
        coEvery { mockPostRepository.getPosts() }.throws(exception)

        val actual = underTest()
            .dropWhile { resource -> resource is Resource.Loading }
            .first()

        assertThat(actual).isInstanceOf(Resource.Error::class.java)
        assertThat((actual as Resource.Error).error.message).isEqualTo(exception.message)
    }

    // ========== HELPERS ========== //

    private fun getPostDto(): PostDto = PostDto(
        body = "body",
        title = "title",
        id = 0,
        userId = 0
    )

}