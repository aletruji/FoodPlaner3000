package me.trujillo.foodplaner3000.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.trujillo.foodplaner3000.data.db.entities.CategoryWithDishId
import me.trujillo.foodplaner3000.data.db.entities.DishCategory
import me.trujillo.foodplaner3000.data.db.entities.DishIngredient
import me.trujillo.foodplaner3000.data.db.entities.IngredientWithAmount
import me.trujillo.foodplaner3000.data.db.entities.IngredientWithDishId
import me.trujillo.foodplaner3000.data.enums.Unit1

@Dao
interface DishRelationsDao {

    @Query("""
        SELECT Category.name FROM Category
        INNER JOIN dish_category ON Category.id = dish_category.categoryId
        WHERE dish_category.dishId = :dishId
    """)
    suspend fun getCategoriesForDish(dishId: Int): List<String>

    @Query("""
    SELECT Category.name FROM Category
    INNER JOIN dish_category ON Category.id = dish_category.categoryId
    WHERE dish_category.dishId = :dishId
""")
    fun getCategoriesForDishFlow(dishId: Int): Flow<List<String>>


    @Query("""
    SELECT Ingredient.name, DishIngredient.quantity, DishIngredient.unit
    FROM Ingredient
    INNER JOIN DishIngredient ON Ingredient.id = DishIngredient.ingredientId
    WHERE DishIngredient.dishId = :dishId
""")
    fun getIngredientsForDishFlow(dishId: Int): Flow<List<IngredientWithAmount>>



    @Query("""
        SELECT Ingredient.name, DishIngredient.quantity, DishIngredient.unit
        FROM Ingredient
        INNER JOIN DishIngredient ON Ingredient.id = DishIngredient.ingredientId
        WHERE DishIngredient.dishId = :dishId
    """)
    suspend fun getIngredientsForDish(dishId: Int): List<IngredientWithAmount>


    // Erst die Relations löschen
    @Query("DELETE FROM dish_category WHERE dishId = :dishId")
    suspend fun deleteDishCategories(dishId: Int)

    @Query("DELETE FROM DishIngredient WHERE dishId = :dishId")
    suspend fun deleteDishIngredients(dishId: Int)
    @Insert
    suspend fun insertDishCategory(rel: DishCategory)

    @Insert
    suspend fun insertDishIngredient(rel: DishIngredient)

    @Delete
    suspend fun deleteDishCategory(rel: DishCategory)

    @Delete
    suspend fun deleteDishIngredient(rel: DishIngredient)

    @Query("SELECT dishId, name FROM Ingredient JOIN dishingredient ON ingredient.id = dishingredient.ingredientId")
    fun getAllIngredientsForAllDishes(): Flow<List<IngredientWithDishId>>

    @Query("""
    SELECT 
        dish_category.dishId AS dishId, 
        Category.name AS categoryName
    FROM Category
    JOIN dish_category ON Category.id = dish_category.categoryId
""")
    fun getAllCategoriesForAllDishes(): Flow<List<CategoryWithDishId>>

}

