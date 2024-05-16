package com.course.core.data.local

/**
 *hrahm,12/05/2024, 08:46
 **/
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: String, // JSON string of ingredients
    @ColumnInfo(name = "instructions") val instructions: String, // JSON string of instructions
    @ColumnInfo(name = "prepTimeMinutes") val prepTimeMinutes: Int,
    @ColumnInfo(name = "cookTimeMinutes") val cookTimeMinutes: Int,
    @ColumnInfo(name = "servings") val servings: Int,
    @ColumnInfo(name = "difficulty") val difficulty: String,
    @ColumnInfo(name = "cuisine") val cuisine: String,
    @ColumnInfo(name = "caloriesPerServing") val caloriesPerServing: Int,
    @ColumnInfo(name = "tags") val tags: String, // JSON string of tags
    @ColumnInfo(name = "userId") val userId: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "rating") val rating: Double,
    @ColumnInfo(name = "reviewCount") val reviewCount: Int,
    @ColumnInfo(name = "mealType") val mealType: String // JSON string of meal types
)