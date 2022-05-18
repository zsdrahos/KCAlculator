package hu.bme.aut.android.kcalculator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FoodItem::class], version = 1)
@TypeConverters(value = [FoodItem.Category::class])
abstract class FoodListDatabase : RoomDatabase() {
    abstract fun foodItemDao(): FoodItemDao

    companion object {
        fun getDatabase(applicationContext: Context): FoodListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                FoodListDatabase::class.java,
                "calory-list"
            ).build();
        }
    }
}