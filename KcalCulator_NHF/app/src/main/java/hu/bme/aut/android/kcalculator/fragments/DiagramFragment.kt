package hu.bme.aut.android.kcalculator.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.data.FoodListDatabase
import hu.bme.aut.android.kcalculator.databinding.FragmentDiagramBinding
import kotlin.concurrent.thread

class DiagramFragment : Fragment() {

    private lateinit var binding: FragmentDiagramBinding;
    private lateinit var lineChart: BarChart

    private lateinit var database: FoodListDatabase
    private lateinit var database_list : List<FoodItem>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiagramBinding.inflate(layoutInflater, container, false)
        //diagram
        lineChart = binding.lineChart
        initLineChart()
        database = context?.let { FoodListDatabase.getDatabase(it.applicationContext) }!!
        initDatabase()
        return binding.root
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

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}