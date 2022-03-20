package com.example.movie.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class MovieModel(
    @SerializedName("id")
    val movieId: Int,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Float
) : Parcelable {


}