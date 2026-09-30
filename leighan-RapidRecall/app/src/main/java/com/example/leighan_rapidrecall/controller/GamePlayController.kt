// Controls the input from user and validates
// It then does the gamePlay functions

package com.example.leighan_rapidrecall.controller

import com.example.leighan_rapidrecall.entities.GamePlayState
import com.example.leighan_rapidrecall.entities.Sequence

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
            if (
                userSeqStr.length != gamePlayState.seqLen ||
                userSeqStr.toInt() < 0
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
            // Assignment requirement for seq len to be from 1-10
            if (seqLenStr.toInt() !in 1..10) throw IllegalArgumentException()
        } catch (e: IllegalArgumentException) {
            println("Error: Input Type is WRONG")
            return -1
        }
        return 1
    }
}