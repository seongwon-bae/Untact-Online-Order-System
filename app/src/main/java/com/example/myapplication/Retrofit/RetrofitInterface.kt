package com.example.myapplication.Retrofit

import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @FormUrlEncoded
    @POST("/food_search_api.php")
    fun getFoodInfo(
        @Field("store_num") store_num : String
    ) : Call<JsonArray>

    @GET("/category_search_api.php")
    fun getCategoryName() : Call<JsonArray>

    @FormUrlEncoded
    @POST("/order_create_api.php")
    fun setOrderInfo(
        @Field("store_num") store_num : String,
        @Field("order_date") order_date : String,
        @Field("table_num") table_num : Int,
        @Field("pay_state") pay_state : Int,
        @Field("request") request : String
    ) : Call<JsonArray>

    @FormUrlEncoded
    @POST("/user_info_update_api.php")
    fun setUserInfo(
        @Field("device_id") device_id : String
    ) : Call<JsonArray>

    // 만약 GET 방식을 쓸 경우 아래와 같다.
    /*
    @GET("food_search_api.php")
    fun getFoodInfo(
        @Query("store_num") store_num : Int
    ) : Call<JsonArray>
    */
}