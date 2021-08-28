package com.example.testingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.repositories.MovieRepository
import com.example.testingkotlin.request.Servicey
import com.example.testingkotlin.response.MovieSearchResponse
import com.example.testingkotlin.utils.Credentials
import com.example.testingkotlin.utils.MovieApi
import com.example.testingkotlin.viewmodels.MovieListViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListActivity : AppCompatActivity() {

    private lateinit var movieListViewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]

        observeAnychange()

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener {
            searchMovieApi("Jack Reacher", 1)

        }
    }

//    Observing any data change
    private fun observeAnychange() {
    movieListViewModel.getMovies()?.observe(this,
        { movieModels ->
            if(movieModels != null) {
                for (movieModel : MovieModel in movieModels) {
                    Log.v("Tag", "onChanged:" + movieModel.title)
                }
            }
        })
    }

    // 4 - Calling method in Main Activity
    private fun searchMovieApi(query: String, pageNumber: Int) {
        movieListViewModel.searchMovieApi(query, pageNumber)
    }


    private fun getRetrofitResponse() {
        val movieApi: MovieApi = Servicey.getMovieApi()
        val responseCall: Call<MovieSearchResponse> = movieApi.searchMovie(
            Credentials.API_KEY,
            "Jack Reacher",
            "1"
        )

        responseCall.enqueue(object : Callback<MovieSearchResponse?> {
            override fun onResponse(
                call: Call<MovieSearchResponse?>,
                response: Response<MovieSearchResponse?>
            ) {
                if (response.code() == 200) {
//                    Log.v("Tag", "the response" + response.body().toString())
                    Log.v("Tag", GsonBuilder().setPrettyPrinting().create().toJson(response.body()))

//                    val movies: List<MovieModel> = ArrayList(response.body()?.movies)
//
//                    for (movie: MovieModel in movies) {
//                        Log.v("Tag", "the List" + movie.release_date)
//                    }
                } else {
                    try {
                        Log.v("Tag", "Error" + (response.errorBody()?.string() ?: ""));
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieSearchResponse?>, t: Throwable) {
                t.printStackTrace();
            }
        })
    }

    private fun getRetrofitResponseAccordingToID() {
        val movieApi: MovieApi = Servicey.getMovieApi()
        val responseCall: Call<MovieModel> = movieApi.getMovie(
            550,
            Credentials.API_KEY
        )
        responseCall.enqueue(object : Callback<MovieModel?> {
            override fun onResponse(call: Call<MovieModel?>, response: Response<MovieModel?>) {
                if (response.code() == 200) {
                    Log.v("Tag", GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                    val movie: MovieModel? = response.body()
                    Log.v("Tag", "Movie Title   " + movie?.title)
                } else {
                    try {
                        Log.v("Tag", "response not 200")
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<MovieModel?>, t: Throwable) {
                Log.v("Tag", "Request Failed")
            }
        })
    }
}