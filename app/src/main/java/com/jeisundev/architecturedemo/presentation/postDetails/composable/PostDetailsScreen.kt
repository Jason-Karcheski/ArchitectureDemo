package com.jeisundev.architecturedemo.presentation.postDetails.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.jeisundev.architecturedemo.R
import com.jeisundev.architecturedemo.domain.model.Post
import com.jeisundev.architecturedemo.presentation.common.composable.Info
import com.jeisundev.architecturedemo.presentation.postDetails.state.PostDetailsScreenState
import com.jeisundev.architecturedemo.presentation.postDetails.viewmodel.PostDetailsViewModel

@Composable
fun PostDetailsRoute(
    viewModel: PostDetailsViewModel = hiltViewModel(),
    postId: Int?,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    PostDetailsScreen(
        state = state,
        onGetPost = viewModel::getPostById,
        postId = postId,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(
    state: PostDetailsScreenState,
    postId: Int?,
    onGetPost: (Int?) -> Unit,
    onBack: () -> Unit
) {

    LaunchedEffect(Unit) {
        onGetPost(postId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.semantics { contentDescription = "top bar" },
                title = {
                    Text(
                        modifier = Modifier.semantics { contentDescription = "title" },
                        text = "${stringResource(id = R.string.post_details)} - $postId"
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.semantics { contentDescription = "back" },
                        content = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        },
                        onClick = onBack
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
                    post = state.post,
                    errorMessage = state.error?.localizedMessage
                )
            }
        }
    )
}

@Composable
private fun LoadingContent(
    modifier: Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    post: Post?,
    errorMessage: String?
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        errorMessage?.let { message: String ->
            Info(
                icon = Icons.Rounded.Warning,
                message = message
            )
        } ?: run {
            when (post == null) {
                true -> Info(message = stringResource(id = R.string.no_post_found))
                false -> PostContent(
                    title = post.title,
                    body = post.body
                )
            }
        }
    }
}

@Composable
private fun PostContent(
    modifier: Modifier = Modifier,
    title: String,
    body: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.space_large)),
        verticalArrangement = Arrangement.spacedBy(space = dimensionResource(id = R.dimen.space_large),)
    ) {
        Text(
            modifier = Modifier.semantics { contentDescription = "heading" },
            text = title,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            modifier = Modifier.semantics { contentDescription = "body"},
            text = body,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}