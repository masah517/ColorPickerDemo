package com.masa.color_picker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.github.ajalt.colormath.model.HSV
import com.github.ajalt.colormath.model.RGB
import com.masa.color_picker.harmony.ColorHarmonyMode

data class HsvColor(
    // 0 ~ 360
    val hue: Float,
    val saturation: Float,
    // 0 ~ 1
    val value: Float,
    // 0 ~ 1
    val alpha: Float,
){
    companion object{

        private fun HSV.toColor() : HsvColor {
            return HsvColor(
                hue = if(this.h.isNaN()) 0f else this.h,
                saturation = this.s,
                value = this.v,
                alpha = this.alpha,
            )
        }

        fun convertFrom(color: Color): HsvColor {
            return RGB(
                color.red,
                color.green,
                color.blue,
                color.alpha,
            ).toHSV().toColor()
        }
    }

    fun toColor(): Color{
        val hsv = HSV(hue, saturation, value, alpha)
        val rgb = hsv.toSRGB()
        return Color(rgb.redInt, rgb.greenInt, rgb.blueInt, rgb.alphaInt)
    }

    fun getComplementaryColor(): List<HsvColor> {
        return listOf(
            this.copy(saturation = (saturation + 0.1f).coerceAtMost(1f), value = (value + 0.3f).coerceIn(0.0f, 1f)),
            this.copy(saturation = (saturation - 0.1f).coerceAtMost(1f), value = (value - 0.3f).coerceIn(0.0f, 1f)),
            this.copy(hue = (hue + 180) % 360), // actual complementary
            this.copy(hue = (hue + 180) % 360, saturation = (saturation + 0.2f).coerceAtMost(1f), value = (value - 0.3f).coerceIn(0.0f, 1f))
        )
    }

    fun getSplitComplementaryColors(): List<HsvColor> {
        return listOf(
            this.copy(hue = (hue + 150) % 360, saturation = (saturation - 0.05f).coerceIn(0.0f, 1f), value = (value - 0.3f).coerceIn(0.0f, 1f)),
            this.copy(hue = (hue + 210) % 360, saturation = (saturation - 0.05f).coerceIn(0.0f, 1f), value = (value - 0.3f).coerceIn(0.0f, 1f)),
            this.copy(hue = (hue + 150) % 360), // actual
            this.copy(hue = (hue + 210) % 360) // actual
        )
    }

    fun getTriadicColors(): List<HsvColor> {
        return listOf(
            this.copy(hue = (hue + 120) % 360, saturation = (saturation - 0.05f).coerceIn(0.0f, 1f), value = (value - 0.3f).coerceIn(0.0f, 1f)),
            this.copy(hue = (hue + 120) % 360),
            this.copy(hue = (hue + 240) % 360, saturation = (saturation - 0.05f).coerceIn(0.0f, 1f), value = (value - 0.3f).coerceIn(0.0f, 1f)),
            this.copy(hue = (hue + 240) % 360)
        )
    }

    fun getTetradicColors(): List<HsvColor> {
        return listOf(
            this.copy(saturation = (saturation + 0.2f).coerceIn(0.0f, 1f)), // bonus one
            this.copy(hue = (hue + 90) % 360),
            this.copy(hue = (hue + 180) % 360),
            this.copy(hue = (hue + 270) % 360)
        )
    }

    fun getAnalagousColors(): List<HsvColor> {
        return listOf(
            this.copy(hue = (hue + 30) % 360),
            this.copy(hue = (hue + 60) % 360),
            this.copy(hue = (hue + 90) % 360),
            this.copy(hue = (hue + 120) % 360)
        )
    }

    fun getMonochromaticColors(): List<HsvColor> {
        return listOf(
            this.copy(saturation = (saturation + 0.2f).mod(1f)),
            this.copy(saturation = (saturation + 0.4f).mod(1f)),
            this.copy(saturation = (saturation + 0.6f).mod(1f)),
            this.copy(saturation = (saturation + 0.8f).mod(1f))
        )
    }

    fun getShadeColors(): List<HsvColor> {
        return listOf(
            this.copy(value = (value - 0.10f).mod(1.0f).coerceAtLeast(0.2f)),
            this.copy(value = (value + 0.55f).mod(1.0f).coerceAtLeast(0.55f)),
            this.copy(value = (value + 0.30f).mod(1.0f).coerceAtLeast(0.3f)),
            this.copy(value = (value + 0.05f).mod(1.0f).coerceAtLeast(0.2f))
        )
    }

    fun getColors(colorHarmonyMode: ColorHarmonyMode): List<HsvColor> {
        return when(colorHarmonyMode){
            ColorHarmonyMode.NONE -> emptyList()
            ColorHarmonyMode.COMPLEMENTARY -> getComplementaryColor()
            ColorHarmonyMode.ANALOGOUS -> getAnalagousColors()
            ColorHarmonyMode.SPLIT_COMPLEMENTARY -> getSplitComplementaryColors()
            ColorHarmonyMode.TRIADIC -> getTriadicColors()
            ColorHarmonyMode.TETRADIC -> getTetradicColors()
            ColorHarmonyMode.MONOCHROMATIC -> getMonochromaticColors()
            ColorHarmonyMode.SHADES -> getShadeColors()
        }
    }
}
