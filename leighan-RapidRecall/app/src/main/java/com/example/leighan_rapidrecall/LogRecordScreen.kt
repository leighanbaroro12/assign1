package com.example.leighan_rapidrecall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun LogRecordScreen (
    logs: MutableList<Log>,
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
            colors = ButtonDefaults.buttonColors(containerColor = themeColor.LIGHT_BLUE),
            modifier = Modifier.padding(10.dp)// make it a box in top left corner btw
        ) {
            Text(
                text = "HOME",
                fontSize = 15.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text (
            text = "LOG RECORDS",
            fontSize = 30.sp,
            color = themeColor.DARK_BLUE,
            modifier = Modifier.padding(25.dp)
        )
        Spacer(modifier = Modifier.height(75.dp))

        LazyColumn() {
            // make sure its not null list yet
            items(logs){ log ->
                LogRow(log)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}