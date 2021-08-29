package com.example.testingkotlin.utils

import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.response.MovieSearchResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
//    Search for movies
    @GET("/3/search/movie")
    fun searchMovie(
    @Query("api_key") key: String,
    @Query("query") query: String,
    @Query("page") page: String
    ): Call<MovieSearchResponse>

//    Search with ID
    @GET("/3/movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") key: String
    ): Call<MovieModel>
}