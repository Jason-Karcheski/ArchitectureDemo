package com.jeisundev.architecturedemo.presentation.postDetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeisundev.architecturedemo.core.status.Resource
import com.jeisundev.architecturedemo.domain.usecase.GetPostByIdUseCase
import com.jeisundev.architecturedemo.presentation.postDetails.state.PostDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow(PostDetailsScreenState())
    val uiState = _uiState.asStateFlow()

    fun getPostById(postId: Int?) {
        viewModelScope.launch {
            if (postId == null) {
                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        error = Throwable(message = "Something went wrong"),
                        post = null
                    )
                }
            } else {
                getPostByIdUseCase(postId = postId).collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> _uiState.update { current ->
                            current.copy(isLoading = true)
                        }
                        is Resource.Error -> _uiState.update { current ->
                            current.copy(
                                isLoading = false,
                                error = resource.error,
                                post = null
                            )
                        }
                        is Resource.Success -> _uiState.update { current ->
                            current.copy(
                                isLoading = false,
                                error = null,
                                post = resource.response
                            )
                        }
                    }
                }
            }
        }
    }

}