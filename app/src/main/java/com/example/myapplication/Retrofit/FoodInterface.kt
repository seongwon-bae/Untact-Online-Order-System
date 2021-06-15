package com.example.myapplication.Retrofit

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface FoodInterface {
    @FormUrlEncoded
    @POST("/food_search_api.php")
    fun getFoodInfo(
        @Field("store_num") store_num : String
    ) : Call<JsonArray>

    // 만약 GET 방식을 쓸 경우 아래와 같다.
    /*
    @GET("food_search_api.php")
    fun getFoodInfo(
        @Query("store_num") store_num : Int
    ) : Call<JsonArray>
    */
}