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
class GetPostByIdUseCaseTest {

    private val mockPostRepository: PostRepository = mockk()
    private lateinit var underTest: GetPostByIdUseCase

    @Before
    fun setUp() {
        underTest = GetPostByIdUseCase(postRepository = mockPostRepository)
    }

    @Test
    fun `given GetPostByIdUseCase, when invoked, should emit loading`() = runTest {
        val actual = underTest(1).first()
        assertThat(actual).isInstanceOf(Resource.Loading::class.java)
    }

    @Test
    fun `given GetPostByIdUseCase, when successful, should emit success`() = runTest {
        coEvery { mockPostRepository.getPostById(any()) }.returns(getPostDto())

        val actual = underTest(1)
            .dropWhile { resource -> resource is Resource.Loading }
            .first()

        assertThat(actual).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `given GetPostByIdUseCase, when error is thrown, should emit error`() = runTest {
        coEvery { mockPostRepository.getPostById(any()) }.throws(Exception())

        val actual = underTest(1)
            .dropWhile { resource -> resource is Resource.Loading }
            .first()

        assertThat(actual).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `given GetPostByIdUseCase, when error is thrown, should emit correct error`() = runTest {
        val exception = Exception("TEST EXCEPTION")
        coEvery { mockPostRepository.getPostById(any()) }.throws(exception)

        val actual = underTest(1)
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