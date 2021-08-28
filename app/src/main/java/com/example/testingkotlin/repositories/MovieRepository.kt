package com.example.testingkotlin.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.request.MovieApiClient

object MovieRepository {


    val movieApiClient: MovieApiClient = MovieApiClient

//    companion object{
//        private var instance: MovieRepository? = null
//        fun getMovieRepositoryInstance(): MovieRepository? {
//            if(instance == null) {
//                instance = MovieRepository()
//            }
//            return instance
//        }
//    }
//
    fun getMovies(): MutableLiveData<MutableList<MovieModel>>? {
        return movieApiClient.mMovies
    }

    // 2 - Calling Method Movie repo

    fun searchMovieApi(query: String, pageNumber: Int) {
        movieApiClient.searchMoviesApi(query, pageNumber)
    }

}