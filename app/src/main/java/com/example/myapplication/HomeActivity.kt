package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        qrbutton.setOnClickListener {
            IntentIntegrator(this).initiateScan()
        }

        orderhistorybutton.setOnClickListener {
            val nextIntent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(nextIntent)
        }
    }
}