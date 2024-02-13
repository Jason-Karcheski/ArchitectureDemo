package com.jeisundev.architecturedemo.core.status

sealed class Resource<T>(
    private val data: T? = null,
    private val throwable: Throwable? = null
) {
    class Loading<T> : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>(throwable = error)
    data class Success<T>(val response: T) : Resource<T>(data = response)
}