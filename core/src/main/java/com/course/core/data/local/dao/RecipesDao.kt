package com.course.core.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.course.core.data.local.RecipesEntity
import kotlinx.coroutines.flow.Flow

/**
 *hrahm,12/05/2024, 09:01
 **/
@Dao
interface RecipesDao {

    @Query("SELECT * FROM recipes")
    fun getAllCarts(): List<RecipesEntity>

    @Query("SELECT * FROM recipes WHERE id == :recipesId")
    fun getCartById(recipesId: Int): Flow<RecipesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: RecipesEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(cart: List<RecipesEntity>)

    @Delete
    suspend fun deleteCart(cart: RecipesEntity): Int

    @Update
    suspend fun updateCart(cart: RecipesEntity): Int

    @Query("DELETE FROM recipes")
    suspend fun deleteAll()
}