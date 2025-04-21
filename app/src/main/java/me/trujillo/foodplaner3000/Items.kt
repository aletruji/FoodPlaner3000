package me.trujillo.foodplaner3000

data class ShoppingItem(
    val item: SingleItem,
    val quantity: Int,
    val unit: Unit1 )


enum class Unit1 (val conversionFactorToBase: Int) {
    g(1), kg(1000),
    l(1000), ml(1),
    EL(15), TL(5),
    x( 1)
}

data class Category(
    val name: String,
    val description: String? = null
)
data class SingleItem(
    val name: String,
    val g_per_piece: Int? = null,
    val defaultUnit: Unit1 = Unit1.x
)

data class Dish(
    val name: String,
    val items: List<DishItem>,
    val categories: List<Category>,
    val imagePath: String? = null
)

data class DishItem(
    val base: SingleItem,
    val quantity: Int,
    val unit: Unit1
)
