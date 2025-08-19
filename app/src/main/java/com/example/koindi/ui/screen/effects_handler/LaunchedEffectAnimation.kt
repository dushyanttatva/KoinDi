package com.example.koindi.ui.screen.effects_handler

import androidx.compose.animation.core.Animatable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun LaunchedEffectAnimation(
    counter: Int
) {
    val animatable = remember {
        Animatable(0f)
    }
    // When the counter changes, it will cancel the old animation and launch a new one
    LaunchedEffect(key1 = counter) {
        animatable.animateTo(counter.toFloat())
    }
}