package com.masa.color_picker.harmony

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.masa.color_picker.HsvColor
import com.masa.color_picker.toRadian
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun HarmonyColorMagnifiers(
    diameterPx: Int,
    hsvColor: HsvColor,
    animateChanges: Boolean,
    currentlyChangingInput: Boolean,
    harmonyMode: ColorHarmonyMode,
){
    val size = IntSize(diameterPx, diameterPx)
    val position = remember(hsvColor, size) {
        positionForColor(hsvColor, size)
    }

    val positionAnimated = remember {
        Animatable(position, typeConverter = Offset.VectorConverter)
    }

    LaunchedEffect(hsvColor, size, animateChanges){
        if(!animateChanges){
            positionAnimated.snapTo(positionForColor(hsvColor, size))
        }else{
            positionAnimated.animateTo(
                positionForColor(hsvColor, size),
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
            )
        }
    }

    val diameterDp = with(LocalDensity.current){
        diameterPx.toDp()
    }

    val animatedDiameter = animateDpAsState(
        targetValue = if(!currentlyChangingInput){
            diameterDp * diameterMainColorDragging
        }else{
            diameterDp * diameterMainColor
        }
    )

    hsvColor.getColors(harmonyMode).forEach { color ->
        val positionForColor = remember {
            Animatable(positionForColor(color, size), typeConverter = Offset.VectorConverter)
        }
        LaunchedEffect(color, size, animateChanges){
            if(!animateChanges){
                positionForColor.snapTo(positionForColor(color, size))
            }else{
                positionForColor.animateTo(
                    positionForColor(color, size),
                    animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
                )
            }
        }
        Magnifier(position = positionForColor.value, color = color, diameter = diameterDp * diameterHarmonyColor)
    }
    Magnifier(position = positionAnimated.value, color = hsvColor, diameter = animatedDiameter.value)
}

internal fun positionForColor(color:HsvColor, size: IntSize): Offset{
    val radians = color.hue.toRadian() // 角度、色相(Hue)をラジアンで表現
    val phi = color.saturation // 半径、彩度(Saturation)を表現　値は0~１（100％）

    /** phiは彩度で0~１が極値となり、cosやsinの値の範囲は -1 ~ 1 になります。
     * 　そこで上記-1 ~ 1を最終的に値をOutputとして0~1の範囲内に収めるために、 +1 してから2で除算処理(割り算)をします。
     */
    val x: Float = ((phi * cos(radians)) + 1) / 2f
    val y: Float = ((phi * sin(radians)) + 1) / 2f

    return Offset(
        x = (x * size.width),
        y = (y * size.height)
    )
}

private const val diameterHarmonyColor = 0.10f
private const val diameterMainColorDragging = 0.18f
private const val diameterMainColor = 0.15f
