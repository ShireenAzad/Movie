package com.example.movie.repositories

import androidx.lifecycle.MutableLiveData
import com.example.movie.models.MovieModel
import com.example.movie.request.MovieApiClient

class MovieRepository() {
    private var mQuery: String? = null
    private var mPageNumber = 0
    val movieApiClient: MovieApiClient
    val movies: MutableLiveData<List<MovieModel>?>
        get() = movieApiClient.movies

    companion object {
        var instance: MovieRepository? = null
            get() {
                if (field == null) {
                    field = MovieRepository()
                }
                return field
            }
            private set
    }

    init {
        movieApiClient = MovieApiClient.instance!!
    }
    fun searchMovieApi(query:String,pageNumber:Int){
        mQuery=query
        mPageNumber=pageNumber
        movieApiClient.searchMoviesApi(query,pageNumber)

    }

    fun searchNextPage() {
        mQuery?.let { searchMovieApi(it, mPageNumber + 1) }
    }

}