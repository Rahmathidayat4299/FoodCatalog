package com.course.core.data.local

import com.course.core.data.local.datasource.LocalDataSource
import com.course.core.utils.RecipeMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList

/**
 *hrahm,12/05/2024, 09:25
 **/
class CartRepository(
    private val localDataSource: LocalDataSource,
) {
    private var currentRecipesEntity: RecipesEntity? = null
    fun setCurrentRecipes(recipesEntity: RecipesEntity) {
        currentRecipesEntity = recipesEntity
    }
    suspend fun saveRecipes(recipesEntity: RecipesEntity) {
        localDataSource.saveRecipes(recipesEntity)
    }

    suspend fun getRecipes():List<RecipesEntity> {
        return localDataSource.getRecipes()
    }

    suspend fun deleteRecipes(recipesEntity: RecipesEntity) {
        localDataSource.deleteRecipes(recipesEntity)
    }
}