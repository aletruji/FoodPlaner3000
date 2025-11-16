package me.trujillo.foodplaner3000.data.Repositorys

import me.trujillo.foodplaner3000.data.db.dao.*
import me.trujillo.foodplaner3000.data.db.entities.*
import me.trujillo.foodplaner3000.data.enums.Unit1

class DishRepository(
    private val dishDao: DishDao,
    private val categoryDao: CategoryDao,
    private val ingredientDao: IngredientDao,
    private val relationsDao: DishRelationsDao
) {

    val allDishes = dishDao.getAllDishes()

    suspend fun deleteDish(dish:Dish){
        dishDao.deleteDish(dish)
    }


    suspend fun createDish(
        name: String,
        description: String?,
        instructions: String?,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>
    ) {
        // 1) Dish speichern
        val dishId = dishDao.insertDish(
            Dish(
                name = name,
                description = description,
                instructions = instructions
            )
        ).toInt()

        // 2) Kategorien speichern bzw. holen
        categories.forEach { categoryName ->
            val categoryId = categoryDao.getCategoryIdByName(categoryName)
                ?: categoryDao.insertCategory(Category(name = categoryName)).toInt()

            relationsDao.insertDishCategory(
                DishCategory(
                    dishId = dishId,
                    categoryId = categoryId
                )
            )
        }

        // 3) Zutaten speichern bzw. holen
        ingredients.forEach { (ingredientName, quantityUnit) ->
            val (quantity, unit) = quantityUnit

            val ingredientId = ingredientDao.getIngredientIdByName(ingredientName)
                ?: ingredientDao.insertIngredient(Ingredient(name = ingredientName)).toInt()

            relationsDao.insertDishIngredient(
                DishIngredient(
                    dishId = dishId,
                    ingredientId = ingredientId,
                    quantity = quantity,
                    unit = unit
                )
            )
        }
    }
}
