package com.example.koindi.data.mapper

import com.example.koindi.data.model.Movie
import com.example.koindi.data.model.MovieDto

fun MovieDto.toDomain(): Movie = Movie(
    id, title, overview, posterPath, releaseDate
)