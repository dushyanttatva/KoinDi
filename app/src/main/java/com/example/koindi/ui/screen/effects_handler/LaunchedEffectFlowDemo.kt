package com.example.koindi.ui.screen.effects_handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun LaunchedEffectFlowDemo(
    viewModel: LaunchedEffectViewModel
) {
    // if key1 is true, the effect will run only once when the composable is first composed
    LaunchedEffect(key1 = true){
        viewModel.sharedFlow.collect { event ->
            when (event) {
                is LaunchedEffectViewModel.ScreenEvents.ShowSnackBar -> {
                    // Handle showing snack-bar
                }
                is LaunchedEffectViewModel.ScreenEvents.NavigateTo -> {
                    // Handle navigation
                }
            }
        }
    }
}