package me.trujillo.foodplaner3000

import android.content.ClipData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class ItemViewModel : ViewModel() {

    private var _items = mutableStateListOf(
        DishItem(Item("Eier", 14, Unit1.x), 14, Unit1.x),
        DishItem(Item("Mehl", 50, Unit1.g), 50, Unit1.g),
        DishItem(Item("Fleisch", 200, Unit1.g), 200, Unit1.g),
        DishItem(Item("Butter", 150, Unit1.g), 150, Unit1.g),
        DishItem(Item("Brokkoli", 1, Unit1.x), 1, Unit1.x),
        DishItem(Item("Mandeln", 500, Unit1.g), 500, Unit1.g),
        DishItem(Item("Chilli", 50, Unit1.g), 50, Unit1.g),
        DishItem(Item("Milch", 1, Unit1.l), 1, Unit1.l),
        DishItem(Item("Curry", 20, Unit1.g), 20, Unit1.g),
        DishItem(Item("Salat", 500, Unit1.g), 500, Unit1.g)
    )


    val items: List<DishItem> get() = _items

    fun removeItem(index: Int) {
        if (index in _items.indices) {
            _items.removeAt(index)
        }
    }
    fun addItem(name: String, quantity: Int, unit: Unit1 = Unit1.x) {
        val newItem = Item(name, quantity, unit)
        _items.add(DishItem(newItem, quantity, unit))
    }
    fun addItemAt(index: Int, item: Item) {
        if (index in 0.._items.size) {
            _items.add(index, DishItem(item, item.quantity, item.unit))
        }
    }
    fun onEditItem(oldDishItem: DishItem, newName: String, newQuantity: Int, newunit: Unit1 = Unit1.x) {
        val index = _items.indexOf(oldDishItem)
        if (index != -1) {
            val newItem = Item(newName, newQuantity, newunit)
            _items[index] = DishItem(newItem, newQuantity, newunit)
        }
    }

    fun addDishItem(restoredItem: DishItem) {
        if (!_items.contains(restoredItem)) {
            _items.add(restoredItem)
        }
    }

}



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
)

    fun addDish(dish: Dish) {

            gerichte.add(dish)


    }
}

val itemList = listOf(
    singleItem(name="Apfel", g_per_piece = 150, unit = Unit1.g),
    singleItem(name="Banane", g_per_piece = 120, unit = Unit1.g),
    singleItem(name="Milch", g_per_piece = null, unit = Unit1.ml),
    singleItem(name="Eier", g_per_piece = 50, unit = Unit1.x),
    singleItem(name="Brot", g_per_piece = 500, unit = Unit1.g),
    singleItem(name="Butter", g_per_piece = 250, unit = Unit1.g),
    singleItem(name="Käse", g_per_piece = 200, unit = Unit1.g),
    singleItem(name="Tomate", g_per_piece = 80, unit = Unit1.g),
    singleItem(name="Kartoffel", g_per_piece = 300, unit = Unit1.g),
    singleItem(name="Zwiebel", g_per_piece = 150, unit = Unit1.g),
    singleItem(name="Karotte", g_per_piece = 100, unit = Unit1.g),
    singleItem(name="Salat", g_per_piece = 400, unit = Unit1.g),
    singleItem(name="Joghurt", g_per_piece = 500, unit = Unit1.ml),
    singleItem(name="Schokolade", g_per_piece = 100, unit = Unit1.g),
    singleItem(name="Nüsse", g_per_piece = 200, unit = Unit1.g)
)