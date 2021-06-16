package com.example.myapplication.RecyclerView

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.Fragments.FoodFragment

class FoodPageAdapter(activity : FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return FoodFragment(FoodAdapter())
    }
}