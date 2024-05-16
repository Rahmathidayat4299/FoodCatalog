package com.course.core.utils

/**
 *hrahm,23/04/2024, 18:53
 **/
sealed class UiState<out T> {

    data class Success<T>(val data: T?) : UiState<T>()

    data class Error<T>(val message: String) : UiState<T>()

    object Loading : UiState<Nothing>()

}