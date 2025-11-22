package me.trujillo.foodplaner3000.Viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.trujillo.foodplaner3000.data.Repositorys.DishRepository
import me.trujillo.foodplaner3000.data.db.entities.Dish
import me.trujillo.foodplaner3000.data.enums.Unit1
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn


class DishViewModel(
    private val repo: DishRepository
) : ViewModel() {


    val dishes: StateFlow<List<Dish>>
        get() = repo.allDishes.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    // --------------------------------------
    // LOAD FUNCTIONS
    // --------------------------------------
    fun getCategoriesForDish(dishId: Int) =
        repo.getCategoriesForDishFlow(dishId)



    fun getIngredientsForDish(dishId: Int) =
        repo.getIngredientsForDishFlow(dishId)


    // --------------------------------------
    // UPDATE DISH
    // --------------------------------------
    fun updateDish(
        updatedDish: Dish,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>,
        imagePath: String?
    ) {
        viewModelScope.launch {
            repo.updateDish(updatedDish, categories, ingredients)
        }
    }

    fun addDish(
        name: String,
        description: String?,
        instructions: String?,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>,
        imagePath: String?
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





    fun getRandomDishes(list: List<Dish>,count: Int): List<Dish> {

        println(list.shuffled().take(count))
        return list.shuffled().take(count)

    }



    fun deleteDish(dish: Dish  ){
        viewModelScope.launch {
            repo.deleteDish(dish)
        }
    }
}
