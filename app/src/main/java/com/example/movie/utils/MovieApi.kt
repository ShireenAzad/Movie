package com.example.movie.utils

import com.example.movie.models.MovieModel
import com.example.movie.response.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //Search for movies
    @GET("/3/search/movie")
    open fun searchMovie(
        @Query("api_key") key: String?,
        @Query("query") query: String?,
        @Query("page") page: Int
    ): Call<MovieSearchResponse?>?

    @GET("3/movie/{movie_id}?")
    open fun getMovie(
        @Path("movie_id") movie_id: Long?,
        @Query("api_key") key: String?
    ): Call<MovieModel?>?


}


