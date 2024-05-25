package com.course.core.data

import android.content.Context
import com.course.core.data.local.RecipesEntity
import com.course.core.data.local.datasource.LocalDataSource
import com.course.core.data.remote.RemoteDataSource
import com.course.core.data.remote.toResultFlow
import com.course.core.utils.UiState
import kotlinx.coroutines.flow.Flow

/**
 *hrahm,25/05/2024, 11:25
 **/
interface FoodRepository {
    suspend fun getRecipes(context: Context): Flow<UiState<Receipes>>
    suspend fun getRecipesDetail(context: Context, id: Int?): Flow<UiState<Receipes.Recipe>>
    fun setCurrentRecipes(recipesEntity: RecipesEntity)
    suspend fun saveRecipes(recipesEntity: RecipesEntity)
    suspend fun getRecipes(): List<RecipesEntity>
    suspend fun deleteRecipes(recipesEntity: RecipesEntity)
    suspend fun isRecipeSaved(recipeId: Int?): Boolean
}

class FoodRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : FoodRepository {

    private var currentRecipesEntity: RecipesEntity? = null

    override suspend fun getRecipes(context: Context): Flow<UiState<Receipes>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipes()
        }
    }

    override suspend fun getRecipesDetail(
        context: Context,
        id: Int?
    ): Flow<UiState<Receipes.Recipe>> {
        return toResultFlow(context) {
            remoteDataSource.getRecipeDetail(id)
        }
    }

    override fun setCurrentRecipes(recipesEntity: RecipesEntity) {
        currentRecipesEntity = recipesEntity
    }

    override suspend fun saveRecipes(recipesEntity: RecipesEntity) {
        localDataSource.saveRecipes(recipesEntity)
    }

    override suspend fun getRecipes(): List<RecipesEntity> {
        return localDataSource.getRecipes()
    }

    override suspend fun deleteRecipes(recipesEntity: RecipesEntity) {
        localDataSource.deleteRecipes(recipesEntity)
    }

    override suspend fun isRecipeSaved(recipeId: Int?): Boolean {
        // Mengambil resep dari database lokal berdasarkan ID
        val recipe = recipeId?.let { localDataSource.isRecipeSaved(recipeId) }
        // Mengembalikan true jika resep ditemukan, false jika tidak
        return recipe != null
    }
}
