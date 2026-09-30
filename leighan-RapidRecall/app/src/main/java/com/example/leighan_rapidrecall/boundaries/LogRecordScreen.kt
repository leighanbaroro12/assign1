package com.example.leighan_rapidrecall.boundaries

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import com.example.leighan_rapidrecall.ui.theme.Colors
import com.example.leighan_rapidrecall.entities.Log
import com.example.leighan_rapidrecall.entities.LogRow

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
            .padding(top = 100.dp, start = 20.dp, end = 20.dp, bottom = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = { onExit(0) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = themeColor.DARK_BLUE),
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "HOME",
                fontSize = 20.sp
            )
        }

        Text (
            text = "LOG RECORDS",
            fontSize = 45.sp,
            color = themeColor.DARK_BLUE,
            modifier = Modifier.padding(35.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn() {
            items(logs){ log ->
                LogRow(log, themeColor)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}