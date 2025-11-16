package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.trujillo.foodplaner3000.data.db.entities.Ingredient

@Dao
interface IngredientDao {

    @Insert
    suspend fun insertIngredient(ingredient: Ingredient): Long

    @Query("SELECT * FROM Ingredient ORDER BY name ASC")
    suspend fun getAllIngredients(): List<Ingredient>

    @Query("SELECT id FROM Ingredient WHERE name = :name LIMIT 1")
    suspend fun getIngredientIdByName(name: String): Int?
}
