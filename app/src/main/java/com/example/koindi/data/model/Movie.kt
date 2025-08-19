package com.example.koindi.data.model

data class Movie(
    val id: Int,
    val title: String?,
    val overview: String?,
//    @SerializedName("poster_path")
    val posterPath: String?,
//    @SerializedName("release_date")
    val releaseDate: String?
)
