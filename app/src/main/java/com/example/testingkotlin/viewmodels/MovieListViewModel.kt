package com.example.testingkotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.repositories.MovieRepository

class MovieListViewModel() : ViewModel(){

    val movieRepository : MovieRepository = MovieRepository

    fun getMovies() : MutableLiveData<MutableList<MovieModel>>? {
        return movieRepository.getMovies()
    }

    // 3 - Calling method in view model
    fun searchMovieApi(query: String, pageNumber: Int) {
        MovieRepository.searchMovieApi(query, pageNumber)
    }
}