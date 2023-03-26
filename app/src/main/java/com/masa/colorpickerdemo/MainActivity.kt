package com.masa.colorpickerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.masa.color_picker.HsvColor
import com.masa.color_picker.harmony.ColorHarmonyMode
import com.masa.color_picker.harmony.HarmonyColorPicker
import com.masa.colorpickerdemo.ui.theme.ColorPickerDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorPickerDemoTheme {
                HarmonyColorPickerScreen()
            }
        }
    }
}

@Composable
fun HarmonyColorPickerScreen(){
    Column {
        var currentColor by remember {
            mutableStateOf(HsvColor.convertFrom(Color.Red))
        }
        var extraColors by remember {
            mutableStateOf(emptyList<HsvColor>())
        }
        var harmonyMode by remember {
            mutableStateOf(ColorHarmonyMode.ANALOGOUS)
        }

        HarmonyColorPicker(modifier = Modifier.size(400.dp), harmonyMode = harmonyMode){

        }
        /*
        HarmonyColorPicker(
            modifier = Modifier.size(400.dp),
            harmonyMode = harmonyMode,
            color = currentColor,
        ){ color ->
            currentColor = color
            extraColors = color.getColors(colorHarmonyMode = harmonyMode)
        }*/
    }
}
