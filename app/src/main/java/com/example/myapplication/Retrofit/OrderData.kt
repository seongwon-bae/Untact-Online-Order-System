package com.example.myapplication.Retrofit

data class OrderData(
    val store_num : String,
    val order_date : String,
    val table_num : Int,
    val pay_state : Int,
    val request : String
)
