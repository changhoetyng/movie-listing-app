package com.example.testingkotlin.request

import com.example.testingkotlin.utils.Credentials
import com.example.testingkotlin.utils.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Servicey {
    companion object{
        fun getMovieApi(): MovieApi {
            return Retrofit.Builder().baseUrl(Credentials.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build().create(MovieApi::class.java);
        }
    }
}