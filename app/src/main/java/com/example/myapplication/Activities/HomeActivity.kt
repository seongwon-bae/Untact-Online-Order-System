package com.example.myapplication.Activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.Retrofit.RetrofitInterface
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_home.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit.Builder()
    .baseUrl("http://52.78.231.192/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
object DeviceObject {
    val retrofitService: RetrofitInterface by lazy {
        retrofit.create(RetrofitInterface::class.java)
    }
}

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        checkFirstRun()

        qrbutton.setOnClickListener {
            val nextIntent = Intent(this, MainActivity::class.java)
            nextIntent.putExtra("store_num","67KE6rGw7YK5")
            startActivity(nextIntent)
            //IntentIntegrator(this).initiateScan()
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

                val nextIntent1 = Intent(this, RetrofitInterface::class.java)
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
    fun checkFirstRun(){
        val pref : SharedPreferences = getSharedPreferences("isFirst", Activity.MODE_PRIVATE)
        val first : Boolean = pref.getBoolean("isFirst", false)

        if(first==false){
            val device_id = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
            DeviceObject.retrofitService.setUserInfo(device_id = device_id).enqueue(object: Callback<JsonArray> {
                override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                    if(response.isSuccessful){
                        Toast.makeText(this@HomeActivity, "Device Info Upload Success!",Toast.LENGTH_SHORT).show()
                        println("==================첫 실행 유저 데이터 저장 성공! : ${device_id}")
                    }
                }
                override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                }
            })

            val editor : SharedPreferences.Editor = pref.edit()
            editor.putBoolean("isFirst", true)
            editor.commit()
        } else{
            println("==========================최초실행 아님")
        }
    }
}

