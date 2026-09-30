// Personal Check - Good UI/Clean Code
package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RapidRecallScreen (
    logs: MutableList<Log>,
    themeColor: Colors,
    modifier: Modifier = Modifier
) {

    // Screens are numbered, using a when statement to switch between the screens
    var currentScreen: Int by remember { mutableIntStateOf(0) }
    var summaryObj: Summary by remember { mutableStateOf(Summary(0, 0)) }

    val homeScreen = 0
    val logRecordScreen = 1
    val gamePlayScreen = 2
    val summaryScreen = 3

    when (currentScreen) {
        homeScreen ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = themeColor.LIGHT_BIEGE)
                    .padding(top = 100.dp, start = 20.dp, end = 20.dp, bottom = 35.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Rapid Recall",
                    fontSize = 55.sp,
                    color = themeColor.DARK_BLUE
                )

                Spacer(modifier = Modifier.padding(top = 35.dp))
                Text(
                    text = "Games Played: ${logs.size}",
                    fontSize = 20.sp,
                    color = themeColor.DARK_BLUE
                )

                Button(
                    onClick = { currentScreen = 2 },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(13, 51, 93)),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(215.dp)
                        .height(115.dp)
                ) {
                    Text(
                        text = "START",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(20.dp)
                    )
                }

                Button(
                    onClick = { currentScreen = 1 },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(13, 51, 93)),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(215.dp)
                        .height(115.dp)
                ) {
                    Text(
                        text = "LOGS",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(20.dp),
                    )
                }

                Button(
                    onClick = { currentScreen = 3 },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(13, 51, 93)),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(215.dp)
                        .height(115.dp)
                ) {
                    Text(
                        text = "SUMMARY",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(15.dp)
                    )
                }
            }

        logRecordScreen -> {
            LogRecordScreen(
                logs = logs,
                onExit = { screenNumber -> currentScreen = screenNumber },
                themeColor
            )
        }

        gamePlayScreen -> {
            GameScreen(
                logs = logs,
                onExit = { screenNumber -> currentScreen = screenNumber },
                summaryObj,
                themeColor
            )
        }

        summaryScreen -> {
            SummaryScreen(
                summaryObj,
                onExit = { screenNumber -> currentScreen = screenNumber },
                themeColor
            )
        }
    }
}