package com.canopas.campose.spotifyplaylist.compose

import android.graphics.Bitmap
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.palette.graphics.Palette
import com.canopas.campose.spotifyplaylist.data.Album
import com.canopas.campose.spotifyplaylist.data.DataProvider

@Composable
fun PlaylistDetailView(album: Album) {

    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    val image = ImageBitmap.imageResource(context.resources, id = album.imageId).asAndroidBitmap()
    val swatch = remember(album.id) { image.generateDominantColorState() }
    val dominantColor = Color(swatch.rgb)

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        PlaylistInfoView(album = album, scrollState = scrollState)
        TopSectionOverlay(scrollState = scrollState)
        BottomScrollableView(scrollState = scrollState)
        ToolbarView(album = album, scrollState = scrollState, color = dominantColor)
    }
}

@Composable
fun ToolbarView(album: Album, scrollState: ScrollState, color: Color) {
    Box {
        Box(
            modifier = Modifier
                .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
                .fillMaxWidth()
                .height(56.dp)
                .background(color)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack, tint = MaterialTheme.colors.onSurface,
                contentDescription = null
            )
            Text(
                text = album.song,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(16.dp)
                    .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
            )
            Icon(
                imageVector = Icons.Default.MoreVert, tint = MaterialTheme.colors.onSurface,
                contentDescription = null
            )
        }

    }

}

@Composable
fun BottomScrollableView(scrollState: ScrollState) {
    Column(modifier = Modifier.verticalScroll(state = scrollState)) {
        Spacer(modifier = Modifier.height(480.dp))
        Column {
            SongListSectionView()
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}


@Composable
fun SongListSectionView() {
    ShuffleButton()
    SwitchDownload()
    val items = remember { DataProvider.albums }
    items.forEach {
        SongListItem(album = it)
    }
}

@Composable
fun SongListItem(album: Album) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(55.dp)
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .weight(1f)
        ) {
            Text(
                text = album.song,
                style = typography.h6.copy(fontSize = 16.sp),
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = "${album.artist}, ${album.descriptions}",
                style = typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (album.id % 4 == 0) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier
                    .padding(4.dp)
                    .size(20.dp)
            )
        }
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun SwitchDownload() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Download",
            style = typography.h6.copy(fontSize = 14.sp),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(8.dp)
        )
        val switched = remember { mutableStateOf(true) }
        Switch(
            checked = switched.value, onCheckedChange = { switched.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.primary
            ), modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ShuffleButton() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff388e3c)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 100.dp)
            .clip(CircleShape),
    ) {
        Text(
            text = "SHUFFLE PLAY",
            style = typography.h6.copy(fontSize = 14.sp),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        )
    }
}

fun Bitmap.generateDominantColorState(): Palette.Swatch = Palette.Builder(this)
    .resizeBitmapArea(0)
    .maximumColorCount(16)
    .generate()
    .swatches
    .maxByOrNull { swatch -> swatch.population }!!
