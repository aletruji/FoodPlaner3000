package me.trujillo.foodplaner3000.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.trujillo.foodplaner3000.data.Repositorys.ShoppingListRepository

class ShoppingViewModelFactory(
    private val repo: ShoppingListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
            return ShoppingViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
