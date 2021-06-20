package com.example.myapplication.Activities

import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Fragments.FoodFragment
import com.example.myapplication.Retrofit.FoodData
import com.example.myapplication.R
import com.example.myapplication.RecyclerView.FoodAdapter
import com.example.myapplication.Fragments.FoodPageAdapter
import com.example.myapplication.RecyclerView.FoodSelectData
import com.example.myapplication.RecyclerView.MenuSelectAdapter
import com.example.myapplication.RecyclerView.SelectedFoodData
import com.example.myapplication.Retrofit.CategoryData
import com.example.myapplication.Retrofit.RetrofitInterface
import com.google.android.material.tabs.TabLayout
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.fragment_food.*
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
    val retrofitService: RetrofitInterface by lazy {
        retrofit.create(RetrofitInterface::class.java)
    }
}

class MainActivity : AppCompatActivity(), FoodFragment.OnMenuSendListener{

    val selectMenuList = ArrayList<SelectedFoodData>()
    override fun OnMenuSend(selectedMenu: SelectedFoodData) {
        selectMenuList.add(selectedMenu)
        menuSelectAdapter.setItems(selectMenuList)
        menuSelectAdapter.notifyDataSetChanged()
    }

//    override fun OnMenuOrder(selectMenus: SelectedFoodData) {
//
//    }

    val food_adapter = FoodAdapter(null)
    val foodPageAdapter = FoodPageAdapter(this)
    val menuSelectAdapter = MenuSelectAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val store_num = intent.getStringExtra("store_num").toString()
        val callFood = ApiObject.retrofitService.getFoodInfo(store_num = store_num)
        val category_nums = mutableSetOf<Int>()

        menu_recyclerview.adapter = menuSelectAdapter

        callFood.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>){
                if(response.isSuccessful){
                    var gson = GsonBuilder().create()
                    var jsonArray =JSONArray(response.body().toString())
                    var allFoodList = ArrayList<FoodSelectData>()
                    for(i in 0 until jsonArray.length()){
                        val food = gson.fromJson(jsonArray.getJSONObject(i).toString(), FoodData::class.java)
                        allFoodList.add(FoodSelectData(food.food_name, food.food_img, food.price, food.food_description, food.status, food.category_num))
                        category_nums.add(food.category_num)
                    }
                    food_adapter.setItems(allFoodList)
                    foodPageAdapter.fragList = category_nums.size
                    category_nums.forEachIndexed{ index, value ->
                        val distributedFoodList = ArrayList<FoodSelectData>()
                        for(j in allFoodList){
                            if(value==j.category_num){
                                distributedFoodList.add(j)
                            }
                        }
                        foodPageAdapter.foodMap.put(index, distributedFoodList)
                    }
                    pager.adapter = foodPageAdapter
                    val callCategory = ApiObject.retrofitService.getCategoryName()
                    callCategory.enqueue(object: Callback<JsonArray> {
                        override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                            if(response.isSuccessful){
                                gson = GsonBuilder().create()
                                jsonArray =JSONArray(response.body().toString())
                                val categoryList = ArrayList<CategoryData>()
                                for(i in 0 until jsonArray.length()){
                                    val category = gson.fromJson(jsonArray.getJSONObject(i).toString(), CategoryData::class.java)
                                    categoryList.add(CategoryData(category.category_num, category.store_num, category.category_name))
                                }
                                makeTab(allFoodList, categoryList)
                            }
                        }

                        override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                t.message?.let { Log.d("api fail : ", it) }
            }
        })

        button.setOnClickListener {
            val items = arrayOf("선결제", "후결제(현금)", "후결제(카드)")
            var selectedItem: String? = null
            val builder = AlertDialog.Builder(this)
                .setTitle("결제 방법을 선택해주세요")
                .setSingleChoiceItems(items, -1) { dialog, which ->
                    selectedItem = items[which]
                }
                .setPositiveButton("OK") { dialog, which ->
                    Toast.makeText(this,"${selectedItem.toString()} is Selected" ,Toast.LENGTH_LONG).show()
                    val nextIntent = Intent(this, OrderCheckActivity::class.java)
                    nextIntent.putExtra("payment", selectedItem)
                    startActivity(nextIntent)
                }
                .show()

        }
    }

    fun makeTab(foodList : ArrayList<FoodSelectData>, categories : ArrayList<CategoryData>){
        tab.removeAllTabs()
        connectViewPager()
        for(i in categories){
            for(j in foodList){
                if(i.category_num == j.category_num){
                    tab.addTab(tab.newTab().setText(i.category_name))
                    break
                }
            }
        }
    }
    fun connectViewPager(){
        tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                pager.setCurrentItem(position)
            }
        })
    }
    fun getFoodFragItem(position: Int) : ArrayList<FoodSelectData>? {
        return foodPageAdapter.foodMap[position]
    }
}