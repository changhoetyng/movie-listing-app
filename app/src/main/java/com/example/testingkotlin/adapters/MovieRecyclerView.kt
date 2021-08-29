package com.example.testingkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testingkotlin.R
import com.example.testingkotlin.models.MovieModel

class MovieRecyclerView(val onMovieListener: OnMovieListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mMovies : List<MovieModel>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item,
        parent, false)

        return MovieViewHolder(view, onMovieListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).title.text = mMovies?.get(position)!!.title
        holder.release_date.text = mMovies?.get(position)!!.release_date
        holder.duration.text = mMovies?.get(position)?.runtime.toString()
        //Rating is 10 so 5 star divide by 2
        holder.ratingBar.rating = ((mMovies?.get(position)!!.vote_average!!.toFloat()) / 2)

        // ImageView: Using Glide Library
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500/" + mMovies?.get(position)!!.poster_path)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        if(mMovies != null) {
            return mMovies!!.size
        }
        return 0
    }

}