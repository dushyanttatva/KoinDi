package com.example.koindi.ui.screen.effects_handler

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@Composable
fun SnapShotFlowDemo() {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = snackBarHostState) {
        snapshotFlow { snackBarHostState.currentSnackbarData }
            .mapNotNull { it } // Filter out null values
            .distinctUntilChanged()
            .collect { snackBarData ->
                println("Current Snack-bar Data: $snackBarData")
            }
    }
}