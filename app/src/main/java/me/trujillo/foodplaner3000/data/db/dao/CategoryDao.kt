package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.trujillo.foodplaner3000.data.db.entities.Category

@Dao
interface CategoryDao{

    @Insert
    suspend fun insertCategory(category: Category):Long

    @Query("SELECT * FROM Category ORDER BY name ASC")
    suspend fun getAllCategories(): List<Category>

    @Query("SELECT id FROM Category WHERE name = :name LIMIT 1")
    suspend fun getCategoryIdByName(name: String): Int?


}