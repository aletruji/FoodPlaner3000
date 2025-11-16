package me.trujillo.foodplaner3000.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository

class DishViewModelFactory(
    private val repo: DishRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DishViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
