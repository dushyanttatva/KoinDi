package com.example.koindi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.koindi.data.api.MovieApi
import com.example.koindi.data.api.SafeApiRequest
import com.example.koindi.data.mapper.toDomain
import com.example.koindi.data.model.Movie
import com.example.koindi.data.paging.MovieListPagingSource
import kotlinx.coroutines.flow.Flow


class MovieRepository(
    private val apiService: MovieApi
): SafeApiRequest() {
    suspend fun getMovieList(page: Int = 1): List<Movie> {
        val response = apiRequest {
            apiService.getMovieList(page)
        }
        return response.results.map {
            it.toDomain()
        }
    }

    fun getMoviePagingData(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { MovieListPagingSource(this) }
        ).flow
    }
}