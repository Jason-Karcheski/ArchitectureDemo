package com.jeisundev.architecturedemo.presentation.posts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeisundev.architecturedemo.core.status.Resource
import com.jeisundev.architecturedemo.domain.usecase.GetPostsUseCase
import com.jeisundev.architecturedemo.presentation.posts.state.PostsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(PostsScreenState())
    val uiState = _uiState.asStateFlow()

    fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase().collectLatest { resource ->
                when (resource) {
                    is Resource.Loading -> _uiState.update { current ->
                        current.copy(isLoading = true)
                    }
                    is Resource.Error -> _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            error = resource.error,
                            posts = null
                        )
                    }
                    is Resource.Success -> _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            error = null,
                            posts = resource.response
                        )
                    }
                }
            }
        }
    }

}