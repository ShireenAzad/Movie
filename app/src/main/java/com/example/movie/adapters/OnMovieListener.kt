package com.example.movie.adapters

interface OnMovieListener {
    fun onMovieClick(position: Int)
    fun onCategoryClick(category: String?)
}
