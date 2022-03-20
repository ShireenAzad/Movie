package com.example.movie.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class MovieModel : Parcelable {

        var title: String?
            private set
        var poster_path: String?
            private set
        var release_date: String?
            private set
    @SerializedName("id")
        var movie_id: Int? = null
            private set
        var vote_average: Float
            private set
        var overview: String?
            private set
        var runTime:Int? = null
            private set

        constructor(
            title: String?,
            poster_path: String?,
            release_date: String?,
            movie_id: Int?,
            vote_average: Float,
            overview: String?,
            runTime:Int?
        ) {
            this.title = title
            this.poster_path = poster_path
            this.release_date = release_date
            this.movie_id = movie_id
            this.vote_average = vote_average
            this.overview = overview
            this.runTime=runTime
        }

        protected constructor(`in`: Parcel) {
            title = `in`.readString()
            poster_path = `in`.readString()
            release_date = `in`.readString()
            movie_id = if (`in`.readByte().toInt() == 0) {
                null
            } else {
                `in`.readInt()
            }
            vote_average = `in`.readFloat()
            overview = `in`.readString()
            runTime    = if (`in`.readByte().toInt() == 0) {
                null
            } else {
                `in`.readInt()
            }
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        companion object CREATOR : Parcelable.Creator<MovieModel> {
            override fun createFromParcel(parcel: Parcel): MovieModel {
                return MovieModel(parcel)
            }

            override fun newArray(size: Int): Array<MovieModel?> {
                return arrayOfNulls(size)
            }
        }

    }

