package com.course.core.data.domain

import android.content.Context
import com.course.core.data.FoodRepository
import com.course.core.data.Receipes
import com.course.core.data.local.RecipesEntity
import com.course.core.utils.UiState
import kotlinx.coroutines.flow.Flow

/**
 *hrahm,25/05/2024, 11:33
 **/
interface FoodUseCase {

    suspend fun getRecipes(context: Context): Flow<UiState<Receipes>>
    suspend fun getRecipeDetail(context: Context, id: Int?): Flow<UiState<Receipes.Recipe>>
    fun setCurrentRecipe(recipesEntity: RecipesEntity)
    suspend fun saveRecipe(recipesEntity: RecipesEntity)
    suspend fun getSavedRecipes(): List<RecipesEntity>
    suspend fun deleteRecipe(recipesEntity: RecipesEntity)

    suspend fun isRecipeSaved(recipeId: Int?): Boolean
}

class FoodInteractor(
    private val repository: FoodRepository
) : FoodUseCase {

    override suspend fun getRecipes(context: Context): Flow<UiState<Receipes>> {
        return repository.getRecipes(context)
    }

    override suspend fun getRecipeDetail(
        context: Context,
        id: Int?
    ): Flow<UiState<Receipes.Recipe>> {
        return repository.getRecipesDetail(context, id)
    }

    override fun setCurrentRecipe(recipesEntity: RecipesEntity) {
        repository.setCurrentRecipes(recipesEntity)
    }

    override suspend fun saveRecipe(recipesEntity: RecipesEntity) {
        repository.saveRecipes(recipesEntity)
    }

    override suspend fun getSavedRecipes(): List<RecipesEntity> {
        return repository.getRecipes()
    }

    override suspend fun deleteRecipe(recipesEntity: RecipesEntity) {
        repository.deleteRecipes(recipesEntity)
    }

    override suspend fun isRecipeSaved(recipeId: Int?): Boolean = repository.isRecipeSaved(recipeId)

}
