package com.course.core.data

import android.content.Context
import com.course.core.data.remote.RemoteDataSourceImpl
import com.course.core.data.remote.toResultFlow
import com.course.core.utils.UiState
import kotlinx.coroutines.flow.Flow

/**
 *hrahm,23/04/2024, 18:55
 **/
class Repository(private val remoteDataSource: RemoteDataSourceImpl) {

    suspend fun getReceipes(context: Context): Flow<UiState<Receipes>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipes()
        }
    }

    suspend fun getReceipesDetail(context: Context, id: Int?): Flow<UiState<Receipes.Recipe>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipeDetail(id)
        }
    }

}