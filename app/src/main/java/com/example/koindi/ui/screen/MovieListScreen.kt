package com.example.koindi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.koindi.data.model.Movie
import com.example.koindi.ui.viewmodel.MovieViewModel
import org.koin.androidx.compose.koinViewModel
import coil.compose.SubcomposeAsyncImage

@Composable
fun MovieListScreen() {
    val viewModel: MovieViewModel = koinViewModel()
    val movies = viewModel.movies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(movies.itemCount) { index ->
            val movie = movies[index]
            movie?.let {
                MovieItem(movie = it)
            }
        }
    }

    // Handling loading and error states in a single reusable function
    LoadStateView(state = movies.loadState.refresh)
    LoadStateView(state = movies.loadState.append)
}

@Composable
fun LoadStateView(state: LoadState) {
    when (state) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        is LoadState.Error -> {
            Text("Error: ${state.error.localizedMessage}", modifier = Modifier.padding(16.dp))
        }
        is LoadState.NotLoading -> {
            // No need to show anything for this state
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            modifier = Modifier.size(100.dp),
            loading = {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            },
            success = { successState ->
                Image(
                    painter = successState.painter,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop
                )
            },
            error = { Text("Error loading image", modifier = Modifier.align(Alignment.Center)) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            movie.title?.let {
                Text(text = it, fontWeight = FontWeight.Bold)
            }
            movie.overview?.let {
                Text(text = it, maxLines = 2, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}
