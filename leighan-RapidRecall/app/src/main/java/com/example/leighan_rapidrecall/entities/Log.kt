// This entity class logs the data of a certain
// game, so it has a timeStamp, result, etc

package com.example.leighan_rapidrecall.entities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leighan_rapidrecall.ui.theme.Colors
import java.util.Date

class Log (
    val genSeq: Sequence,
    val userSeq: Sequence,
    val result: Boolean,
    val timeStamp: Date
)

@Composable
fun LogRow(
    log: Log,
    themeColors: Colors
){

    Box(
        modifier = Modifier
            .background(color = themeColors.DARK_BLUE,
                shape = RoundedCornerShape(10.dp))
            .padding(all = 16.dp),
    ) {
        Text(
            text = """
            Generated Sequence: 
            ${log.genSeq.sequence}
            
            User Sequence:  
            ${log.userSeq.sequence}
            
            Correct Sequence?:  
            ${log.result}
            
            Time Stamp: 
            ${log.timeStamp}
            """,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}