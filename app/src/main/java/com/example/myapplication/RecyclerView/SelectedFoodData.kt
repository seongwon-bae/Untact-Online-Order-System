package com.example.myapplication.RecyclerView
import java.io.Serializable

data class SelectedFoodData(
    val menu_name : String,
    val menu_price : Int,
    val menu_amount : Int,
    val menu_img : String
) : Serializable