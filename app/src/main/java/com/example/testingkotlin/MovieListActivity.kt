package com.example.testingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.request.Servicey
import com.example.testingkotlin.response.MovieSearchResponse
import com.example.testingkotlin.utils.Credentials
import com.example.testingkotlin.utils.MovieApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener{
            getRetrofitResponse()
        }
    }

    private fun getRetrofitResponse() {
        val movieApi: MovieApi = Servicey.getMovieApi()
        val responseCall: Call<MovieSearchResponse> = movieApi.
        searchMovie(
            Credentials.API_KEY,
            "Jack Reacher",
            "1"
        )

        responseCall.enqueue(object : Callback<MovieSearchResponse?> {
            override fun onResponse(
                call: Call<MovieSearchResponse?>,
                response: Response<MovieSearchResponse?>
            ) {
                if(response.code() == 200) {
                    Log.v("Tag", "the response" + response.body().toString())

                    val movies : List<MovieModel> = ArrayList(response.body()?.movies)

                    for (movie: MovieModel in movies) {
                        Log.v("Tag", "the List" + movie.release_date)
                    }
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
}