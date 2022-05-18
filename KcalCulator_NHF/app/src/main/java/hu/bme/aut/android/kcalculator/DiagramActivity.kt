package hu.bme.aut.android.kcalculator

import android.animation.ObjectAnimator
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.data.FoodListDatabase
import hu.bme.aut.android.kcalculator.databinding.ActivityDiagramBinding
import kotlin.concurrent.thread
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import hu.bme.aut.android.kcalculator.adapter.DiagramPageAdapter


class DiagramActivity : AppCompatActivity()  {
     private lateinit var binding : ActivityDiagramBinding
    private lateinit var lineChart: BarChart

    private lateinit var database: FoodListDatabase
    private lateinit var database_list : List<FoodItem>
    private lateinit var bottomNavigationView : BottomNavigationView
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityDiagramBinding.inflate(layoutInflater)
         setContentView(binding.root)
         bottomNavigationView = findViewById(R.id.navigation)

         binding.vpLauncherPanels.vpLauncherPanels.adapter = DiagramPageAdapter(supportFragmentManager)



         bottomNavigationView.getMenu().findItem(R.id.action_diagram).setChecked(true);
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
     }
/*
         //diagram
         lineChart = findViewById(R.id.line_chart)
         initLineChart()
         database = FoodListDatabase.getDatabase(applicationContext)
         initDatabase()



         //setDataToLineChart()


     }

    private fun initDatabase() {

        thread {
            database_list = database.foodItemDao().getAll()
            val breakfast = database_list.sumOf { if (it.time.name.equals("BREAKFAST")) it.calorie else 0}
            val lunch = database_list.sumOf { if (it.time.name.equals("LUNCH")) it.calorie else 0}
            val dinner = database_list.sumOf { if (it.time.name.equals("DINNER")) it.calorie else 0}
            Log.d("asd", breakfast.toString())

            val entries: ArrayList<BarEntry> = ArrayList()
            entries.add(BarEntry(0.toFloat(), breakfast.toFloat()))
            entries.add(BarEntry(1.toFloat(), lunch.toFloat()))
            entries.add(BarEntry(2.toFloat(), dinner.toFloat()))

            val barDataSet = BarDataSet(entries, "")
            val data = BarData(barDataSet)
            barDataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            barDataSet.setValueTextSize(20f)
            lineChart.data = data
            lineChart.invalidate()
        }

    }

    private fun initLineChart() {


        val xAxisValues: List<String> = listOf("BREAKFAST", "LUNCH", "DINNER")
//        hide grid lines
        lineChart.axisLeft.setDrawGridLines(true)
        val xAxis: XAxis = lineChart.xAxis
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)

        //remove right y-axis
        lineChart.axisLeft.textSize = 13f
        lineChart.axisRight.isEnabled = false
        //remove legend
        val l: Legend = lineChart.getLegend()

        lineChart.legend.isEnabled = false

        l.setTextSize(50f);
        lineChart.getXAxis().setTextSize(15f);
        //remove description label
        lineChart.description.isEnabled = false

        //add animation
        lineChart.animateX(500, Easing.EaseInSine)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE

        lineChart.getXAxis().setValueFormatter(IndexAxisValueFormatter(xAxisValues))
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        //xAxis.labelRotationAngle = +90f

    }*/




}