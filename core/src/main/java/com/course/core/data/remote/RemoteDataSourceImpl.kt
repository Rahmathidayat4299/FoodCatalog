package com.course.core.data.remote

import com.course.core.data.Receipes
import retrofit2.Response

/**
 *hrahm,23/04/2024, 18:49
 **/

interface RemoteDataSource{
    suspend fun getRecipes(): Response<Receipes>
    suspend fun getRecipeDetail(id: Int?): Response<Receipes.Recipe>
}
class RemoteDataSourceImpl(private val apiService: ApiService): RemoteDataSource {

    override suspend fun getRecipes(): Response<Receipes> = apiService.getReceipes()
    override suspend fun getRecipeDetail(id: Int?): Response<Receipes.Recipe>  = apiService.getReceipeDetails(id)
}