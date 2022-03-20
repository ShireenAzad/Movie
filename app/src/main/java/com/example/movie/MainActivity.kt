package com.example.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie.adapters.MovieRecyclerView
import com.example.movie.adapters.OnMovieListener
import com.example.movie.databinding.ActivityMainBinding
import com.example.movie.models.MovieModel
import com.example.movie.request.Service
import com.example.movie.response.MovieSearchResponse
import com.example.movie.utils.Credentials
import com.example.movie.utils.MovieApi
import com.example.movie.viewmodels.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MainActivity : AppCompatActivity(), OnMovieListener {
    val MOVIE = "movie"
    private var movieRecyclerAdapter: MovieRecyclerView? = null
    private var recyclerView: RecyclerView? = null
    private var movieListViewModel: MovieListViewModel? = null
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        setSupportActionBar(activityMainBinding.toolBar)
        movieListViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
        recyclerView = activityMainBinding.recyclerView
        searchMovieByKeyWord()
        searchMovieApi("jack",1)
        configRecyclerView()
        ObserveAnyChange()


    }
    private fun ObserveAnyChange() {
        movieListViewModel!!.getMovies().observe(
            this
        ) { movieModels ->
            if (movieModels != null) {
                for (movieModel in movieModels) {
                    Log.v("Tag", "Movie Data" + movieModel.title)
                    movieRecyclerAdapter?.setviewModelMovies(movieModels)
                }
            }
        }
        run {}
    }

    private fun searchMovieByKeyWord() {
        val searchView: SearchView = activityMainBinding.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                movieListViewModel?.searchMoviesApi(
                    query,
                    1
                )
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
    }

    fun getRetrofitResponse() {
        val service = Service()
        val credentials = Credentials()
        val movieApi: MovieApi = service.getMovieApi()
        val responseCall = movieApi.searchMovie(
            credentials.API_KEY,
            " Jack Reacher",
            1
        )
        responseCall?.enqueue(object : Callback<MovieSearchResponse?> {
            override fun onFailure(call: Call<MovieSearchResponse?>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<MovieSearchResponse?>,
                response: Response<MovieSearchResponse?>
            ) {
                if (response.code() == 200) {
                    Log.v("Tag", "the message" + response.body().toString())
                    val movies: ArrayList<MovieModel?> = ArrayList(response.body()?.movies)
                    for (movie in movies) {
                        Log.v("Tag", "The list" + movie?.releaseDate)
                        print(1)
                    }


                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().toString())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        })
    }

    private fun getRetroFitResponseById() {
        val service = Service()
        val movieApi = service.getMovieApi()
        val credentials = Credentials()
        val responseCall = movieApi.getMovie(550, credentials.API_KEY)
        responseCall!!.enqueue(object : Callback<MovieModel?> {
            override fun onResponse(call: Call<MovieModel?>, response: Response<MovieModel?>) {
                if (response.code() == 200) {
                    val movie = response.body()
                    Log.v("Tag", "Movie Title" + movie?.title)
                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().toString())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }

            override fun onFailure(call: Call<MovieModel?>, t: Throwable) {

            }
        })
    }

    private fun searchMovieApi(query: String, pageNumber: Int) {
        movieListViewModel?.searchMoviesApi(query, pageNumber)

    }

    private fun configRecyclerView() {
        movieRecyclerAdapter = MovieRecyclerView(this)
        recyclerView?.adapter = movieRecyclerAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this)
        setRecyclerViewScrollListener()

    }

    private fun setRecyclerViewScrollListener() {
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView!!.canScrollVertically(1)) {
                    movieListViewModel?.searchNextPage()
                }
            }

        }
        recyclerView?.addOnScrollListener(scrollListener)
    }

    override fun onMovieClick(position: Int) {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("movie", movieRecyclerAdapter?.getIdOfMovieSelected(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String?) {
        TODO("Not yet implemented")
    }


}