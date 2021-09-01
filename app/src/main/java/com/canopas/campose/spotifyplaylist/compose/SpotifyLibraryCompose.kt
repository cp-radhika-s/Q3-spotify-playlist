package com.canopas.campose.spotifyplaylist.compose

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.spotifyplaylist.ALBUM
import com.canopas.campose.spotifyplaylist.PlaylistDetailActivity
import com.canopas.campose.spotifyplaylist.data.Album
import com.canopas.campose.spotifyplaylist.data.DataProvider
import kotlin.random.Random

@Composable
fun SpotifyLibrary() {
    val albums = remember { DataProvider.albums }
    val context = LocalContext.current
    VerticalGrid(maxColumnWidth = 250.dp) {
        albums.forEach {
            PlaylistView(album = it, context = context)
        }
    }
}

@Composable
fun PlaylistView(album: Album, context: Context) {
    val randomHeight = remember(album.id) { Random.nextInt(150, 300).dp }
    Card(elevation = 8.dp, modifier = Modifier
        .padding(6.dp)
        .clickable {
            val intent = Intent(context, PlaylistDetailActivity::class.java).apply {
                putExtra(ALBUM, album)
            }
            context.startActivity(intent)
        }) {

        Column {
            Image(
                painter = painterResource(album.imageId),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.height(randomHeight)
            )
            Text(
                text = album.artist,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.h6.copy(fontSize = 14.sp)
            )
        }


    }

}