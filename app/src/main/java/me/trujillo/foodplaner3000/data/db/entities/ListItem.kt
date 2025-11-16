package me.trujillo.foodplaner3000.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.trujillo.foodplaner3000.data.enums.Unit1

@Entity
data class ShoppingList(
    @PrimaryKey(autoGenerate = true )val id: Int = 0,
    val name: String,
    val quantity: Int,
    val unit: Unit1
)

@Entity
data class Dish(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String?,
    val instructions: String?
)

@Entity
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity(
    tableName = "dish_category",
    primaryKeys = ["dishId", "categoryId"]
)
data class DishCategory(

    val dishId: Int,
    val categoryId: Int
)

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)

@Entity
data class DishIngredient(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dishId: Int,
    val ingredientId: Int,
    val quantity: Double?,
    val unit: Unit1
)



