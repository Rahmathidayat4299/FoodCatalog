package com.course.core.utils
import com.course.core.data.Receipes
import com.course.core.data.local.RecipesEntity
import com.google.gson.Gson
/**
 *hrahm,16/05/2024, 08:07
 **/




object RecipeMapper {

    fun mapToEntity(recipe: Receipes.Recipe): RecipesEntity {
        return RecipesEntity(
            id = recipe.id ?: 0,
            name = recipe.name ?: "",
            ingredients = Gson().toJson(recipe.ingredients),
            instructions = Gson().toJson(recipe.instructions),
            prepTimeMinutes = recipe.prepTimeMinutes ?: 0,
            cookTimeMinutes = recipe.cookTimeMinutes ?: 0,
            servings = recipe.servings ?: 0,
            difficulty = recipe.difficulty ?: "",
            cuisine = recipe.cuisine ?: "",
            caloriesPerServing = recipe.caloriesPerServing ?: 0,
            tags = Gson().toJson(recipe.tags),
            userId = recipe.userId ?: 0,
            image = recipe.image ?: "",
            rating = recipe.rating ?: 0.0,
            reviewCount = recipe.reviewCount ?: 0,
            mealType = Gson().toJson(recipe.mealType)
        )
    }

    fun mapFromEntity(recipesEntity: RecipesEntity): Receipes.Recipe {
        return Receipes.Recipe(
            id = recipesEntity.id,
            name = recipesEntity.name,
            ingredients = Gson().fromJson(recipesEntity.ingredients, Array<String>::class.java)
                .toList(),
            instructions = Gson().fromJson(recipesEntity.instructions, Array<String>::class.java)
                .toList(),
            prepTimeMinutes = recipesEntity.prepTimeMinutes,
            cookTimeMinutes = recipesEntity.cookTimeMinutes,
            servings = recipesEntity.servings,
            difficulty = recipesEntity.difficulty,
            cuisine = recipesEntity.cuisine,
            caloriesPerServing = recipesEntity.caloriesPerServing,
            tags = Gson().fromJson(recipesEntity.tags, Array<String>::class.java).toList(),
            userId = recipesEntity.userId,
            image = recipesEntity.image,
            rating = recipesEntity.rating,
            reviewCount = recipesEntity.reviewCount,
            mealType = Gson().fromJson(recipesEntity.mealType, Array<String>::class.java).toList()
        )
    }
}
