package com.example.testingkotlin.response

import com.example.testingkotlin.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MovieResponse {
    // Finding the movie object
    @SerializedName("results")
    @Expose
    var movie : MovieModel? = null;
}