package hu.bme.aut.android.kcalculator.fragments

import android.R.attr.label
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import hu.bme.aut.android.kcalculator.data.FoodItem
import hu.bme.aut.android.kcalculator.data.FoodListDatabase
import hu.bme.aut.android.kcalculator.databinding.FragmentChartBinding
import kotlin.concurrent.thread


class ChartFragment : Fragment() {

    private lateinit var binding: FragmentChartBinding;
    private lateinit var lineChart: PieChart

    private lateinit var database: FoodListDatabase
    private lateinit var database_list : List<FoodItem>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChartBinding.inflate(layoutInflater, container, false)
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
            val breakfast = database_list.sumOf { if (it.time.name.equals("BREAKFAST")) it.sugar else 0.0}
            val lunch = database_list.sumOf { if (it.time.name.equals("LUNCH")) it.sugar else 0.0}
            val dinner = database_list.sumOf { if (it.time.name.equals("DINNER")) it.sugar else 0.0}
            Log.d("asd", breakfast.toString())

            val entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(breakfast.toFloat(), "Breakfast"))
            entries.add(PieEntry(lunch.toFloat(), "Lunch"))
            entries.add(PieEntry(dinner.toFloat(), "Dinner"))

            val barDataSet = PieDataSet(entries, "")
            val data = PieData(barDataSet)
            barDataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            barDataSet.setValueTextSize(20f)
            lineChart.data = data
            lineChart.invalidate()
            data.setValueTextColor(Color.BLACK)
            lineChart.getDescription().setEnabled(false);

        }

    }

    private fun initLineChart() {


        lineChart.animateY(1400, Easing.EaseInOutQuad);
        lineChart.setEntryLabelColor(Color.BLACK);


        val l: Legend = lineChart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}