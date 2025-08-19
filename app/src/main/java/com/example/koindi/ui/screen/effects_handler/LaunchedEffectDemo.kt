package com.example.koindi.ui.screen.effects_handler

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun LaunchedEffectDemo() {
    var text by remember { mutableStateOf("") }
    LaunchedEffect(key1 = text) {
        delay(1000L)
        println("The text is $text")
    }

}