package com.example.movie.request

import com.example.movie.utils.Credentials
import com.example.movie.utils.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Service {
   var credentials=Credentials()
    private val retrofitBuilder = Retrofit.Builder().baseUrl(credentials.BASE_URL)

        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    private val movieApi: MovieApi = retrofit.create(MovieApi::class.java)
    fun getMovieApi(): MovieApi {
        return movieApi;
    }

}