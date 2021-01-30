package com.example.week5.utils

object API{
    const val BASE_URL: String = "https://www.omdbapi.com"
    const val API_KEY: String = "f9d1ae80"
    const val SEARCH_MOVIES: String = "/"
}

enum class RESPONSE_STATE{
    OKAY,
    FAIL
}