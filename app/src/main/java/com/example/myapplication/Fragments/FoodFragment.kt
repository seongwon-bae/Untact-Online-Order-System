package com.example.myapplication.Fragments

import android.content.Context
import android.content.Intent
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
import com.example.myapplication.RecyclerView.SelectedFoodData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_food.*

class FoodFragment(val position : Int) : Fragment() {

    private lateinit var foodadapter : FoodAdapter
    private lateinit var viewPager : ViewPager2
    var onMenuSendListener = null as OnMenuSendListener?
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        foodadapter = FoodAdapter(onMenuSendListener)
        val layoutManager = LinearLayoutManager(context)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = this.foodadapter
        refreshAdapter()
        val tab = view.findViewById<TabLayout>(R.id.tab)
        viewPager = view.findViewById<ViewPager2>(R.id.pager) ?: return
        TabLayoutMediator(tab, pager){tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }

    fun refreshAdapter(){
        (activity as? MainActivity)?.let{
            val items = it.getFoodFragItem(position) ?: return
            this.foodadapter?.setItems(items)
        }
    }

    interface OnMenuSendListener{
        fun OnMenuSend(selectedMenu : SelectedFoodData)
        //fun OnMenuOrder(selectMenus : SelectedFoodData)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnMenuSendListener){
            onMenuSendListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onMenuSendListener = null
    }
}