package com.example.movie.request

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.movie.AppExecutors
import com.example.movie.models.MovieModel
import com.example.movie.response.MovieSearchResponse
import com.example.movie.utils.Credentials
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class MovieApiClient private constructor() {

    var appExecutors = AppExecutors()
    private val viewModelMovies: MutableLiveData<List<MovieModel>?>
    var retrieveMoviesRunnable: RetrieveMoviesRunnable? = null
    val movies: MutableLiveData<List<MovieModel>?>
        get() = viewModelMovies

    companion object {
        var instance: MovieApiClient? = null
            get() {
                if (field == null) {
                    field = MovieApiClient()
                }
                return field
            }
            private set
    }

    init {
        viewModelMovies = MutableLiveData()
    }


    fun searchMoviesApi(query: String, pageNumber: Int) {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null
        }
        val retrieveMoviesRunnable = RetrieveMoviesRunnable(query, pageNumber)
        val retroFitHandler: Future<*> =
            appExecutors.getInstance()?.getNetworkIO()!!.submit(retrieveMoviesRunnable)
        appExecutors.getInstance()!!.getNetworkIO()?.schedule(Runnable {
            @Override
            fun run() {
                retroFitHandler.cancel(true)

            }
        }, 5000, TimeUnit.MILLISECONDS)
    }

    inner class RetrieveMoviesRunnable(
        private val query: String,
        private val pageNumber: Int
    ) :
        Runnable {

        var cancelRequest = false
        override fun run() {
            try {
                if (query.length != 0) {
                    val response = getMovies(query, pageNumber)?.execute()
                    responseData(response)
                }
//                else {
//                    val response = getPopularMovies(pageNumber)?.execute()
//                    responseData(response)
//                }
                else{
                    val response = getLatestMovies(pageNumber)?.execute()
                    Log.v("Tag","Latest"+response?.body())
                    responseData(response)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        fun responseData(response: Response<MovieSearchResponse?>?){
            if (cancelRequest) {
                return
            }
            if (response!!.code() == 200) {
                val list = response.body()!!.movies as java.util.ArrayList<MovieModel>?
                if (pageNumber == 1) {
                    viewModelMovies?.postValue(list as List<MovieModel>?)
                } else {
                    val currentMovies =
                        viewModelMovies.value as java.util.ArrayList<MovieModel>?
                    currentMovies?.addAll(list!!)
                    viewModelMovies.postValue(currentMovies!!)

                }

            } else {
                val error = response.errorBody().toString()
                Log.v("Tag", "Error" + error)
                viewModelMovies.postValue(null)
            }

        }

        //Search Method
        private fun getMovies(query: String, pageNumber: Int): Call<MovieSearchResponse?>? {
            val service = Service()
            val credentials = Credentials()
            return service.getMovieApi().searchMovie(
                credentials.API_KEY, query, pageNumber
            )
        }
        private fun getPopularMovies( pageNumber: Int): Call<MovieSearchResponse?>? {
            val service = Service()
            val credentials = Credentials()
            return service.getMovieApi().searchForPopularMovies(
                credentials.API_KEY
            )
        }
        private fun getLatestMovies( pageNumber: Int): Call<MovieSearchResponse?>? {
            val service = Service()
            val credentials = Credentials()
            return service.getMovieApi().searchForLatestMovies(
                credentials.API_KEY
            )
        }

        private fun cancelRequest() {
            Log.v("Tag", "Cancel retrofit Search Request")
            cancelRequest = true
        }
    }

}