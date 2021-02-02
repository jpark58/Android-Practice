package com.example.week5.kakaoretrofit

import android.util.Log
import com.example.week5.model.Movie
import com.example.week5.retrofit.IRetrofit
import com.example.week5.retrofit.RetrofitClient
import com.example.week5.retrofit.RetrofitManager
import com.example.week5.utils.API
import com.example.week5.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

class PapagoManager {

    companion object{
        val instance = PapagoManager()
    }

    private val iRetrofit: IPapago? = PapagoClient.getClient(API.PAPAGO_BASE_URL)?.create(IPapago::class.java)

    fun translate(src: String?, target: String?, text: String?, completion: (RESPONSE_STATE, translatedText: String) -> Unit){
        val call = iRetrofit?.translate(clientId = "xUwnIdPepOWY0dAbdJDD", clientSecret = "uLsO_wAVhx", source=src!!, target=target!!, text=text!!).let{
            it
        }?: return

        call.enqueue(object: retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("로그", "RetrofitManager - onResponse / response: ${response.body()}")

                when(response.code()){
                    200 -> {

                        response.body()?.let{

                            var translatedText = ""
                            val body = it.asJsonObject
                            val members = body.get("message").asJsonObject
                            val temp = members.get("result").asJsonObject
                            translatedText = temp.get("translatedText").asString

                            completion(RESPONSE_STATE.OKAY, translatedText)
                        }

                    }
                }


            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("로그", "RetrofitManager - onFailure")
                completion(RESPONSE_STATE.FAIL, "Nothing")
            }

        })
    }

}