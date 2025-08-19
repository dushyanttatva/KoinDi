package com.example.koindi.ui.screen.effects_handler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LaunchedEffectViewModel: ViewModel() {

    private val _sharedFlow = MutableSharedFlow<ScreenEvents>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            _sharedFlow.emit(ScreenEvents.ShowSnackBar("Welcome to the app!"))
        }
    }

    sealed class ScreenEvents {
        data class ShowSnackBar(val message: String): ScreenEvents()
        data class NavigateTo(val route: String): ScreenEvents()
    }
}