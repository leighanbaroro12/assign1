package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.util.Date
import kotlin.time.Duration.Companion.milliseconds

class GamePlayState {
    var seqLen by mutableIntStateOf(0)
    var userSeq by mutableStateOf(Sequence())
    var genSeq by mutableStateOf(Sequence())
    var comparisons: MutableList<Boolean> = mutableListOf()

    var currentScreen by mutableIntStateOf(0)
    var userSeqStr by mutableStateOf("")
    var userSeqLenStr by mutableStateOf("")
    val millis by mutableLongStateOf(System.currentTimeMillis())
    val timeStamp by mutableStateOf(Date(millis))
    var results by mutableStateOf(false)
}

class GamePlayController (
    val gamePlayState: GamePlayState
) {
    fun generateSequence() {
        val randInts = mutableListOf<Int>()

        for (i in 0..(gamePlayState.seqLen-1)) {
            val randDigit = (0..9).random()
            randInts.add(randDigit)
        }
        gamePlayState.genSeq = Sequence(randInts, gamePlayState.seqLen)
    }

    fun comparisonsResult(userSeq: MutableList<Int>, genSeq: MutableList<Int>) {
        for (i in 0..(gamePlayState.seqLen-1)) {
            gamePlayState.comparisons.add(userSeq[i] == genSeq[i])
        }
    }

    fun safeConversion(inputStr: String) {
        var convertedSequence = mutableListOf<Int>()
        for (i in 0..(gamePlayState.seqLen-1)) {
            convertedSequence.add((inputStr[i]).digitToInt())
        }
        gamePlayState.userSeq = Sequence(convertedSequence, gamePlayState.seqLen)
    }

    // Returns 1 if valid input
    // Else returns -1 to indicate error
    fun validateUserSeq(userSeqStr: String): Int{
        try {
            userSeqStr.toInt()
            val userSeqStrLen = userSeqStr.length

            if (
                userSeqStr.toInt() < 1 ||
                userSeqStrLen != gamePlayState.seqLen
            ) throw IllegalArgumentException()

        } catch (e: IllegalArgumentException) {
            println("Error: Input Type is WRONG")
            return -1
        }
        return 1
    }

    // Similar to above
    fun validateSeqLen(seqLenStr: String): Int {
        try {
            seqLenStr.toInt()
            if (seqLenStr.toInt() < 1) throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("Error: Input Type is WRONG")
            return -1
        }
        return 1
    }
}

@Composable
fun GameScreen (
    logs: MutableList<Log>,
    onExit: (Int) -> Unit,
    summaryObj: Summary,
    themeColor: Colors,
    modifier: Modifier = Modifier
) {
    var gamePlayState = remember { GamePlayState() }
    var gamePlayController = remember { GamePlayController(gamePlayState) }

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
                            gamePlayState.results = (gamePlayState.userSeq.sequence.equals(gamePlayState.genSeq.sequence))

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
                        if (gamePlayState.comparisons[index]) resultColor = themeColor.LIGHT_GREEN
                        else resultColor = themeColor.LIGHT_RED
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
