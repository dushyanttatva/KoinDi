package com.example.koindi.ui.screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleAnimation() {
    var sizeState by remember { mutableStateOf(200.dp) }
    val size by animateDpAsState(
        targetValue = sizeState,
//        keyframes {
//            durationMillis = 5000
//            sizeState at 0 using LinearEasing
//            sizeState * 1.5f at 1000 using FastOutLinearInEasing
//            sizeState * 2f at 5000
//        },
//        spring(
//            Spring.DampingRatioHighBouncy
//        ),
        tween(
            durationMillis = 1000,
            delayMillis = 0,
            easing = LinearOutSlowInEasing),
        label = ""
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Column {

        Box(modifier = Modifier
            .size(size)
            .background(color),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                sizeState += 50.dp
            }) {
                Text(text = "Increase size")
            }
        }

    }
}