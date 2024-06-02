package com.course.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.course.core.data.local.dao.RecipesDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

/**
 *hrahm,12/05/2024, 09:06
 **/
@Database(
    entities = [RecipesEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao

    companion object {
        const val DATABASE_NAME = "recipes_db"

        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
        }
    }
}