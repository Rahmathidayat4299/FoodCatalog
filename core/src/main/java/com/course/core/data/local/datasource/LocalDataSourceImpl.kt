package com.course.core.data.local.datasource

import com.course.core.data.local.RecipesEntity
import com.course.core.data.local.dao.RecipesDao
import kotlinx.coroutines.flow.firstOrNull

/**
 *hrahm,12/05/2024, 09:15
 **/

interface LocalDataSource {
    suspend fun getRecipes(): List<RecipesEntity>
    suspend fun saveRecipes(recipes: RecipesEntity): Long
    suspend fun deleteRecipes(recipes: RecipesEntity): Int
    suspend fun isRecipeSaved(recipeId: Int?): Boolean
}

class LocalDataSourceImpl(
    private val dao: RecipesDao
) : LocalDataSource {
    override suspend fun getRecipes(): List<RecipesEntity> = dao.getAllCarts()

    override suspend fun saveRecipes(recipes: RecipesEntity) = dao.insertCart(recipes)

    override suspend fun deleteRecipes(recipes: RecipesEntity) = dao.deleteCart(recipes)
    override suspend fun isRecipeSaved(recipeId: Int?): Boolean {
        if (recipeId == null) return false
        val recipe = dao.getCartById(recipeId).firstOrNull()
        return recipe != null
    }

}