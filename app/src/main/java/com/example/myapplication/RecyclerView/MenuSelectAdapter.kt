package com.example.myapplication.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import kotlinx.android.synthetic.main.selected_menu.view.*

class MenuSelectAdapter(): RecyclerView.Adapter<MenuSelectAdapter.MainViewHolder>() {
    val menus = ArrayList<SelectedFoodData>()

    class MainViewHolder(v: View): RecyclerView.ViewHolder(v){
        private var view: View = v
        fun bind(listener: View.OnClickListener, menu : SelectedFoodData){
            view.food_name_text.text = menu.menu_name
            Glide.with(view.food_selected_imgView).load(menu.menu_img).into(view.food_selected_imgView)
            view.food_price_text.text = menu.menu_price
            view.food_amount_text.text = "${menu.menu_amount}"
            view.setOnClickListener(listener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSelectAdapter.MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected_menu, parent, false)
        return MenuSelectAdapter.MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuSelectAdapter.MainViewHolder, position: Int) {
        val item = menus[position]
        val listener = View.OnClickListener {
            Toast.makeText(it.context, "클릭한 아이템의 이름 : ${item.menu_name}", Toast.LENGTH_SHORT).show()
            val menu_name = item.menu_name
            val menu_price = item.menu_price
            val menu_amount = item.menu_amount
            val menu_img = item.menu_img
            //listener?.OnMenuOrder(SelectedFoodData(menu_name, menu_price, menu_amount, menu_img))
        }

        holder.apply{
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = menus.size

    fun setItems(newItem : ArrayList<SelectedFoodData>){
        this.menus.clear()
        this.menus.addAll(newItem)
        notifyDataSetChanged()
    }
}