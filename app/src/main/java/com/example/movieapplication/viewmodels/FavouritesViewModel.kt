package com.example.movieapplication.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.movieapplication.models.Movie

class FavoritesViewModel : ViewModel() {

    private val _favoriteMovies = mutableStateListOf<Movie>()
    val favoriteMovies: List<Movie>
        get() = _favoriteMovies

    fun addFavMovie(movie: Movie) {
        if (!exists(movie = movie)) {
            _favoriteMovies.add(movie)
        }
    }

    fun removeFavMovie(movie: Movie) {
        _favoriteMovies.remove(movie)
    }

    private fun exists(movie: Movie): Boolean {
        return _favoriteMovies.any { m -> m.id == movie.id }
    }

    fun checkIfAlreadyFavMovie(movie: Movie): Boolean { //: Boolean
        return exists(movie)
    }

}