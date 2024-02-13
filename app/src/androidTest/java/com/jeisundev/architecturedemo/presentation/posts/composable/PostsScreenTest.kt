package com.jeisundev.architecturedemo.presentation.posts.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.jeisundev.architecturedemo.domain.model.Post
import com.jeisundev.architecturedemo.presentation.posts.state.PostsScreenState
import org.junit.Rule
import org.junit.Test

class PostsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPostsScreen_whenStarted_shouldShowTopBar() {
        with(composeTestRule) {
            setContent { PostScreenTestWrapper() }
            onNodeWithContentDescription("top bar").assertExists()
        }
    }

    @Test
    fun givenPostsScreen_whenStarted_shouldShowCorrectTitle() {
        with(composeTestRule) {
            setContent { PostScreenTestWrapper() }
            onNodeWithContentDescription("title").assert(hasText("Posts"))
        }
    }

    @Test
    fun givenPostsScreen_whenLoading_shouldShowLoadingContent() {
        with(composeTestRule) {
            setContent { PostScreenTestWrapper() }
            onNodeWithContentDescription("spinner").assertIsDisplayed()
        }
    }

    @Test
    fun givenPostsScreen_whenThereIsAnError_shouldShowErrorContent() {
        with(composeTestRule) {
            val state = PostsScreenState(
                isLoading = false,
                error = Throwable("TEST MESSAGE")
            )
            setContent { PostScreenTestWrapper(state = state) }
            onNodeWithContentDescription("error").assertIsDisplayed()
        }
    }

    @Test
    fun givenPostsScreen_whenThereIsAnError_shouldShowCorrectErrorMessage() {
        with(composeTestRule) {
            val errorMessage = "TEST MESSAGE"
            val state = PostsScreenState(
                isLoading = false,
                error = Throwable("TEST MESSAGE")
            )
            setContent { PostScreenTestWrapper(state = state) }
            onNodeWithContentDescription("info text")
                .assertIsDisplayed()
                .assert(hasText(errorMessage))
        }
    }

    @Test
    fun givenPostsScreen_whenThereArePosts_shouldShowPostList() {
        with(composeTestRule) {
            val posts = listOf(getPost())
            val state = PostsScreenState(
                isLoading = false,
                posts = posts
            )
            setContent { PostScreenTestWrapper(state = state) }
            onNodeWithContentDescription("post list").assertIsDisplayed()
        }
    }


    // ========== HELPERS ========== //

    @Composable
    private fun PostScreenTestWrapper(
        state: PostsScreenState = PostsScreenState(),
        onGetPosts: () -> Unit = { /* Implementation not needed by default for test */  },
        onPostClicked: (Int) -> Unit = { /* Implementation not needed by default for test */  }
    ) {
        MaterialTheme {
            PostsScreen(
                state = state,
                onGetPosts = onGetPosts,
                onPostClicked = onPostClicked
            )
        }
    }

    private fun getPost() : Post = Post(
        id = 0,
        userId = 0,
        title = "",
        body = ""
    )

}