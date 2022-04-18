package com.example.movieapplication.widgets


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.movieapplication.models.Movie
import com.example.movieapplication.models.getMovies
import com.example.movieapplication.ui.theme.Teal200

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieRow(
    movie: Movie,
    alreadyFavMovie: Boolean,
    onItemClick: (String) -> Unit = {},
    onFavoriteIconClick: () -> Unit = {},
    showFavIcon: Boolean
) {

    var info by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .height(
                if (!info) {
                    150.dp
                } else {
                    400.dp
                }
            )
            .fillMaxWidth()
            .padding(12.dp)
            .clickable { onItemClick(movie.id) },

        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 6.dp
            ) {
                //Icon(imageVector = Icons.Default.AccountBox, contentDescription = "movie pic")
                Image(
                    painter = rememberImagePainter(
                        data = movie.images[0],
                        builder = {
                            transformations(CircleCropTransformation())
                        }
                    ),
                    contentDescription = "Poster",
                    modifier = Modifier
                        .size(128.dp)
                        .padding(10.dp)
                )
            }

            Column {
                Text(text = movie.title)
                Text(text = "Director: " + movie.director)
                Text(text = "Released: " + movie.year)

                AnimatedVisibility(
                    visible = info,
                    enter = fadeIn(
                        // Fade in with the initial alpha of 0.3f.
                        initialAlpha = 0.3f
                    ),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text("Plot: " + movie.plot, Modifier.fillMaxWidth())
                        Divider(startIndent = 5.dp)
                        Text("Genre: " + movie.genre, Modifier.fillMaxWidth())
                        Text("Actors: " + movie.actors, Modifier.fillMaxWidth())
                        Text("Rating: " + movie.rating, Modifier.fillMaxWidth())
                    }
                }

                Icon(imageVector = if (info) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                    contentDescription = "arrowDown",
                    Modifier.clickable { info = !info })
            }
            if (showFavIcon) {
                FavoriteIcon(alreadyFavMovie, onFavoriteIconClick)
            }
        }
    }
}

@Composable
fun HorizontalScrollableImageView(movie: Movie = getMovies()[0]) {
    LazyRow {
        items(movie.images) { image ->

            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 4.dp
            ) {
                Image(
                    painter = rememberImagePainter(data = image),
                    contentDescription = "Movie Images"
                )
            }
        }
    }
}

@Composable
fun FavoriteIcon(alreadyFavMovie: Boolean, onFavoriteIconClick: () -> Unit = {}) {

    IconButton(onClick = { onFavoriteIconClick() }) {
        Icon(
            imageVector =
            if (alreadyFavMovie) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            }, tint = Teal200, contentDescription = "Empty"
        )
    }
}
