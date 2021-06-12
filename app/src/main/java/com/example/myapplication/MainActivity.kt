package com.example.myapplication

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit.Builder()
    .baseUrl("http://3.34.192.199/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
object ApiObject {
    val retrofitFoodService: FoodInterface by lazy {
        retrofit.create(FoodInterface::class.java)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callFood = ApiObject.retrofitFoodService.getFoodInfo(2)
        callFood.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>){
                if(response.isSuccessful){
                    val gson = GsonBuilder().create()
                    val jsonArray =JSONArray(response.body().toString())
                    for(i in 0 until jsonArray.length()){
                        val food = gson.fromJson(jsonArray.getJSONObject(i).toString(), FoodData::class.java)
                        println("api (${i}) response food_name : ${food.food_name!!.toString()}")
                        println("api (${i}) response food_img : ${food.food_img!!.toString()}")
                        println("api (${i}) response price : ${food.price!!.toString()}")
                        println("api (${i}) response food_description : ${food.food_description!!.toString()}")
                    }
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let { Log.d("api fail : ", it) }
            }
        })
        button.setOnClickListener {
            val nextIntent = Intent(this, OrderCheckActivity::class.java)
            startActivity(nextIntent)
        }
    }
}