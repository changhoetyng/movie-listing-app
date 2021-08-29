package com.example.testingkotlin.adapters

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testingkotlin.R

class MovieViewHolder(itemView: View, val onMovieListener: OnMovieListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
    // Widgets
    var title : TextView = itemView.findViewById(R.id.movie_title)
    var release_date : TextView = itemView.findViewById(R.id.movie_category)
    var duration : TextView = itemView.findViewById(R.id.movie_duration)
    var imageView : ImageView = itemView.findViewById(R.id.movie_img)
    var ratingBar : RatingBar = itemView.findViewById(R.id.rating_bar)
//    var release_date : TextView = itemView.findViewById(R.id.rating_bar)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        onMovieListener.onMovieClick(adapterPosition)
    }
}