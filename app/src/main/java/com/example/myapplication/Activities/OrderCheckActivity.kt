package com.example.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_order_check.*
import java.time.LocalDate

class OrderCheckActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_check)

        val payment = intent.getStringExtra("payment").toString()
        how1.text = payment
        val today = LocalDate.now().toString()
        val ymd = today.split("-")
        val year = ymd[0]
        val month = ymd[1]
        val day = ymd[2]
        if(payment=="선결제"){
            date1.text = "${year}년 ${month}월 ${day}일"
        } else {
            date1.text = "미결제"
        }
    }
}