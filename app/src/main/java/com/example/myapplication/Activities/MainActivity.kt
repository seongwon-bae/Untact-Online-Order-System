package com.example.myapplication.Activities

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Retrofit.FoodData
import com.example.myapplication.Retrofit.FoodInterface
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.RecyclerView.FoodSelectData
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
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
object ApiObject {
    val retrofitFoodService: FoodInterface by lazy {
        retrofit.create(FoodInterface::class.java)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var store_num = intent.getStringExtra("store_num").toString()
        val food_adapter = FoodAdapter()
        val callFood = ApiObject.retrofitFoodService.getFoodInfo(store_num = store_num)
        callFood.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>){
                if(response.isSuccessful){
                    val gson = GsonBuilder().create()
                    val jsonArray =JSONArray(response.body().toString())
                    var foodlist = ArrayList<FoodSelectData>()
                    for(i in 0 until jsonArray.length()){
                        val food = gson.fromJson(jsonArray.getJSONObject(i).toString(), FoodData::class.java)

                        foodlist.add(FoodSelectData(food.food_name, food.food_img, food.food_description, food.price))
                    }
                    for(i in foodlist){
                        println(i.food_name)
                    }
                    food_adapter.setItems(foodlist)
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let { Log.d("api fail : ", it) }
            }
        })
        val layoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = food_adapter

        button.setOnClickListener {
            val nextIntent = Intent(this, OrderCheckActivity::class.java)
            startActivity(nextIntent)
        }
    }
}