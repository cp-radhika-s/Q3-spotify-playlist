package com.canopas.campose.spotifyplaylist.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import kotlin.math.ceil

@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {

    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val placeableXY: MutableMap<Placeable, Pair<Int, Int>> = mutableMapOf()

        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns

        val itemConstrains = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 }

        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstrains)
            placeableXY[placeable] = Pair(columnWidth * column, colHeights[column])
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            placeables.forEach { placeable ->
                placeable.place(
                    x = placeableXY.getValue(placeable).first,
                    y = placeableXY.getValue(placeable).second
                )
            }
        }
    }
}


private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}