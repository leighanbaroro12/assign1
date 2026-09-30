package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun SummaryScreen (
    summaryObj: Summary,
    onExit: (Int) -> Unit,
    themeColor: Colors,
    modifier: Modifier = Modifier
) {

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
                fontSize = 20.sp
            )
        }

        Text(
            text = "SUMMARY",
            fontSize = 45.sp,
            color = themeColor.DARK_BLUE,
            modifier = Modifier.padding(35.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .padding(10.dp)
                .background(color = themeColor.DARK_BLUE,
                    shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = """
               TOTAL ATTEMPTS:    ${summaryObj.totalAttempts}
          
               CORRECT ATTEMPTS:    ${summaryObj.correctAttempts}
               
               ACCURACY ( % ):    ${summaryObj.accuracy}
               """,
                fontSize = 15.sp,
                color = Color.White,
                modifier = Modifier.padding(20.dp)
            )
        }
    }
}