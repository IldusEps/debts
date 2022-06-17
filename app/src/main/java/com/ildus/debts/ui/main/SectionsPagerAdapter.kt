package com.ildus.debts.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ildus.debts.AddDeptFragment
import com.ildus.debts.DeptsRecyclerAdapter

class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return PlaceholderFragment()
            1 -> return AddDeptFragment()
        }
        return PlaceholderFragment()
    }
}