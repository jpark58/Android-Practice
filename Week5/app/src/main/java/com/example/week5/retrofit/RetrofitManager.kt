package com.example.week5.retrofit

import android.util.Log
import com.example.week5.model.Movie
import com.example.week5.utils.API
import com.example.week5.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class RetrofitManager {
    companion object{
        val instance = RetrofitManager()
    }

    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    fun searchMovies(title: String?, completion: (RESPONSE_STATE, ArrayList<Movie>?) -> Unit){
        val t = title ?: ""
        val call = iRetrofit?.searchMovies(title = t).let{
            it
        }?: return

        call.enqueue(object: retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("로그", "RetrofitManager - onResponse / response: ${response.body()}")

                when(response.code()){
                    200 -> {
                        response.body()?.let{

                            var parsedMovieDataArray = ArrayList<Movie>()
                            val body = it.asJsonObject
                            val total = body.get("totalResults").asInt
                            val search = body.getAsJsonArray("Search")

                            search.forEach{ item ->
                                val obj = item.asJsonObject

                                val title = obj.get("Title").asString
                                val year = obj.get("Year").asString
                                val id = obj.get("imdbID").asString
                                val poster = obj.get("Poster").asString

                                val movieItem = Movie(title=title, year = year, id = id, poster = poster)
                                parsedMovieDataArray.add(movieItem)
                            }
                            completion(RESPONSE_STATE.OKAY, parsedMovieDataArray)
                        }

                    }
                }


            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("로그", "RetrofitManager - onFailure")
                completion(RESPONSE_STATE.FAIL, null)
            }

        })
    }
}