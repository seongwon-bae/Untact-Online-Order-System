package com.example.myapplication.Fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.RecyclerView.FoodSelectData

class FoodPageAdapter(activity : FragmentActivity, val foodAdapter: FoodAdapter) : FragmentStateAdapter(activity) {
    val foodMap = mutableMapOf<Int,ArrayList<FoodSelectData>>()
    var fragList = 0
    val foodList = ArrayList<FoodFragment>()

    override fun getItemCount(): Int {
        return foodMap.size
    }

    override fun createFragment(position: Int): Fragment {
        val frag = FoodFragment(position)
        foodList.add(frag)
        return frag
    }

}