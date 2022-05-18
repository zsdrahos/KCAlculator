package hu.bme.aut.android.kcalculator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "calorytable")
data class FoodItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "sugar") var sugar: Double,
    @ColumnInfo(name = "protein") var protein: Double,
    @ColumnInfo(name = "time") var time: Category,
    @ColumnInfo(name = "calorie") var calorie: Int
) {
    enum class Category {
        BREAKFAST, LUNCH, DINNER;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                var ret: Category? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
}