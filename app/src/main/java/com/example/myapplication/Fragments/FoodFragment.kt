package com.example.myapplication.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.Activities.MainActivity
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.RecyclerView.FoodSelectData
import kotlinx.android.synthetic.main.fragment_food.*

class FoodFragment(val position : Int) : Fragment() {

    private lateinit var foodadapter : FoodAdapter
    private lateinit var viewPager : ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        foodadapter = FoodAdapter()
        val layoutManager = LinearLayoutManager(context)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = this.foodadapter
        refreshAdapter()
    }

    fun refreshAdapter(){
        (activity as? MainActivity)?.let{
            val items = it.getFragItem(position) ?: return
            this.foodadapter?.setItems(items)
        }
    }
}