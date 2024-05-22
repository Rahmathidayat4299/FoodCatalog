package com.course.core.data.local.datasource

import com.course.core.data.local.RecipesEntity
import com.course.core.data.local.dao.RecipesDao

/**
 *hrahm,12/05/2024, 09:15
 **/
class LocalDataSource(
    private val dao: RecipesDao
) {
    suspend fun getRecipes(): List<RecipesEntity> = dao.getAllCarts()
    suspend fun saveRecipes(recipes: RecipesEntity) = dao.insertCart(recipes)
    suspend fun deleteRecipes(recipes: RecipesEntity) = dao.deleteCart(recipes)
}