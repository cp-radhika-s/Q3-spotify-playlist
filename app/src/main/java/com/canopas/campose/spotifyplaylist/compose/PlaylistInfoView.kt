package com.canopas.campose.spotifyplaylist.compose

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.canopas.campose.spotifyplaylist.data.Album

@Composable
fun PlaylistInfoView(album: Album, scrollState: ScrollState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        val dynamicValue =
            if (250.dp - Dp(scrollState.value / 50f) < 5.dp) 5.dp
            else 250.dp - Dp(scrollState.value / 20f)

        val animateImageScale = animateDpAsState(targetValue = dynamicValue).value
        Image(
            painter = painterResource(id = album.imageId), contentDescription = null,
            modifier = Modifier
                .size(animateImageScale)
                .padding(8.dp)
        )
        Text(text = album.song,
            style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.onSurface)
        Text(
            text = "FOLLOWING",
            color = MaterialTheme.colors.onSurface,
            style = typography.h6.copy(fontSize = 12.sp),
            modifier = Modifier
                .padding(4.dp)
                .border(
                    border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(vertical = 4.dp, horizontal = 24.dp)
        )
        Text(
            text = album.descriptions,
            style = typography.subtitle2,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
fun TopSectionOverlay(scrollState: ScrollState) {
    val dynamicAlpha = ((scrollState.value + 0.00f) / 1000).coerceIn(0f, 1f)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(
                MaterialTheme.colors.surface.copy(
                    alpha = animateFloatAsState(dynamicAlpha).value
                )
            )
    )
}