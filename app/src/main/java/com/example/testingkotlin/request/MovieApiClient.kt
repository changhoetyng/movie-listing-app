package com.example.testingkotlin.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.testingkotlin.AppExecutors
import com.example.testingkotlin.models.MovieModel
import com.example.testingkotlin.response.MovieSearchResponse
import com.example.testingkotlin.utils.Credentials
import retrofit2.Call
import java.io.IOException
import java.util.concurrent.TimeUnit

object MovieApiClient {
    val mMovies: MutableLiveData<MutableList<MovieModel>> = MutableLiveData()
//    var retrieveMovieRunnable: RetrieveMoviesRunnable? = null

    fun searchMoviesApi(query: String, pageNumber : Int) {
        val retrieveMovieRunnable = RetrieveMoviesRunnable(query, pageNumber)
        val myHandler = AppExecutors.mNetworkIO().submit {
            retrieveMovieRunnable.run()
        }

        AppExecutors.mNetworkIO().schedule({
            myHandler?.cancel(true)
            retrieveMovieRunnable.cancelRequest()
        }, 5000, TimeUnit.MILLISECONDS)
//        schedule({myHandler.cancel(true)}, 5000, TimeUnit.MILLISECONDS)

        // We have 2 types of queries: ID and search Queries
    }

    class RetrieveMoviesRunnable(val query: String, val pageNumber : Int) : Runnable{
        private var cancelRequest: Boolean = false
        override fun run() {
            try {
                val response = getMovies(query, pageNumber).execute()
//                Log.v("Tag", response.body().toString())
//                Log.v("Tag", GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                if(cancelRequest) {
                    return
                }

                if (response.code() == 200) {
//                    Log.v("Tag", GsonBuilder().setPrettyPrinting().create().toJson(response.body()))
                    val list = ArrayList(response.body()?.movies)
                    if(pageNumber == 1) {
                        // PostValue: used for background thread
                        // setValue: not for background thread
                        mMovies.postValue(list)
                    } else {
                        var currentMovies : MutableList<MovieModel>? = mMovies.value
                        currentMovies?.addAll(list)
                        mMovies.postValue(currentMovies)
                    }
                } else {
                    val error: String = response.errorBody().toString()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mMovies?.postValue(null)
            }
        }

        private fun getMovies(query: String, pageNumber: Number) : Call<MovieSearchResponse> {
            Log.v("Tag", pageNumber.toString())
            return Servicey.getMovieApi().searchMovie(
                Credentials.API_KEY,
                query,
                pageNumber.toString()
            )
        }
        fun cancelRequest() {
            Log.v("Tag", "Cancelling Search Request")
            cancelRequest = true
        }
    }
//    companion object{
//        private var instance: MovieApiClient? = null
//
//        fun getMovieApiClientInstance(): MovieApiClient? = synchronized(this) {
//            if(instance == null) {
//                instance = MovieRepository()
//            }
//            return instance
//        }
//    }
}