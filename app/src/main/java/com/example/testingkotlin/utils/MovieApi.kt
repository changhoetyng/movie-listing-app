package com.example.testingkotlin.utils

import com.example.testingkotlin.response.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface MovieApi {
//    Search for movies
    @GET("/3/discover/movie")
    fun searchMovie(
    @Query("api_key") key: String,
    @Query("query") query: String,
    @Query("page") page: String
    ): Call<MovieSearchResponse>
}