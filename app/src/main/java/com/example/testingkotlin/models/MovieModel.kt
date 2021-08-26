package com.example.testingkotlin.models

import android.os.Parcel
import android.os.Parcelable

class MovieModel() : Parcelable{
    var title: String? = null
    var release_date : String? = null
    var poster_path: String? = null
    var movie_id: Int? = null
    var vote_average: Float? = null
    var movie_overview: String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        poster_path = parcel.readString()
        movie_id = parcel.readValue(Int::class.java.classLoader) as? Int
        vote_average = parcel.readValue(Float::class.java.classLoader) as? Float
        movie_overview = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(poster_path)
        parcel.writeValue(movie_id)
        parcel.writeValue(vote_average)
        parcel.writeString(movie_overview)
    }

    override fun describeContents(): Int {
        return 0
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
