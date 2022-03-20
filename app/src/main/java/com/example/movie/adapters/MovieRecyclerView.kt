package com.example.movie.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.with
import com.example.movie.R
import com.example.movie.models.MovieModel
import com.example.movie.utils.Credentials

class MovieRecyclerView(private val onMovieListener: OnMovieListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var viewModelMovies: List<MovieModel>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view, onMovieListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieViewHolder).title.text = viewModelMovies!![position].title
        holder.releaseDate.text = viewModelMovies!![position].releaseDate
        holder.ratingBar.text = (viewModelMovies!![position].voteAverage / 2).toString()
        with(holder.itemView.context).load(Credentials().IMAGE_URI + viewModelMovies!![position].posterPath)
            .into(holder.imageView)
//        with(holder.itemView.context)
//            .load(viewModelMovies!![position].poster_path)
//            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_background))
//            .into(holder.imageView);
    }

    override fun getItemCount(): Int {
        if (viewModelMovies != null) {
            return viewModelMovies!!.size
        }
        return 0
    }

    fun setviewModelMovies(viewModelMovies: List<MovieModel>?) {
        this.viewModelMovies = viewModelMovies
        notifyDataSetChanged()
    }
    fun getIdOfMovieSelected(position: Int): MovieModel? {
        if (viewModelMovies != null) {
            if (viewModelMovies!!.isNotEmpty()) {
                return viewModelMovies!![position]
            }
        }
        return null
    }


}
