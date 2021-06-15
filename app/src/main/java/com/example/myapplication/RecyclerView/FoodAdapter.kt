package com.example.myapplication.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.foodview.view.*

class FoodAdapter(): RecyclerView.Adapter<FoodAdapter.MainViewHolder>() {
    private val foods = ArrayList<FoodSelectData>()
    class MainViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        fun bind(listener: View.OnClickListener, food:FoodSelectData){
            view.food_name.text = food.food_name
            Glide.with(view.food_imgView).load(food.food_img).into(view.food_imgView)
            view.food_description.text = food.food_description
            view.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.foodview, parent, false)
        return FoodAdapter.MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodAdapter.MainViewHolder, position: Int) {
        val item = foods[position]
        val listener = View.OnClickListener { it->
            Toast.makeText(it.context, "클릭한 아이템의 이름 : ${item.food_name}", Toast.LENGTH_SHORT).show()
        }
        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = foods.size

    fun setItems(newItem : ArrayList<FoodSelectData>){
        this.foods.addAll(newItem)
        notifyDataSetChanged()
    }
}