package me.trujillo.foodplaner3000.data.db.converters

import androidx.room.TypeConverter
import me.trujillo.foodplaner3000.data.enums.Unit1

class Converters {

    @TypeConverter
    fun fromUnit(unit: Unit1): String {
        return unit.name
    }

    @TypeConverter
    fun toUnit(value: String): Unit1 {
        return Unit1.valueOf(value)
    }
}