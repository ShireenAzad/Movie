package com.example.movie.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie.models.MovieModel
import com.example.movie.repositories.MovieRepository

class MovieListViewModel: ViewModel() {

    var movieRepository=MovieRepository()
    fun MovieListViewModel() {
        movieRepository = MovieRepository.instance!!

    }
    fun getMovies(): MutableLiveData<List<MovieModel>?> {

        return movieRepository.movies
    }

    fun searchMoviesApi(query: String,pageNumber:Int) {
        movieRepository.searchMovieApi(query,pageNumber)
        
    }
    fun searchNextPage() {
        movieRepository.searchNextPage()

    }
}