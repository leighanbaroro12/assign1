package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class Sequence(
    // Sequence is a list of ints so it's easier to loop through each digit
    val sequence: MutableList<Int> = mutableListOf(),
    val seqLen: Int = 0
)

@Composable
fun ShowDigit (
    digit: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = color, shape = RoundedCornerShape(10.dp))
            .width(140.dp)
            .height(140.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "$digit",
            fontSize = 45.sp,
            color = Color.White
        )
    }
}

@Composable
fun FlashSequence(
    seqLen: Int,
    digits: MutableList<Int>,
    themeColor : Colors,
    gamePlayState: GamePlayState
) {
    var digitIndex by remember { mutableIntStateOf(0) }
    val twoColors = listOf(themeColor.DARK_BLUE, themeColor.LIGHT_BLUE)
    var alternateColorIndex by remember { mutableIntStateOf(0) }

    if (digitIndex < seqLen ) {
        ShowDigit(
            digits[digitIndex],
            twoColors[alternateColorIndex]
        )
    }

    LaunchedEffect(digitIndex) {
        delay(1000.milliseconds)
        if (digitIndex < seqLen ) {
            alternateColorIndex = (digitIndex+1) % 2
            digitIndex++
        }
        else {
            gamePlayState.currentScreen = 2
        }
    }
}
