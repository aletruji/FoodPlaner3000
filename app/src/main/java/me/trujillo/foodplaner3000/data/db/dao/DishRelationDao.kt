package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import me.trujillo.foodplaner3000.data.db.entities.DishCategory
import me.trujillo.foodplaner3000.data.db.entities.DishIngredient

@Dao
interface DishRelationsDao {

    @Insert
    suspend fun insertDishCategory(rel: DishCategory)

    @Insert
    suspend fun insertDishIngredient(rel: DishIngredient)

    @Delete
    suspend fun deleteDishCategory(rel: DishCategory)

    @Delete
    suspend fun deleteDishIngredient(rel: DishIngredient)
}
