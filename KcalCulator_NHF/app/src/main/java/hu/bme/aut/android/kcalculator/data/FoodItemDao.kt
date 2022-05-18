package hu.bme.aut.android.kcalculator.data

import androidx.room.*

@Dao
interface FoodItemDao {
    @Query("SELECT * FROM calorytable")
    fun getAll(): List<FoodItem>


    @Insert
    fun insert(foodsItems: FoodItem): Long

    @Update
    fun update(foodsItems: FoodItem)


    @Delete
    fun deleteItem(foodsItems: FoodItem)
}