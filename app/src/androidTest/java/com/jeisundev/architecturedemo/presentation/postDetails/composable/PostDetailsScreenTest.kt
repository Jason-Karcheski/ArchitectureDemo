package com.jeisundev.architecturedemo.presentation.postDetails.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.jeisundev.architecturedemo.domain.model.Post
import com.jeisundev.architecturedemo.presentation.postDetails.state.PostDetailsScreenState
import org.junit.Rule
import org.junit.Test

class PostDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPostDetailsScreen_whenStarted_shouldShowTopBar() {
        with(composeTestRule) {
            setContent { PostDetailsScreenTestWrapper() }
            onNodeWithContentDescription("top bar").assertIsDisplayed()
        }
    }

    @Test
    fun givenPostDetailsScreen_whenStarted_shouldShowCorrectTitle() {
        with(composeTestRule) {
            setContent { PostDetailsScreenTestWrapper() }
            onNodeWithContentDescription("title")
                .assertIsDisplayed()
                .assert(hasText("Post Details - 1"))
        }
    }

    @Test
    fun givenPostDetailsScreen_whenStarted_shouldShowBackButton() {
        with(composeTestRule) {
            setContent { PostDetailsScreenTestWrapper() }
            onNodeWithContentDescription("back")
                .assertIsDisplayed()
        }
    }

    @Test
    fun givenPostDetailsScreen_whenHasPost_shouldShowCorrectTitle() {
        with(composeTestRule) {
            val state = PostDetailsScreenState(
                isLoading = false,
                post = getPost()
            )
            setContent { PostDetailsScreenTestWrapper(state = state) }
            onNodeWithContentDescription("heading")
                .assertIsDisplayed()
                .assert(hasText(state.post?.title ?: "TITLE WAS NULL"))
        }
    }

    @Test
    fun givenPostDetailsScreen_whenHasPost_shouldShowCorrectBody() {
        with(composeTestRule) {
            val state = PostDetailsScreenState(
                isLoading = false,
                post = getPost()
            )
            setContent { PostDetailsScreenTestWrapper(state = state) }
            onNodeWithContentDescription("body")
                .assertIsDisplayed()
                .assert(hasText(state.post?.body ?: "BODY WAS NULL"))
        }
    }

    // ========= HELPERS ========== //

    @Composable
    private fun PostDetailsScreenTestWrapper(
        state: PostDetailsScreenState = PostDetailsScreenState(),
        postId: Int = 1,
        onGetPost: (Int?) -> Unit = { /* Implementation not needed by default for tests */ },
        onBack: () -> Unit = { /* Implementation not needed by default for tests */ }
    ) {
        MaterialTheme {
            PostDetailsScreen(
                state = state,
                postId = postId,
                onGetPost = onGetPost,
                onBack = onBack
            )
        }
    }

    private fun getPost(): Post = Post(
        id = 0,
        userId = 0,
        title = "TITLE",
        body = "BODY"
    )

}