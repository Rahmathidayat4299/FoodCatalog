package com.course.core.data.remote

import android.content.Context
import com.course.core.utils.Constants
import com.course.core.utils.UiState
import com.course.core.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response


/**
 *hrahm,23/04/2024, 18:50
 **/
inline fun <reified T> toResultFlow(
    context: Context,
    crossinline call: suspend () -> Response<T>?
): Flow<UiState<T>> {
    return flow {
        val isInternetConnected = Utils.hasInternetConnection(context)
        if (isInternetConnected) {
            emit(UiState.Loading)
            try {
                val c = call()
                if (c?.isSuccessful == true && c.body() != null) {
                    emit(UiState.Success(c.body()))
                } else {
                    if (c != null) {
                        emit(UiState.Error(c.message()))
                    }
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.toString()))
            }
        } else {
            emit(UiState.Error(Constants.API_INTERNET_MESSAGE))
        }
    }.flowOn(Dispatchers.IO)
}