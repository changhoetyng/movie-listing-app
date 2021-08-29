package com.example.testingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.testingkotlin.models.MovieModel

class MovieDetails : AppCompatActivity() {

    var imageViewDetails: ImageView? = null
    var titleDetails: TextView? = null
    var descDetails: TextView? = null
    var ratingBarDetails: RatingBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        imageViewDetails = findViewById(R.id.imageView_details)
        titleDetails = findViewById(R.id.textView_title_details)
        descDetails = findViewById(R.id.textView_desc_details)
        ratingBarDetails = findViewById(R.id.ratingBar_details)

        GetDataFromIntent()
    }

    private fun GetDataFromIntent() {
        if (intent.hasExtra("movie")) {
            val movieModel: MovieModel? = intent.getParcelableExtra("movie")
            titleDetails?.text = movieModel?.title
            descDetails?.text = movieModel?.overview
            ratingBarDetails?.rating = (movieModel?.vote_average!! / 2)

            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/"
                        + movieModel.poster_path)
                .into(imageViewDetails!!)
        }
    }
}