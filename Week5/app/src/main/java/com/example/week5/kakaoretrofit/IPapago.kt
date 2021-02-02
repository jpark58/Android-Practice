package com.example.week5.kakaoretrofit

import com.example.week5.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*

interface IPapago {

    @FormUrlEncoded
    @POST("/v1/papago/n2mt")
    fun translate(@Header("X-Naver-Client-Id") clientId: String,
                  @Header("X-Naver-Client-Secret") clientSecret: String,
                  @Field("source") source: String,
                  @Field("target") target: String,
                  @Field("text") text: String): Call<JsonElement>
}