package hu.bme.aut.android.kcalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.android.kcalculator.adapter.FoodAdapter
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.data.FoodListDatabase
import hu.bme.aut.android.kcalculator.databinding.ActivityFoodBinding
import hu.bme.aut.android.kcalculator.fragments.NewItemDialogFragment
import hu.bme.aut.android.kcalculator.network.API
import kotlin.concurrent.thread

class FoodActivity : AppCompatActivity(), FoodAdapter.FoodItemClickListener, NewItemDialogFragment.NewFoodItemDialogListener {
    private lateinit var binding : ActivityFoodBinding

    private lateinit var database: FoodListDatabase
    private lateinit var adapter: FoodAdapter

    private lateinit var api: API

    private lateinit var bottomNavigationView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        api = API()
        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.getMenu().findItem(R.id.action_food).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    val profileIntent = Intent(this, ProfileActivity::class.java)
                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(profileIntent)
                }
                R.id.action_home -> {
                    val profileIntent = Intent(this, MainActivity::class.java)
                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(profileIntent)
                }
                R.id.action_food -> {
                    val profileIntent = Intent(this, FoodActivity::class.java)
                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(profileIntent)
                }
                R.id.action_diagram -> {
                    val profileIntent = Intent(this, DiagramActivity::class.java)
                    profileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(profileIntent)
                }


            }
            true

        }

        database = FoodListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener{
            NewItemDialogFragment().show(
                supportFragmentManager,
                NewItemDialogFragment.TAG
            )
        }
        initRecyclerView()



    }
    private fun initRecyclerView() {
        adapter = FoodAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.foodItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }
    override fun onItemChanged(item: FoodItem) {
        thread {
            database.foodItemDao().update(item)
            Log.d("MainActivity", "Item update was successful")
        }
    }
    override fun onFoodItemCreated(newItem: FoodItem) {
        thread {
            val response = api.download(newItem.name)
            var a = showResponse(response)
            if (a.size != 1)
            {
                newItem.calorie = a[8].drop(13).dropLast(2).toInt()
                newItem.sugar = a[11].drop(26).dropLast(3).toDouble()
                newItem.protein = a[10].drop(13).toDouble()
                val insertId = database.foodItemDao().insert(newItem)
                newItem.id = insertId
            }
            runOnUiThread {

                var a = showResponse(response)
                if (a.size != 1)
                {  newItem.calorie = a[8].drop(13).dropLast(2).toInt()
                    newItem.sugar = a[11].drop(26).dropLast(3).toDouble()
                    newItem.protein = a[10].drop(13).toDouble()
                adapter.addItem(newItem)
                }
                else
                    Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()


            }
        }

    }

    private fun showResponse(response: String)  : List<String> {
        val split = response.split(",")
        return split
    }


    override fun onFoodItemDeleted(newItem: FoodItem) {
        thread {
            database.foodItemDao().deleteItem(newItem)

            runOnUiThread {
                adapter.delete(newItem)
            }
        }
    }
}