package me.trujillo.foodplaner3000.data.db.converters

import androidx.room.TypeConverter
import me.trujillo.foodplaner3000.data.enums.Unit

class Converters {

    @TypeConverter
    fun fromUnit(unit: Unit): String {
        return unit.name
    }

    @TypeConverter
    fun toUnit(value: String): Unit {
        return Unit.valueOf(value)
    }
}