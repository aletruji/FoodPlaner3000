package me.trujillo.foodplaner3000.Viewmodels

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import me.trujillo.foodplaner3000.data.Repositorys.ShoppingListRepository
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList


class ShoppingViewModel(private val repo: ShoppingListRepository) : ViewModel() {
    val items: Flow<List<ShoppingList>> = repo.allItems

    fun addItem(item: ShoppingList) = viewModelScope.launch { repo.add(item) }
    fun removeItem(item: ShoppingList) = viewModelScope.launch { repo.remove(item) }
    fun updateItem(item: ShoppingList) = viewModelScope.launch { repo.update(item) }
}
