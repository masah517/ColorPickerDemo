package com.masa.color_picker.harmony

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import com.masa.color_picker.HsvColor

@Composable
internal fun ColorWheel(
    hsvColor: HsvColor,
    diameter: Int,
) {
    val saturation = 1.0f
    val value = hsvColor.value

    val radius = diameter / 2f
    val alpha = 1.0f
    val colorSweepGradientBrush = remember(hsvColor.value, diameter) {
        val wheelColors = arrayOf(
            HsvColor(0f, saturation, value, alpha),
            HsvColor(60f, saturation, value, alpha),
            HsvColor(120f, saturation, value, alpha),
            HsvColor(180f, saturation, value, alpha),
            HsvColor(240f, saturation, value, alpha),
            HsvColor(300f, saturation, value, alpha),
            HsvColor(360f, saturation, value, alpha)
        ).map {
            it.toColor()
        }
        Brush.sweepGradient(wheelColors, Offset(radius, radius))
    }

    val saturationGradientBrush = remember(diameter) {
        Brush.radialGradient(
            listOf(Color.White, Color.Transparent),
            Offset(radius, radius),
            radius,
            TileMode.Clamp,
        )
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(colorSweepGradientBrush)
        drawCircle(saturationGradientBrush)


        drawCircle(
            hsvColor.copy(
                hue = 0F,
                saturation = 0F,
            ).toColor(),
            blendMode = BlendMode.Modulate
        )
    }
}