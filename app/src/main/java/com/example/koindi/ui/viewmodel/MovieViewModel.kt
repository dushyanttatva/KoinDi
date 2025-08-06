package com.example.koindi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.koindi.data.model.Movie
import com.example.koindi.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieViewModel(
    private val repository: MovieRepository
): ViewModel() {

    val movies: Flow<PagingData<Movie>> = repository.getMoviePagingData()
        .cachedIn(viewModelScope)

}