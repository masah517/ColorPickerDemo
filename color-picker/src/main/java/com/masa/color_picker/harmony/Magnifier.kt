package com.masa.color_picker.harmony

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masa.color_picker.HsvColor

@Composable
internal fun Magnifier(position: Offset, color: HsvColor, diameter: Dp) {
    val offset = with(LocalDensity.current){
        Modifier.offset(
            position.x.toDp() - diameter / 2,
            position.y.toDp() - diameter /2,
        )
    }

    Column (offset.size(width = diameter, height =  diameter)){
        MagnifierSelectionCircle(Modifier.size(diameter), color)
    }
}

@Composable
private fun MagnifierSelectionCircle(modifier: Modifier, color: HsvColor){
    Surface(
        modifier,
        shape = CircleShape,
        elevation = 4.dp,
        color = color.toColor(),
        border = BorderStroke(2.dp, SolidColor(Color.White)),
        content = {}
    )
}
