package com.example.leighan_rapidrecall.boundaries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leighan_rapidrecall.ui.theme.Colors
import com.example.leighan_rapidrecall.entities.FlashSequence
import com.example.leighan_rapidrecall.controller.GamePlayController
import com.example.leighan_rapidrecall.entities.GamePlayState
import com.example.leighan_rapidrecall.entities.Log
import com.example.leighan_rapidrecall.entities.ShowDigit
import com.example.leighan_rapidrecall.entities.Summary

@Composable
fun GameScreen (
    logs: MutableList<Log>,
    onExit: (Int) -> Unit,
    summaryObj: Summary,
    themeColor: Colors,
    modifier: Modifier = Modifier
) {
    val gamePlayState = remember { GamePlayState() }
    val gamePlayController = remember { GamePlayController(gamePlayState) }

    val homeScreen = 0
    val flashDigitScreen = 1
    val userGuessScreen = 2
    val resultsScreen = 3

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = themeColor.LIGHT_BIEGE)
            .padding(top = 50.dp, start = 20.dp, end = 20.dp, bottom = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { onExit(0) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = themeColor.DARK_BLUE),
            modifier = Modifier.padding(10.dp)// make it a box in top left corner btw
        ) {
            Text(
                text = "HOME",
                fontSize = 15.sp
            )
        }

        Text(
            text = "GAME PLAY",
            fontSize = 40.sp,
            color = themeColor.DARK_BLUE,
            modifier = Modifier.padding(35.dp)
        )

        when (gamePlayState.currentScreen) {
            // Home Screen gets the size of sequence from user
            homeScreen-> {
                Button(
                    onClick = {
                        if (gamePlayController.validateSeqLen(gamePlayState.userSeqLenStr) != 1) {
                            gamePlayState.currentScreen = 0
                        } else {
                            gamePlayState.seqLen = gamePlayState.userSeqLenStr.toInt()
                            gamePlayState.currentScreen = 1
                            gamePlayController.generateSequence()
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor.LIGHT_BLUE),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Enter",
                        fontSize = 20.sp
                    )
                }

                OutlinedTextField(
                    value = gamePlayState.userSeqLenStr,
                    // FIX THIS BELOW
                    onValueChange = { gamePlayState.userSeqLenStr = it},
                    label = { Text("Number of Digits") }
                )
            }

            flashDigitScreen -> {
                FlashSequence(
                    gamePlayState.seqLen,
                    gamePlayState.genSeq.sequence,
                    themeColor,
                    gamePlayState
                )
            }

            userGuessScreen -> {
                Button(
                    onClick = {

                        if (gamePlayController.validateUserSeq(gamePlayState.userSeqStr) != 1) {
                            gamePlayState.currentScreen = 2
                        } else {
                            gamePlayState.currentScreen = 3
                            gamePlayController.safeConversion(gamePlayState.userSeqStr)
                            gamePlayState.results = (gamePlayState.userSeq.sequence == gamePlayState.genSeq.sequence)

                            gamePlayController.comparisonsResult(
                                gamePlayState.userSeq.sequence,
                                gamePlayState.genSeq.sequence
                            )
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor.LIGHT_BLUE),
                    modifier = Modifier
                        .padding(8.dp)
                        //  .weight(50.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Enter",
                        fontSize = 20.sp
                    )
                }

                OutlinedTextField(
                    value = gamePlayState.userSeqStr,
                    onValueChange = { gamePlayState.userSeqStr = it},
                    label = { Text("Enter Guess") },
                )
            }

            // This Screen gives feedback to user
            resultsScreen -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = themeColor.DARK_BLUE, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(gamePlayState.results) "CORRECT" else "INCORRECT",
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.padding(25.dp)
                    )
                }

                LazyColumn() {
                    itemsIndexed(gamePlayState.userSeq.sequence){ index, digit ->
                        var resultColor: Color
                        resultColor = if (gamePlayState.comparisons[index]) themeColor.LIGHT_GREEN
                        else themeColor.LIGHT_RED
                        ShowDigit(digit, resultColor)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

                // Just updating the logs and summary objects below
                logs.add(
                    Log(
                        gamePlayState.genSeq,
                        gamePlayState.userSeq,
                        (gamePlayState.userSeq.sequence == gamePlayState.genSeq.sequence),
                        gamePlayState.timeStamp
                    )
                )

                summaryObj.increaseTotalAttempts()
                if (gamePlayState.userSeq.sequence == gamePlayState.genSeq.sequence) {
                    summaryObj.increaseCorrectAttempts()
                }
                summaryObj.setAccuracy(summaryObj.totalAttempts, summaryObj.correctAttempts)
            }
        }
    }
}
