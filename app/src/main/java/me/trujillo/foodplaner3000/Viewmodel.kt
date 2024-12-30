package me.trujillo.foodplaner3000

import android.content.ClipData
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel


class ViewModel : ViewModel() {
    private var _items = mutableStateListOf(
        Item("Eier", 14, Unit.peace),
        Item("Mehl", 50, Unit.g),
        Item("Fleisch", 200, Unit.g),
        Item("Butter", 150, Unit.g),
        Item("Brokkoli", 1,Unit.peace),
        Item("Mandeln", 500, Unit.g),
        Item("Chilli", 50, Unit.g),
        Item("Milch", 1, Unit.l),
        Item("Curry", 20, Unit.g),
        Item("Salat", 500, Unit.g),
    )
    val items: List<Item> get() = _items

    fun removeItem(index: Int) {
        if (index in _items.indices) {
            _items.removeAt(index)
        }
    }
    fun addItem(name: String, quantity: Int, Unit: Unit = Unit.g) {
        _items.add(Item(name, quantity,Unit))
    }
    fun onEditItem(oldItem: Item, newName: String, newQuantity: Int) {
        val index = _items.indexOf(oldItem)
        if (index != -1) {
            _items[index] = ClipData.Item(newName, newQuantity)
        }
    }
}

