package com.example.koindi.ui.screen.effects_handler

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun DerivedStateOfDemo() {
    var counter by remember { mutableStateOf(0) }
//    val counterText = "The counter is $counter"
    val counterText by derivedStateOf {
        "The counter is $counter"
    }
    Button(onClick = {
        counter++
    }) {
        Text(text = counterText)
    }
}