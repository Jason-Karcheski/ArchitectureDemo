package com.jeisundev.architecturedemo.presentation.posts.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeisundev.architecturedemo.R
import com.jeisundev.architecturedemo.domain.model.Post
import com.jeisundev.architecturedemo.presentation.common.composable.Info
import com.jeisundev.architecturedemo.presentation.posts.state.PostsScreenState
import com.jeisundev.architecturedemo.presentation.posts.viewmodel.PostsViewModel

@Composable
fun PostsRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClicked: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    PostsScreen(
        state = state,
        onGetPosts = viewModel::getPosts,
        onPostClicked = onPostClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    state: PostsScreenState,
    onGetPosts: () -> Unit,
    onPostClicked: (Int) -> Unit
) {

    LaunchedEffect(Unit) {
        onGetPosts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.semantics { contentDescription = "top bar" },
                title = {
                    Text(
                        modifier = Modifier.semantics { contentDescription = "title" },
                        text = stringResource(id = R.string.posts)
                    )
                }
            )
        },
        content = { padding: PaddingValues ->
            when (state.isLoading) {
                true -> LoadingContent(
                    modifier = Modifier.padding(paddingValues = padding)
                )
                false -> ScreenContent(
                    modifier = Modifier.padding(paddingValues = padding),
                    posts = state.posts,
                    errorMessage = state.error?.localizedMessage,
                    onPostClicked = onPostClicked
                )
            }
        }
    )
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics { contentDescription = "spinner" }
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    posts: List<Post>?,
    errorMessage: String?,
    onPostClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        errorMessage?.let { message: String ->
            Info(
                modifier = Modifier.semantics { contentDescription = "error" },
                icon = Icons.Rounded.Warning,
                message = message
            )
        } ?: run {
            when (posts == null) {
                true -> Info(message = stringResource(id = R.string.no_posts_found))
                false -> PostsList(
                    posts = posts,
                    onItemClick = onPostClicked
                )
            }
        }
    }

}

@Composable
private fun PostsList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .semantics { contentDescription = "post list" },
        contentPadding = PaddingValues(
            top = dimensionResource(id = R.dimen.space_medium),
            bottom = dimensionResource(id = R.dimen.space_large),
            start = dimensionResource(id = R.dimen.space_large),
            end = dimensionResource(id = R.dimen.space_large),
        ),
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.space_large))
    ) {
        items(items = posts) { post: Post ->
            PostListItem(
                title = post.title,
                body = post.body,
                postId = post.id,
                onClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostListItem(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    postId: Int,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier.fillMaxSize(),
        onClick = { onClick(postId) }
    ) {
        Column(
            modifier = Modifier.padding(all = dimensionResource(id = R.dimen.space_large)),
            verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.space_medium))
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = body,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

