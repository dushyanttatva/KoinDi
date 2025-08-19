package com.example.koindi.ui.screen.effects_handler

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun ProduceStateDemo(countUpTo: Int): State<Int> {
    return produceState(initialValue = 0) {
        while(value < countUpTo) {
            delay(1000)
            value++
        }
    }
}