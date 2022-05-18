package hu.bme.aut.android.kcalculator.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.android.kcalculator.fragments.ChartFragment
import hu.bme.aut.android.kcalculator.fragments.DiagramFragment

class DiagramPageAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DiagramFragment()
            1 -> ChartFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }


}