package me.trujillo.foodplaner3000.data.Repositorys

import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.dao.ShoppingListDao
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList

class ShoppingListRepository(private val dao: ShoppingListDao) {
    val allItems: Flow<List<ShoppingList>> = dao.getAll()

    suspend fun add(item: ShoppingList) = dao.insert(item)
    suspend fun remove(item: ShoppingList) = dao.delete(item)
    suspend fun update(item: ShoppingList) = dao.update(item)
}
