package com.example.myapplication.RecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.selected_menu.view.*

class MenuSelectAdapter(): RecyclerView.Adapter<MenuSelectAdapter.MainViewHolder>(){
    var menus = mutableSetOf<SelectedFoodData>()
    val plus : Button? = null
    val minus : Button? = null

    class MainViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        private var amount = 1

        fun bind(listener: View.OnClickListener, menu : SelectedFoodData?) : Int{
            var flag = 0
            view.food_name_text.text = menu?.menu_name
            Glide.with(view.food_selected_imgView).load(menu?.menu_img)
                .into(view.food_selected_imgView)
            view.food_price_text.text = "${menu?.menu_price}"
            view.setOnClickListener(listener)
            view.plus_button.setOnClickListener {
                amount = Integer.parseInt("${view.food_amount_text.text}")
                view.food_amount_text.text = "${amount + 1}"
                bind(listener, menu)
            }
            view.minus_button.setOnClickListener {
                amount = Integer.parseInt("${view.food_amount_text.text}")
                if(amount-1 == 0){
                    flag = -1
                } else{
                    view.food_amount_text.text = "${amount-1}"
                    bind(listener, menu)
                }
            }
            return amount
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSelectAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_menu, parent, false)
        return MenuSelectAdapter.MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuSelectAdapter.MainViewHolder, position: Int) {
        var item : SelectedFoodData? = null
        menus.forEachIndexed{ index, value ->
            if(index==position){
                item = value
            }
        }
        val listener = View.OnClickListener {
            val menu_name = item?.menu_name
            val menu_price = item?.menu_price
            val menu_amount = item?.menu_amount
            val menu_img = item?.menu_img
        }
        holder.apply{
            var amount = bind(listener, item)

            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = menus.size

    fun setItems(newItem : ArrayList<SelectedFoodData>){
        for(i in newItem){
            this.menus.add(i)
        }
        notifyDataSetChanged()
    }

    fun setPrice() : Int{
        var price=0
        for(i in menus){
            price += (i.menu_price)*(i.menu_amount)
        }
        return price
    }
}