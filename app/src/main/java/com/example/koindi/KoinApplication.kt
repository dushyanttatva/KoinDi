package com.example.koindi

import android.app.Application
import com.example.koindi.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize Koin or any other dependency injection framework here
         startKoin {
             androidContext(this@KoinApplication)
             modules(appModule)
         }
    }
}