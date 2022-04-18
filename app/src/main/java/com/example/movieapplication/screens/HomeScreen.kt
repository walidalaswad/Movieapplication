package com.example.movieapplication.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.getMovies
import com.example.movieapplication.ui.theme.MovieApplicationTheme
import com.example.movieapplication.viewmodels.FavoritesViewModel
import com.example.movieapplication.widgets.MovieRow


@Composable
fun HomeScreen(navController: NavController, viewModel: FavoritesViewModel) {

    var menu by remember {
        mutableStateOf(false)
    }

    MovieApplicationTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Movies") },
                    actions = {
                        IconButton(onClick = { menu = !menu }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                        }

                        DropdownMenu(expanded = menu, onDismissRequest = { menu = false }) {
                            DropdownMenuItem(onClick = { navController.navigate("favoritesscreen") }) {
                                Row {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Favorites",
                                        modifier = Modifier.padding(4.dp)
                                    )
                                    Text(
                                        text = "Favorites",
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .width(100.dp)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        ) {
            MainContent(navController, viewModel)
        }
    }

}

@Composable
fun MainContent(
    navController: NavController,
    favoritesViewModel: FavoritesViewModel,
    movieList: List<Movie> = getMovies()
) {

    var favourite by remember {
        mutableStateOf(false)
    }

    LazyColumn {
        items(movieList) { movie ->
            favourite = favoritesViewModel.checkIfAlreadyFavMovie(movie)
            MovieRow(
                movie = movie,
                alreadyFavMovie = favourite,
                onItemClick = { movieId -> navController.navigate("detailscreen/$movieId") },
                onFavoriteIconClick = {
                    favourite = favoritesViewModel.checkIfAlreadyFavMovie(movie)
                    if (favourite) {
                        favoritesViewModel.removeFavMovie(movie)
                        favourite = false
                    } else {
                        favoritesViewModel.addFavMovie(movie)
                        favourite = true
                    }
                },
                showFavIcon = true
            )
        }
    }
}



