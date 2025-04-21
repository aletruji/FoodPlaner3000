package me.trujillo.foodplaner3000

import android.content.ClipData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class ItemViewModel : ViewModel() {

    private var _items = mutableStateListOf(
        ShoppingItem(SingleItem("Eier", 14, Unit1.x), 14, Unit1.x),
        ShoppingItem(SingleItem("Mehl", 50, Unit1.g), 50, Unit1.g),
        ShoppingItem(SingleItem("Fleisch", 200, Unit1.g), 200, Unit1.g),
        ShoppingItem(SingleItem("Butter", 150, Unit1.g), 150, Unit1.g),
        ShoppingItem(SingleItem("Brokkoli", 1, Unit1.x), 1, Unit1.x),
        ShoppingItem(SingleItem("Mandeln", 500, Unit1.g), 500, Unit1.g),
        ShoppingItem(SingleItem("Chilli", 50, Unit1.g), 50, Unit1.g),
        ShoppingItem(SingleItem("Milch", 1, Unit1.l), 1, Unit1.l),
        ShoppingItem(SingleItem("Curry", 20, Unit1.g), 20, Unit1.g),
        ShoppingItem(SingleItem("Salat", 500, Unit1.g), 500, Unit1.g)
    )


    val items: List<ShoppingItem> get() = _items

    fun removeItem(index: Int) {
        if (index in _items.indices) {
            _items.removeAt(index)
        }
    }
    fun addItem(name: String, quantity: Int, unit: Unit1 = Unit1.x) {
        val newItem = ShoppingItem(SingleItem(name, defaultUnit  = unit), quantity, unit)
        _items.add(newItem)
    }
    fun addItemAt(index: Int, item: ShoppingItem) {
        if (index in 0.._items.size) {
            _items.add(index, item)
        }
    }
    fun onEditItem(oldItem: ShoppingItem, newName: String, newQuantity: Int, newUnit: Unit1 = Unit1.x) {
        val index = _items.indexOf(oldItem)
        if (index != -1) {
            val newItem = ShoppingItem(SingleItem(newName, defaultUnit  = newUnit), newQuantity, newUnit)
            _items[index] = newItem
        }
    }

    fun addDishItem(restoredItem: ShoppingItem) {
        if (!_items.contains(restoredItem)) {
            _items.add(restoredItem)
        }
    }

}



class GerichteViewModel : ViewModel() {

// nochmal ueberpruefen ob das alles passt


    private fun dishItem(name: String, quantity: Int, unit: Unit1): DishItem {
        return DishItem(SingleItem(name, defaultUnit = unit), quantity, unit)
    }

    val gerichte = mutableStateListOf(
        Dish(
            name = "Spaghetti Bolognese",
            items = listOf(
                dishItem("Spaghetti", 500, Unit1.g),
                dishItem("Hackfleisch", 250, Unit1.g),
                dishItem("Tomatensauce", 200, Unit1.ml)
            ),
            categories = listOf(Category("Italienisch"), Category("Pasta"))
        ),
        Dish(
            name = "Caesar Salad",
            items = listOf(
                dishItem("Römersalat", 1, Unit1.x),
                dishItem("Hähnchenbrust", 150, Unit1.g),
                dishItem("Parmesan", 30, Unit1.g)
            ),
            categories = listOf(Category("Salat"), Category("Gesund"))
        ),
        Dish(
            name = "Pizza Margherita",
            items = listOf(
                dishItem("Pizzateig", 1, Unit1.x),
                dishItem("Tomatensauce", 100, Unit1.ml),
                dishItem("Mozzarella", 125, Unit1.g)
            ),
            categories = listOf(Category("Italienisch"), Category("Pizza"))
        ),
        Dish(
            name = "Sushi",
            items = listOf(
                dishItem("Sushireis", 200, Unit1.g),
                dishItem("Lachs", 150, Unit1.g),
                dishItem("Nori-Blätter", 5, Unit1.x)
            ),
            categories = listOf(Category("Japanisch"), Category("Fisch"))
        ),
        Dish(
            name = "Burger",
            items = listOf(
                dishItem("Burgerbrötchen", 1, Unit1.x),
                dishItem("Rindfleischpatty", 150, Unit1.g),
                dishItem("Käse", 1, Unit1.x)
            ),
            categories = listOf(Category("Fast Food"), Category("Amerikanisch"))
        ),
        Dish(
            name = "Lasagne",
            items = listOf(
                dishItem("Lasagneplatten", 6, Unit1.x),
                dishItem("Hackfleisch", 250, Unit1.g),
                dishItem("Béchamelsauce", 200, Unit1.ml)
            ),
            categories = listOf(Category("Italienisch"), Category("Ofengericht"))
        ),
        Dish(
            name = "Tacos",
            items = listOf(
                dishItem("Tortillas", 3, Unit1.x),
                dishItem("Hähnchenfleisch", 200, Unit1.g),
                dishItem("Guacamole", 100, Unit1.ml)
            ),
            categories = listOf(Category("Mexikanisch"), Category("Fast Food"))
        ),
        Dish(
            name = "Käsespätzle",
            items = listOf(
                dishItem("Spätzle", 300, Unit1.g),
                dishItem("Käse", 150, Unit1.g),
                dishItem("Röstzwiebeln", 50, Unit1.g)
            ),
            categories = listOf(Category("Deutsch"), Category("Vegetarisch"))
        ),
        Dish(
            name = "Currywurst mit Pommes",
            items = listOf(
                dishItem("Bratwurst", 1, Unit1.x),
                dishItem("Pommes", 200, Unit1.g),
                dishItem("Currysauce", 50, Unit1.ml)
            ),
            categories = listOf(Category("Deutsch"), Category("Fast Food"))
        ),
        Dish(
            name = "Ratatouille",
            items = listOf(
                dishItem("Zucchini", 1, Unit1.x),
                dishItem("Paprika", 1, Unit1.x),
                dishItem("Aubergine", 1, Unit1.x)
            ),
            categories = listOf(Category("Französisch"), Category("Vegetarisch"))
        )
    )

    fun addDish(dish: Dish) {
        gerichte.add(dish)
    }
}


val itemList = listOf(
    SingleItem(name="Apfel", g_per_piece = 150, defaultUnit = Unit1.g),
    SingleItem(name="Banane", g_per_piece = 120, defaultUnit = Unit1.g),
    SingleItem(name="Milch", g_per_piece = 1000, defaultUnit = Unit1.ml),
    SingleItem(name="Eier", g_per_piece = 50, defaultUnit = Unit1.x),
    SingleItem(name="Brot", g_per_piece = 500, defaultUnit = Unit1.g),
    SingleItem(name="Butter", g_per_piece = 250, defaultUnit = Unit1.g),
    SingleItem(name="Käse", g_per_piece = 200, defaultUnit = Unit1.g),
    SingleItem(name="Tomate", g_per_piece = 80, defaultUnit = Unit1.g),
    SingleItem(name="Kartoffel", g_per_piece = 300, defaultUnit = Unit1.g),
    SingleItem(name="Zwiebel", g_per_piece = 150, defaultUnit = Unit1.g),
    SingleItem(name="Karotte", g_per_piece = 100, defaultUnit = Unit1.g),
    SingleItem(name="Salat", g_per_piece = 400, defaultUnit = Unit1.g),
    SingleItem(name="Joghurt", g_per_piece = 500, defaultUnit = Unit1.ml),
    SingleItem(name="Schokolade", g_per_piece = 100, defaultUnit = Unit1.g),
    SingleItem(name="Nüsse", g_per_piece = 200, defaultUnit = Unit1.g)
)