package com.canopas.campose.spotifyplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.campose.spotifyplaylist.compose.ContinueListeningList
import com.canopas.campose.spotifyplaylist.compose.SpotifyLibrary
import com.canopas.campose.spotifyplaylist.ui.theme.SpotifyPlaylistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotifyPlaylistTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = Color.Black) {
                    SpotifyHome()
                }
            }
        }
    }
}

@Composable
fun SpotifyHome() {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(8.dp)
        ) {
            item {
                SpotifyTitle("Continue  Listening")
                ContinueListeningList()
                SpotifyTitle(text = "Your Library")
                SpotifyLibrary()
            }
        }
    }
}

@Composable
fun SpotifyTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color=Color.White,
        style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
        modifier = modifier.padding(start = 8.dp, end = 4.dp, bottom = 8.dp, top = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotifyPlaylistTheme {
        SpotifyHome()
    }
}