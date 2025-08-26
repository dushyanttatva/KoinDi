package com.example.koindi.ui.screen

sealed class Screen (val route: String) {

    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen")
    object MovieListScreen : Screen("movie_list_screen")
    object TextInputScreen : Screen("text_input_screen")
    object TextFieldsButtons : Screen("text_fields_screen")
    object SimpleAnimation : Screen("animation_screen")
    object ListScreen : Screen("list_screen")
    object ConstraintLayoutExample : Screen("constrain_screen")
    object TimerScreen : Screen("timer_screen")
    object CircularProgressBar : Screen("circular_bar_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            if (args.isNotEmpty()) {
                args.forEach { arg ->
                    append("/$arg")
                }
            }
        }
    }
}