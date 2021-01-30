package com.example.week5.retrofit

import com.example.week5.utils.API
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    @GET(API.SEARCH_MOVIES)
    fun searchMovies(@Query("s") title: String): Call<JsonElement>

}