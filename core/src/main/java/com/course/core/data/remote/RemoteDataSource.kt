package com.course.core.data.remote

/**
 *hrahm,23/04/2024, 18:49
 **/
class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getReceipes() = apiService.getReceipes()
    suspend fun getReceipesDetail(id: Int?) = apiService.getReceipeDetails(id)
}