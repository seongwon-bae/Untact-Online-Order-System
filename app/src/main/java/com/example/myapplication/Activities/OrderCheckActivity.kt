package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.SelectedFoodData
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_order_check.*
import java.nio.file.Path
import java.time.LocalDate

class OrderCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_check)

        val payment = intent.getStringExtra("payment").toString()
        val selectMenuList = intent.getSerializableExtra("menus") as ArrayList<SelectedFoodData>
        val requestText = intent.getStringExtra("request")
        var allMenu = ""
        var allPrice = 0
        for(i in selectMenuList){
            allMenu += i.menu_name+" ${i.menu_amount}개\n"
            allPrice += i.menu_price
        }
        allMenu = allMenu.trim()
        val today = LocalDate.now().toString()
        val ymd = today.split("-")
        val year = ymd[0]
        val month = ymd[1]
        val day = ymd[2]

        ordered_price.text = "${allPrice}원"
        ordered_foods.text = allMenu
        date1.text = "${year}년 ${month}월 ${day}일"
        ordered_payment.text = payment
        ordered_request.text = requestText

        order_check.setOnClickListener {
            val nextIntent = Intent(this, HomeActivity::class.java)
            startActivity(nextIntent)
        }
    }
}