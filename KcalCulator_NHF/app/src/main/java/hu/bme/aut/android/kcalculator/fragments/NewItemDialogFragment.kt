package hu.bme.aut.android.kcalculator.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.kcalculator.FoodActivity
import hu.bme.aut.android.kcalculator.R
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.databinding.DialogNewFoodItemBinding
import hu.bme.aut.android.kcalculator.network.API
import kotlinx.coroutines.runInterruptible
import java.time.LocalDateTime

class NewItemDialogFragment : DialogFragment() {
    interface NewFoodItemDialogListener {
        fun onFoodItemCreated(newItem: FoodItem)
        fun onFoodItemDeleted(newItem: FoodItem)
    }



    private lateinit var listener: NewFoodItemDialogListener

    private lateinit var binding: DialogNewFoodItemBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? NewFoodItemDialogListener
            ?: throw RuntimeException("Error")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        binding = DialogNewFoodItemBinding.inflate(LayoutInflater.from(context))

        binding.spCategory.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.category_items)
        )

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.new_food_item)
            .setView(binding.root)
            .setPositiveButton(R.string.button_ok) { dialogInterface, i ->

                if (isValid()) {
                    listener.onFoodItemCreated(getFoodItem())

                }
            }
            .setNegativeButton(R.string.button_cancel, null)
            .create()
    }


    companion object {
        const val TAG = "NewItemDialogFragment"
    }
    private fun isValid() = binding.etName.text.isNotEmpty()

    private fun getFoodItem() : FoodItem{


        return FoodItem(
            name = binding.etName.text.toString(),
            sugar = 0.0,
            protein = 0.0,
            time = FoodItem.Category.getByOrdinal(binding.spCategory.selectedItemPosition) ?: FoodItem.Category.LUNCH,
            calorie = 0

        )



    }
}