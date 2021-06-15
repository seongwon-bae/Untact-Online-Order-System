package com.example.myapplication.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.Retrofit.FoodInterface
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        qrbutton.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
//            IntentIntegrator(this).initiateScan()
        }

        orderhistorybutton.setOnClickListener {
            val nextIntent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(nextIntent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        var input: String
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                input = result.contents
                var store_table = input.split("\'")
                // store_table[3] = store id
                // store_table[7] = table number
                Toast.makeText(this, "Scanned Store id : " + store_table[3] + "Table number : " + store_table[7], Toast.LENGTH_LONG).show()

                val nextIntent1 = Intent(this, FoodInterface::class.java)
                nextIntent1.putExtra("store_num",store_table[3])
                nextIntent1.putExtra("table_num", store_table[7])

                val nextIntent2 = Intent(this, MainActivity::class.java)
                nextIntent2.putExtra("store_num",store_table[3])
                nextIntent2.putExtra("table_num", store_table[7])

                val nextIntent = Intent(this, MainActivity::class.java)
                nextIntent.putExtra("store_num",store_table[3])
                nextIntent.putExtra("table_num", store_table[7])
                startActivity(nextIntent)

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}

