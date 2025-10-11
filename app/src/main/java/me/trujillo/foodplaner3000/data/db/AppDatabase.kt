package me.trujillo.foodplaner3000.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.trujillo.foodplaner3000.data.db.converters.Converters
import me.trujillo.foodplaner3000.data.db.dao.ShoppingListDao
import me.trujillo.foodplaner3000.data.db.entities.ShoppingList

@Database(entities = [ShoppingList::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    companion object{
        @Volatile
        private var INSTANCE:AppDatabase?= null
        fun getDatabase(context: Context):AppDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Footdatabase").build()
                    INSTANCE = instance
                    instance

            }
        }
    }
}