package hu.bme.aut.android.kcalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.kcalculator.FoodActivity
import hu.bme.aut.android.kcalculator.R
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.databinding.ItemFoodListBinding

class FoodAdapter(private val listener: FoodActivity) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val items = mutableListOf<FoodItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FoodViewHolder(
        ItemFoodListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodsItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(foodsItem.time))
        holder.binding.tvName.text = foodsItem.name
        holder.binding.tvCalory.text = "${foodsItem.calorie} Kcal"
        if (foodsItem.calorie > 800) holder.binding.tvCalory.setBackgroundResource(R.drawable.rounded_corner_red)
        holder.binding.tvComment.text = "Carbs: ${foodsItem.sugar}  g"
        holder.binding.tvProtein.text = "Proteins: ${foodsItem.protein} g"

        holder.binding.ibRemove.setOnClickListener() {
            listener.onFoodItemDeleted(foodsItem)

        }

    }

    @DrawableRes()
    private fun getImageResource(category: FoodItem.Category): Int {
        return when (category) {
            FoodItem.Category.BREAKFAST -> R.drawable.breakfast
            FoodItem.Category.LUNCH -> R.drawable.sausages
            FoodItem.Category.DINNER -> R.drawable.dinner
        }
    }

    override fun getItemCount(): Int = items.size

    interface FoodItemClickListener {
        fun onItemChanged(item: FoodItem)
        fun onFoodItemDeleted(item: FoodItem)
    }

    inner class FoodViewHolder(val binding: ItemFoodListBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: FoodItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(foodsItems: List<FoodItem>) {
        items.clear()
        items.addAll(foodsItems)
        notifyDataSetChanged()
    }
    fun delete(item: FoodItem) {
        items.remove(item)
        notifyDataSetChanged()
    }


}