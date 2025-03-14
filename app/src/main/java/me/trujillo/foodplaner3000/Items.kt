package me.trujillo.foodplaner3000

data class Item(var name: String, var quantity: Int, var unit: Unit1, var gperpiece: Int? = null) {

}
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

data class Dish(
    val name: String,
    val items: List<DishItem>,
    val categories: List<Category>,
    val imagePath: String? = null
)

data class DishItem(
    val item: Item,
    val amount: Int, // Menge
    val unit: Unit1 // Einheit
)
