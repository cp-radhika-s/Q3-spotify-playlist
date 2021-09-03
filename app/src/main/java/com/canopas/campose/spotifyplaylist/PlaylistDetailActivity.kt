package com.canopas.campose.spotifyplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.canopas.campose.spotifyplaylist.compose.PlaylistDetailView
import com.canopas.campose.spotifyplaylist.data.Album

const val ALBUM = "album"


class PlaylistDetailActivity : ComponentActivity() {

    private val album: Album by lazy {
        intent?.getSerializableExtra(ALBUM) as Album
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { 
            PlaylistDetailView(album = album)
        }
    }
}