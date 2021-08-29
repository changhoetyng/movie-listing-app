package com.example.testingkotlin.response

import com.example.testingkotlin.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// Getting multiple movies - popular
class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    var total_count : Int? = null;

    @SerializedName("results")
    @Expose
    var movies : List<MovieModel>? = null;

//    override fun toString(): String {
//        return "MovieSearchResponse{" +
//                "total_count=" + total_count +
//                "movies=" + movies +
//                "}"
//    }
}