package me.trujillo.foodplaner3000

import android.content.ClipData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class ItemViewModel : ViewModel() {

    private var _items = mutableStateListOf(
        Item("Eier", 14, Unit1.x),
        Item("Mehl", 50, Unit1.g),
        Item("Fleisch", 200, Unit1.g),
        Item("Butter", 150, Unit1.g),
        Item("Brokkoli", 1,Unit1.x),
        Item("Mandeln", 500, Unit1.g),
        Item("Chilli", 50, Unit1.g),
        Item("Milch", 1, Unit1.l),
        Item("Curry", 20, Unit1.g),
        Item("Salat", 500, Unit1.g),
    )

    val items: List<Item> get() = _items

    fun removeItem(index: Int) {
        if (index in _items.indices) {
            _items.removeAt(index)
        }
    }
    fun addItem(name: String, quantity: Int, unit: Unit1 = Unit1.x) {
        _items.add(Item(name, quantity, unit))
    }
    fun addItemAt(index: Int, item: Item) {
        if (index in 0.._items.size) {
            _items.add(index, item)
        }
    }
    fun onEditItem(oldItem: Item, newName: String, newQuantity: Int, newunit: Unit1 = Unit1.x) {
        val index = _items.indexOf(oldItem)
        if (index != -1) {
            _items[index] = Item(newName, newQuantity, newunit)
        }
    }
}

data class DishItem(
    val item: Item,
    val quantity: Int,
    val unit: Unit1
)


class GerichteViewModel : ViewModel(){
val gerichte = mutableStateListOf(
    Dish(
        name = "Spaghetti Bolognese",
        items = listOf(
            DishItem(Item("Spaghetti", 500, Unit1.g), 500, Unit1.g),
            DishItem(Item("Hackfleisch", 250, Unit1.g), 250, Unit1.g),
            DishItem(Item("Tomatensauce", 200, Unit1.ml), 200, Unit1.ml)
        ),
        categories = listOf(Category("Italienisch"), Category("Pasta"))

    ),
    Dish(
        name = "Caesar Salad",
        items = listOf(
            DishItem(Item("Römersalat", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Hähnchenbrust", 150, Unit1.g), 150, Unit1.g),
            DishItem(Item("Parmesan", 30, Unit1.g), 30, Unit1.g)
        ),
        categories = listOf(Category("Salat"), Category("Gesund"))

    ),
    Dish(
        name = "Pizza Margherita",
        items = listOf(
            DishItem(Item("Pizzateig", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Tomatensauce", 100, Unit1.ml), 100, Unit1.ml),
            DishItem(Item("Mozzarella", 125, Unit1.g), 125, Unit1.g)
        ),
        categories = listOf(Category("Italienisch"), Category("Pizza"))
    ),
    Dish(
        name = "Sushi",
        items = listOf(
            DishItem(Item("Sushireis", 200, Unit1.g), 200, Unit1.g),
            DishItem(Item("Lachs", 150, Unit1.g), 150, Unit1.g),
            DishItem(Item("Nori-Blätter", 5, Unit1.x), 5, Unit1.x)
        ),
        categories = listOf(Category("Japanisch"), Category("Fisch"))
    ),
    Dish(
        name = "Burger",
        items = listOf(
            DishItem(Item("Burgerbrötchen", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Rindfleischpatty", 150, Unit1.g), 150, Unit1.g),
            DishItem(Item("Käse", 1, Unit1.x), 1, Unit1.x)
        ),
        categories = listOf(Category("Fast Food"), Category("Amerikanisch"))
    ),
    Dish(
        name = "Lasagne",
        items = listOf(
            DishItem(Item("Lasagneplatten", 6, Unit1.x), 6, Unit1.x),
            DishItem(Item("Hackfleisch", 250, Unit1.g), 250, Unit1.g),
            DishItem(Item("Béchamelsauce", 200, Unit1.ml), 200, Unit1.ml)
        ),
        categories = listOf(Category("Italienisch"), Category("Ofengericht"))
    ),
    Dish(
        name = "Tacos",
        items = listOf(
            DishItem(Item("Tortillas", 3, Unit1.x), 3, Unit1.x),
            DishItem(Item("Hähnchenfleisch", 200, Unit1.g), 200, Unit1.g),
            DishItem(Item("Guacamole", 100, Unit1.ml), 100, Unit1.ml)
        ),
        categories = listOf(Category("Mexikanisch"), Category("Fast Food"))
    ),
    Dish(
        name = "Käsespätzle",
        items = listOf(
            DishItem(Item("Spätzle", 300, Unit1.g), 300, Unit1.g),
            DishItem(Item("Käse", 150, Unit1.g), 150, Unit1.g),
            DishItem(Item("Röstzwiebeln", 50, Unit1.g), 50, Unit1.g)
        ),
        categories = listOf(Category("Deutsch"), Category("Vegetarisch"))
    ),
    Dish(
        name = "Currywurst mit Pommes",
        items = listOf(
            DishItem(Item("Bratwurst", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Pommes", 200, Unit1.g), 200, Unit1.g),
            DishItem(Item("Currysauce", 50, Unit1.ml), 50, Unit1.ml)
        ),
        categories = listOf(Category("Deutsch"), Category("Fast Food"))
    ),
    Dish(
        name = "Ratatouille",
        items = listOf(
            DishItem(Item("Zucchini", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Paprika", 1, Unit1.x), 1, Unit1.x),
            DishItem(Item("Aubergine", 1, Unit1.x), 1, Unit1.x)
        ),
        categories = listOf(Category("Französisch"), Category("Vegetarisch"))
    )
)}

