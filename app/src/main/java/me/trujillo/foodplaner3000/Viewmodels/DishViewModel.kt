package me.trujillo.foodplaner3000.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.entities.Dish
import me.trujillo.foodplaner3000.data.enums.Unit1

class DishViewModel(
    private val repo: DishRepository
) : ViewModel() {




    val dishes = repo.allDishes
    fun addDish(
        name: String,
        description: String?,
        instructions: String?,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>
    ) {
        viewModelScope.launch {
            repo.createDish(
                name = name,
                description = description,
                instructions = instructions,
                categories = categories,
                ingredients = ingredients
            )


        }
    }

    fun deleteDish(dish: Dish  ){
        viewModelScope.launch {
            repo.deleteDish(dish)
        }
    }
}
