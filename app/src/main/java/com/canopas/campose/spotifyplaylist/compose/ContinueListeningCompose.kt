package com.canopas.campose.spotifyplaylist.compose

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.canopas.campose.spotifyplaylist.ALBUM
import com.canopas.campose.spotifyplaylist.PlaylistDetailActivity
import com.canopas.campose.spotifyplaylist.data.Album
import com.canopas.campose.spotifyplaylist.data.DataProvider

@Composable
fun ContinueListeningList() {
    val albums = remember { DataProvider.albums }
    LazyRow(modifier = Modifier.height(180.dp)) {
        items(albums) {
            SpotifyAlbums(album = it)
        }
    }

}

@Composable
fun SpotifyAlbums(album: Album) {
    val context = LocalContext.current
    Column(modifier = Modifier
        .width(180.dp)
        .padding(8.dp)
        .clickable {
            val intent = Intent(context, PlaylistDetailActivity::class.java).apply {
                putExtra(ALBUM, album)
            }
            context.startActivity(intent)
        }) {

        Image(
            painter = painterResource(id = album.imageId), contentDescription = null,
            contentScale = ContentScale.Crop, modifier = Modifier
                .width(180.dp)
                .height(170.dp)
        )

        Text(
            text = "${album.song}: ${album.descriptions}",
            style = typography.body2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}


@Preview
@Composable
fun PreviewLaneItem() {
    val album = remember { DataProvider.album }
    SpotifyAlbums(album)
}
