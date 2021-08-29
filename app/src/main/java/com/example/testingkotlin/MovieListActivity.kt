package com.example.testingkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.databinding.adapters.AbsListViewBindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testingkotlin.adapters.MovieRecyclerView
import com.example.testingkotlin.adapters.OnMovieListener
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

class MovieListActivity : AppCompatActivity(), OnMovieListener {

    private var movieListViewModel: MovieListViewModel? = null

    //Recycler View
    private var recyclerView: RecyclerView? = null
    private var movieRecyclerAdapter: MovieRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // SearchView
        SetupSearchView()

        movieListViewModel = ViewModelProvider(this)[MovieListViewModel::class.java]
        observeAnychange()
        recyclerView = findViewById(R.id.recyclerView)
        ConfigureRecyclerView()
//        searchMovieApi("fast", 1)
    }

    private fun SetupSearchView() {
        // Get data from searchView
        val searchView: SearchView = findViewById(R.id.search_view)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    movieListViewModel?.searchMovieApi(
                        // The search string got from searchView
                        query,
                        1
                    )
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    //    Observing any data change
    private fun observeAnychange() {
    movieListViewModel?.getMovies()?.observe(this,
        { movieModels ->
            if(movieModels != null) {
//                for (movieModel : MovieModel in movieModels) {
//                    Log.v("Tag", "onChanged:" + movieModel.title)
                    movieRecyclerAdapter?.mMovies = movieModels
//                }
            }
        })
    }

    // 4 - Calling method in Main Activity
    private fun searchMovieApi(query: String, pageNumber: Int) {
        movieListViewModel?.searchMovieApi(query, pageNumber)
    }

    // Initializing Recycle View
    fun ConfigureRecyclerView() {
        //LiveData cannot be passed via constructor
        movieRecyclerAdapter = MovieRecyclerView(this)
        recyclerView?.adapter = movieRecyclerAdapter
        recyclerView?.layoutManager = LinearLayoutManager(this)

        // RecyclerView pagination
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recyclerView.canScrollVertically(1)) {
                    movieListViewModel?.searchNextPage()
                }
            }
        })

    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(this, MovieDetails::class.java)
        intent.putExtra("movie", movieRecyclerAdapter?.getSelectedMovie(position))
        startActivity(intent)

        //Toast.makeText(this,"The position" + position, Toast.LENGTH_SHORT).show()
    }

    override fun onCategory(category: String) {
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