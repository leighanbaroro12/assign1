package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    val millis by mutableStateOf(System.currentTimeMillis())
    val timeStamp by mutableStateOf(Date(millis))
}

class GamePlayController (
    val gamePlayState: GamePlayState
) {

    fun generateSequence() {
        val randInts = mutableListOf<Int>()
        val firstDigit = (1..9).random()

        for (i in 1..(gamePlayState.seqLen-1)) {
            val randDigit = (0..9).random()
            randInts.add(randDigit)
        }
        gamePlayState.genSeq = Sequence(randInts, gamePlayState.seqLen)
    }

    fun comparisonsResult(userSeq: MutableList<Int>, genSeq: MutableList<Int>) {
        for (i in 1..(gamePlayState.seqLen-1)) {
            gamePlayState.comparisons.add(userSeq[i] == genSeq[i])
        }
    }


    // Do these later on - Data validation not necessary rn
    fun safeConversion(inputStr: String) {
        var convertedSequence = mutableListOf<Int>()
        for (i in 1..(gamePlayState.seqLen-1)) {
            convertedSequence.add((inputStr[i]).toInt())
        }
        gamePlayState.userSeq = Sequence(convertedSequence, gamePlayState.seqLen)
    }

    fun strToInt(inputStr: String, outputInt: Int) {
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
            colors = ButtonDefaults.buttonColors(containerColor = themeColor.LIGHT_BLUE),
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
        )
        Spacer(modifier = Modifier.height(65.dp))


        when (gamePlayState.currentScreen) {
            // 'Home Screen' of GamePlay Screen
            0 -> {

                OutlinedTextField(
                    value = gamePlayState.userSeqLenStr,
                    // FIX THIS BELOW
                    onValueChange = { gamePlayState.userSeqLenStr = it},
                    label = { Text("Number of Digits") },
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        gamePlayState.seqLen = gamePlayState.userSeqLenStr.toInt()
                        gamePlayState.currentScreen = 1
                        gamePlayController.generateSequence()
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor.DARK_BLUE),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Enter",
                        fontSize = 20.sp
                    )
                }
            }

            // Screen Flashes the Random Digits
            1 -> {
                /*
                LaunchedEffect(Unit) {
                    for (digit in gamePlayState.genSeq.sequence) {
                        FlashDigit(digit, themeColor.DARK_BLUE)
                    }
                }

                 */
                Button(
                    onClick = { gamePlayState.currentScreen = 2 },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor.DARK_BLUE),
                    modifier = Modifier
                        .padding(8.dp)
                        .height(35.dp)
                ) {
                    Text(
                        text = "Enter",
                        fontSize = 20.sp
                    )
                }
                // problem now is it only shows n - 1 digits
                // then immediately exits the app completely
                // look at how this composable runs below
                // FlashDigit(gamePlayState.seqLen, gamePlayState.genSeq.sequence, themeColor, gamePlayState)
                LazyColumn() {
                    // make sure its not null list yet
                    items(gamePlayState.genSeq.sequence){ digit ->
                        Box(
                            modifier = Modifier
                                .background(color = themeColor.LIGHT_BLUE,
                                    shape = RoundedCornerShape(10.dp))
                                .padding(all = 16.dp),
                        ) {
                            Text(
                                text = digit.toString()
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

            }

            // Screen asks for a guess - user input
            2 -> {

                Button(
                    onClick = {
                        gamePlayState.currentScreen = 3
                        gamePlayController.safeConversion(gamePlayState.userSeqStr)

                        gamePlayController.comparisonsResult(
                            gamePlayState.userSeq.sequence,
                            gamePlayState.genSeq.sequence
                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = themeColor.DARK_BLUE),
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
                    modifier = Modifier.weight(1f)
                )
            }

            // Screen gives feedback to user
            3 -> {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = themeColor.DARK_BLUE),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "GAME RESULTS",
                        fontSize = 30.sp
                    )
                }

                Text(
                    text = "",
                    fontSize = 30.sp
                )

                LazyColumn() {
                    itemsIndexed(gamePlayState.userSeq.sequence){ index, digit ->
                        var resultColor: Color
                        if (gamePlayState.comparisons[index]) resultColor = themeColor.LIGHT_GREEN
                        else resultColor = themeColor.LIGHT_RED
                        ShowDigit(digit, resultColor)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }

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
            }
        }
    }
}
