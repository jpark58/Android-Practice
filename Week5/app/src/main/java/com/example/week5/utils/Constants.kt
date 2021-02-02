package com.example.week5.utils

object API{
    const val BASE_URL: String = "https://www.omdbapi.com"
    const val API_KEY: String = "f9d1ae80"
    const val SEARCH_MOVIES: String = "/"

    var token: String = ""
    const val PAPAGO_BASE_URL = "https://openapi.naver.com"
}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}