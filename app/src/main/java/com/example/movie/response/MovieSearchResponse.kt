package com.example.movie.response

import com.example.movie.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//This class is for getting multple movies(Movies list)-popular movies
class MovieSearchResponse {
    @SerializedName("total_results")
    @Expose
    private val total_count = 0

    @SerializedName("results")
    @Expose
    val movies: List<MovieModel?>? = null

    override fun toString(): String {
        return "MovieSearchResponsey{" +
                "total_count=" + total_count +
                ", movies=" + movies +
                '}'
    }

}

