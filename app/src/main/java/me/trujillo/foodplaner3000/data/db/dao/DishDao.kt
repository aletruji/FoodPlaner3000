package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.entities.Dish

@Dao
interface DishDao{
    @Insert
    suspend fun insertDish(dish: Dish):Long
    @Delete
    suspend fun deleteDish(dish:Dish)
    @Update
    suspend fun updateDish(dish:Dish)

    @Query("SELECT * FROM Dish")
    fun getAllDishes(): Flow<List<Dish>>

    @Query("SELECT * FROM Dish WHERE id = :id")
    fun getDishById(id: Int): Flow<Dish>


}