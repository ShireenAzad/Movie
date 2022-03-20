package com.example.movie.response

import com.example.movie.models.MovieModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//This class is for aingle movie request
class MovieResponse {
    //Finding the movie object
    @SerializedName("results")
    @Expose
    val movie: MovieModel? = null

    override fun toString(): String {
        return "MovieResponse{" +
                "movie=" + movie +
                '}'
    }

}

