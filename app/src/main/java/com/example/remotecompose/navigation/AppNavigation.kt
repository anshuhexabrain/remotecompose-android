package com.example.remotecompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.remotecompose.MainUiState
import com.example.remotecompose.ui.screen.RemoteScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            RemoteScreen(
                configUrl = MainUiState.configUrlForScreen("home"),
                title = "Sample App",
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("detail") {
            RemoteScreen(
                configUrl = MainUiState.configUrlForScreen("detail"),
                title = "Detail",
                showBack = true,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("estimates") {
            RemoteScreen(
                configUrl = MainUiState.configUrlForScreen("estimates"),
                title = "Estimates",
                showBack = true,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
        composable("estimate_detail") {
            RemoteScreen(
                configUrl = MainUiState.configUrlForScreen("estimate_detail"),
                title = "Estimate Detail",
                showBack = true,
                onBack = { navController.popBackStack() },
                onNavigate = { route -> navController.navigate(route) }
            )
        }
    }
}
