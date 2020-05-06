package com.example.ftmsnew.retrofit

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


interface RetrofitInterface {
    @POST("TranSync")
    fun sendingDetails(@Body commonList: TranList): Call<ResponseBody>


    @POST("Masters/MasterSync")
    fun gettingMasterDetails(): Call<MasterList>



}