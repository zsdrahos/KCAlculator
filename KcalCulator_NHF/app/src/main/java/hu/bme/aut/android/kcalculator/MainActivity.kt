package hu.bme.aut.android.kcalculator

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.android.kcalculator.data.FoodListDatabase
import hu.bme.aut.android.kcalculator.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var database: FoodListDatabase
    private var sum = 0
    private lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val sp2 = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sp2.edit()
        if (sp2.getBoolean("START", false) == false)
        {
            editor.putBoolean("START", true)
            editor.apply()
            val profileIntent = Intent(this, ProfileActivity::class.java)
            profileIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(profileIntent)

        }


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
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



        //adatbekeres
       database = FoodListDatabase.getDatabase(applicationContext)
       CalculateCaloryIntake()



        var tmpname = sp.getString("NAME", "")
        if (tmpname.isNullOrEmpty()) tmpname = "User"



        binding.dalilyCalory.setText(CalculateMaxCalory().toString() + " kcal")
        binding.welcomeName.setText("Welcome " + tmpname.toString())


    }

    fun CalculateMaxCalory() : Double {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val tmpbirth = sp.getInt("BIRTH", 0)
        val tmpheight = sp.getInt("HEIGHT", 0)
        val tmpweight = sp.getInt("WEIGHT", 0)
        val tmpsex = sp.getString("SEX", "")
        if (tmpsex == "male") binding.imageView.setImageResource(R.drawable.men)
        else if (tmpsex == "female")binding.imageView.setImageResource(R.drawable.women)
        else if (tmpsex == "other") binding.imageView.setImageResource(R.drawable.android)

        if (tmpbirth == 0|| tmpheight == 0 || tmpweight == 0)
            return 0.0
        else
            return (66.6 + (13*tmpweight) + (5*tmpheight) - (6*tmpbirth))
    }

   private fun CalculateCaloryIntake() {

        thread {
            val a = database.foodItemDao().getAll()

            sum = a.sumOf { it.calorie }
            runOnUiThread{
                binding.takenCalory.setText(sum.toString() + " kcal")
                binding.progressBar.max = CalculateMaxCalory().toInt()
                ObjectAnimator.ofInt( binding.progressBar, "progress", sum)
                    .start()
                binding.percentT.setText(((sum/CalculateMaxCalory())*100).toInt().toString()+" %")
            }

        }

    }


}