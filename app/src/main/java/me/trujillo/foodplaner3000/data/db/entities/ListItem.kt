package me.trujillo.foodplaner3000.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.trujillo.foodplaner3000.data.enums.Unit

@Entity
data class ShoppingList(
    @PrimaryKey(autoGenerate = true )val id: Int = 0,
    val name: String,
    val quantity: Int,
    val unit: Unit
)
