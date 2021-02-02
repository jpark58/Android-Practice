package com.example.week5.kakaoretrofit

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.week5.KakaoApplication
import com.example.week5.utils.API
import com.example.week5.utils.isJsonArray
import com.example.week5.utils.isJsonObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

object PapagoClient {
    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit?{
        Log.d("파파고", "Papago get Client called")

        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("파파고", " PapagoClient - Log called / message : $message")

                when{
                    message.isJsonObject() -> Log.d("파파고", JSONObject(message).toString(4))
                    message.isJsonArray() -> Log.d("파파고", JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d("파파고", JSONObject(message).toString(4))
                        }catch (e: Exception){
                            Log.d("파파고","Caught")
                        }
                    }
                }
            }

        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(loggingInterceptor)


        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        if(retrofitClient == null){
            Log.d("파파고", "Client building")
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }

        return retrofitClient
    }
}