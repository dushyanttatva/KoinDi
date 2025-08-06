package com.example.koindi.di

import com.example.koindi.data.api.MovieApi
import com.example.koindi.data.repository.MovieRepository
import com.example.koindi.ui.viewmodel.MovieViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module{

    val baseUrl = "https://api.themoviedb.org/3/"
    val bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwNjNiOGE2Y2ZmYTkxYTUwMzEyODFiNDU5M2M2NDE4ZSIsIm5iZiI6MTc1MDY3Mjk2My42ODksInN1YiI6IjY4NTkyNjQzNWNkMDMxMTdkYjUzZDA5ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.geIiMfQ63x1fYXGHPZLYY7zbL1k-KueKPS9MfPHhf6M"

    // OkHttp client
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithAuth = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $bearerToken")
                    .build()
                chain.proceed(requestWithAuth)
            }
            .build()
    }

    // Retrofit instance
    single {
        Retrofit.Builder()
            .baseUrl(baseUrl)
//            .client(get<OkHttpClient>())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // MovieApi service
    single<MovieApi> {
        get<Retrofit>().create(MovieApi::class.java)
    }

    single { MovieRepository(get()) }

    viewModel { MovieViewModel(get()) }
}