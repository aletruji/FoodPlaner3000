package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.entities.Category
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

    @Query("SELECT id FROM Dish WHERE name = :name LIMIT 1")
    fun getDishIdByName(name: String): Int?

    @Dao
    interface CategoryDao {

        @Query("SELECT id FROM Category WHERE name = :name LIMIT 1")
        fun getCategoryIdByName(name: String): Int?

        @Insert
        fun insertCategory(category: Category): Long


        @Transaction
        fun insertCategoryIfNotExists(name: String): Int {
            val existingId = getCategoryIdByName(name)
            if (existingId != null) return existingId
            return insertCategory(Category(name = name)).toInt()
        }
    }




}