package com.example.movieapplication

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieapplication.screens.DetailScreen
import com.example.movieapplication.screens.FavoritesScreen
import com.example.movieapplication.screens.HomeScreen
import com.example.movieapplication.viewmodels.FavoritesViewModel

@Composable
fun MovieNavigation() {
    val navController = rememberNavController()

    val favoritesViewModel: FavoritesViewModel = viewModel()

    NavHost(navController = navController, startDestination = "homescreen") {
        composable("homescreen") {
            HomeScreen(navController = navController, viewModel = favoritesViewModel)
        }
        composable(
            "detailscreen/{movieId}",
            arguments = listOf(navArgument("movieId") {})
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                viewModel = favoritesViewModel,
                movieId = backStackEntry.arguments?.getString("movieId")
            )
        }
        composable(
            "favoritesscreen"
        ) {
            FavoritesScreen(navController, viewModel = favoritesViewModel)
        }
    }
}