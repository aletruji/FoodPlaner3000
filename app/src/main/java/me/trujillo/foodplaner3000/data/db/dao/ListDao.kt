package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList

@Dao
interface ShoppingListDao{
    @Insert
    suspend fun insert(shoppingList: ShoppingList)
    @Delete
    suspend fun delete(shoppingList: ShoppingList)
    @Update
    suspend fun update(shoppingList: ShoppingList)
    @Query("SELECT * FROM SHOPPINGLIST")
    fun getAll(): Flow<List<ShoppingList>>
}