package com.example.week5.retrofit

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
import retrofit2.http.Url
import java.lang.Exception
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofitClient: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit?{
        Log.d("로그", "get Client called")

        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object: HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
                Log.d("로그", "RetrofitClient - Log called / message : $message")

                when{
                    message.isJsonObject() -> Log.d("로그", JSONObject(message).toString(4))
                    message.isJsonArray() -> Log.d("로그", JSONObject(message).toString(4))
                    else -> {
                        try {
                            Log.d("로그", JSONObject(message).toString(4))
                        }catch (e: Exception){
                            Log.d("로그","Caught")
                        }
                    }
                }
            }

        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        client.addInterceptor(loggingInterceptor)



        val baseParameterInterceptor: Interceptor = (object : Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val addedUrl = originalRequest.url.newBuilder().addQueryParameter("apikey", API.API_KEY).build()
                val finalRequest = originalRequest.newBuilder().url(addedUrl).method(originalRequest.method, originalRequest.body).build()

                Log.d("로그", "baseparams: ${addedUrl.toString()}")

                //return chain.proceed(finalRequest)

                val response = chain.proceed(finalRequest)
                if(response.code != 200){
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(KakaoApplication.instance, "${response.code} Error!!", Toast.LENGTH_SHORT).show()
                    }
                }

                return response
            }

        })

        client.addInterceptor(baseParameterInterceptor)

        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)
        client.retryOnConnectionFailure(true)

        if(retrofitClient == null){
            Log.d("로그", "Client building")
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        }

        return retrofitClient
    }
}