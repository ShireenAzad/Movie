package com.example.movie.adapters

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.R

class MovieViewHolder(itemView: View, onMovieListener: OnMovieListener?) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var title: TextView
    var releaseDate: TextView
    var imageView: ImageView
    var ratingBar: TextView
    var onMovieListener: OnMovieListener? = null
    override fun onClick(view: View) {
        onMovieListener!!.onMovieClick(adapterPosition)
    }

    init {
        this.onMovieListener=onMovieListener
        title = itemView.findViewById(R.id.movieTitle)
        releaseDate = itemView.findViewById(R.id.releaseDate)
        imageView = itemView.findViewById(R.id.imageView)
        ratingBar = itemView.findViewById(R.id.ratingBar)
        itemView.setOnClickListener(this)
    }
}