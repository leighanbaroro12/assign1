// I had to split up the state from the gamePlay composable
// this just tracks the variables, so I used 'by'
// in order to notify the composable to update it

package com.example.leighan_rapidrecall.entities

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Date

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