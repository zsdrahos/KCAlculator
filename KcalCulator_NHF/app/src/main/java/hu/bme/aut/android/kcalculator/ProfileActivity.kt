package hu.bme.aut.android.kcalculator


import android.content.SharedPreferences
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView

import hu.bme.aut.android.kcalculator.databinding.ActivityProfileBinding

import androidx.preference.PreferenceManager
import java.util.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var bottomNavigationView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = findViewById(R.id.navigation)

        val sp3 = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sp3.edit()
        if (sp3.getBoolean("START_P", false) == false)
        {


            val imgView: ImageView = binding.imageView
            imgView.visibility = View.GONE
            binding.welcomeTitle.visibility = View.VISIBLE
            binding.welcomeTitle2.visibility = View.VISIBLE
            bottomNavigationView.visibility = View.GONE

        }



        bottomNavigationView.getMenu().findItem(R.id.action_profile).setChecked(true);
        bottomNavigationView.setOnItemSelectedListener { item ->
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

        //shared preferences

        binding.save.setOnClickListener() {
            saveLastStartTime()
            if (sp3.getBoolean("START_P", false) == false)
            {
                val mainactivity = Intent(this, MainActivity::class.java)
                mainactivity.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(mainactivity)
                editor.putBoolean("START_P", true)
                editor.apply()
            }
        }
        loadString()

    }
    private fun saveLastStartTime() {
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = sp.edit()
        var name = binding.fullname.text.toString()
        var age = Integer.parseInt((binding.birth.text.toString().toIntOrNull()?:0).toString())
        val height = Integer.parseInt((binding.height.text.toString().toIntOrNull()?:0).toString())
        val weight = Integer.parseInt((binding.weight.text.toString().toIntOrNull()?:0).toString())
        var sex = ""
        when {
            binding.rbMale.isChecked -> {
                sex = "male"
                binding.imageView.setImageResource(R.drawable.men)
            }
            binding.rbFemale.isChecked -> {
                sex = "female"
                binding.imageView.setImageResource(R.drawable.women)
            }
            else -> {
                sex = "other"
                binding.imageView.setImageResource(R.drawable.android)
            }
        }

        if (name.isNullOrEmpty()) name = ""


        editor.putString("NAME", name)
        editor.putInt("BIRTH", (age))
        editor.putInt("HEIGHT", height)
        editor.putInt("WEIGHT", weight)
        editor.putString("SEX", sex)
        editor.apply()






    }
    private fun loadString(){
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        val tmpname = sp.getString("NAME", "")
        binding.fullname.setText(tmpname)
        val tmpbirth = sp.getInt("BIRTH", 0)
        binding.birth.setText(tmpbirth.toString())
        val tmpheight = sp.getInt("HEIGHT", 0)
        binding.height.setText(tmpheight.toString())
        val tmpweight = sp.getInt("WEIGHT", 0)
        binding.weight.setText(tmpweight.toString())
        var sex = sp.getString("SEX", "")

        if (sex == "male") {binding.rGroup.check(binding.rbMale.id)
            binding.imageView.setImageResource(R.drawable.men)
        }
        else if (sex == "female"){binding.rGroup.check(binding.rbFemale.id)
            binding.imageView.setImageResource(R.drawable.women)}
        else if (sex == "other"){binding.rGroup.check(binding.rbOther.id)
            binding.imageView.setImageResource(R.drawable.android)}

    }
}