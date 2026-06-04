package com.example.a210135_nicolechengkee_drnazatul_project1
import com.example.a210135_nicolechengkee_drnazatul_project1.data.*

sealed class Screen(val route: String) {
    object Home   : Screen("home")
    object Jobs   : Screen("jobs")
    object Chat   : Screen("chat")
    object Profile: Screen("profile")

    object Detail : Screen("detail/{jobId}") {
        fun createRoute(jobId: Int) = "detail/$jobId"
    }
    object Apply  : Screen("apply/{jobId}") {
        fun createRoute(jobId: Int) = "apply/$jobId"
    }
}
