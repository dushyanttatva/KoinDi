package com.example.koindi.data.api

import com.example.koindi.data.model.MovieListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMovieList(
        @Query("page") page: Int
    ): Response<MovieListResponseDto>
}