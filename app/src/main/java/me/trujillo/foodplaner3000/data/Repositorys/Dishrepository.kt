package me.trujillo.foodplaner3000.data.Repositorys

import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.dao.*
import me.trujillo.foodplaner3000.data.db.entities.*
import me.trujillo.foodplaner3000.data.enums.Unit1

class DishRepository(
    private val dishDao: DishDao,
    private val categoryDao: CategoryDao,
    private val ingredientDao: IngredientDao,
    private val relationsDao: DishRelationsDao
) {

    val allDishes: Flow<List<Dish>> = dishDao.getAllDishes()

    suspend fun deleteDish(dish:Dish){
        dishDao.deleteDish(dish)
    }
    fun getDishById(id: Int) = dishDao.getDishById(id)


    fun getAllIngredientsForAllDishes() = relationsDao.getAllIngredientsForAllDishes()

    fun getAllCategoriesForAllDishes() = relationsDao.getAllCategoriesForAllDishes()

    // --------------------------------------
    // LADE-KATEGORIEN
    // --------------------------------------
    suspend fun getCategoriesForDish(dishId: Int): List<String> {
        return relationsDao.getCategoriesForDish(dishId)
    }

    fun getCategoriesForDishFlow(dishId: Int) =
        relationsDao.getCategoriesForDishFlow(dishId)


    // --------------------------------------
    // LADE-ZUTATEN
    // --------------------------------------
    fun getIngredientsForDishFlow(dishId: Int): Flow<List<IngredientWithAmount>> =
        relationsDao.getIngredientsForDishFlow(dishId)


    // --------------------------------------
    // UPDATE DISH + KATEGORIEN + ZUTATEN
    // --------------------------------------
    suspend fun updateDish(
        dish: Dish,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>
    ) {
        // 1. Dish updaten
        dishDao.updateDish(dish)

        val dishId = dish.id

        // 2. Alte Relations löschen
        relationsDao.deleteDishCategories(dishId)
        relationsDao.deleteDishIngredients(dishId)

        // 3. Kategorien neu speichern
        for (cat in categories) {
            val categoryId =
                categoryDao.getCategoryIdByName(cat) ?: categoryDao.insertCategory(Category(name = cat)).toInt()

            relationsDao.insertDishCategory(
                DishCategory(
                    dishId = dishId,
                    categoryId = categoryId
                )
            )
        }

        // 4. Zutaten neu speichern
        for ((name, qtyUnit) in ingredients) {
            val (qty, unit) = qtyUnit

            val ingId =
                ingredientDao.getIngredientIdByName(name)
                    ?: ingredientDao.insertIngredient(Ingredient(name = name)).toInt()

            relationsDao.insertDishIngredient(
                DishIngredient(
                    dishId = dishId,
                    ingredientId = ingId,
                    quantity = qty,
                    unit = unit
                )
            )
        }
    }

    suspend fun createDish(
        name: String,
        description: String?,
        instructions: String?,
        categories: List<String>,
        ingredients: List<Pair<String, Pair<Double, Unit1>>>,
        imagePath: String?
    ) {
        // 1) Dish speichern
        val dishId = dishDao.insertDish(
            Dish(
                name = name,
                description = description,
                instructions = instructions,
                imagePath = imagePath
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
