package com.example.koindi.ui.screen.effects_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun DisposableEffectDemo() {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    println("Lifecycle started")
                }
                Lifecycle.Event.ON_PAUSE -> {
                    println("Lifecycle paused")
                }
                Lifecycle.Event.ON_STOP -> {
                    println("Lifecycle stopped")
                }
                else -> {
                    // Handle other lifecycle events if needed
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            println("Observer disposed")
        }
    }
}