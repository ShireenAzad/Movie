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
import com.example.movie.viewmodels.MovieListViewModel


class MainActivity : AppCompatActivity(), OnMovieListener {
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
        searchMovieApi("",1)
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
                if (!recyclerView!!.canScrollVertically(1) && (newState==RecyclerView.SCROLL_STATE_IDLE)) {
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