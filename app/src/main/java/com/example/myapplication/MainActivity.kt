package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println(intent.getStringExtra("store_num"))
        println(intent.getStringExtra("table_num"))
        println("===================store_num : ${intent.getStringExtra("store_num")}=======================")

        button.setOnClickListener {
            val nextIntent = Intent(this, OrderCheckActivity::class.java)
            startActivity(nextIntent)
        }
    }
}