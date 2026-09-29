// TEST 1:
// Summary Screen is okay, Log and Game Screen dont even function

package com.example.leighan_rapidrecall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.leighan_rapidrecall.ui.theme.LeighanRapidRecallTheme
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userSeq1 = Sequence(mutableListOf(1,2,3), 3)
        val genSeq1 = Sequence(mutableListOf(1,2,3), 3)
        val log1: Log = Log(genSeq1, userSeq1, false, Date(System.currentTimeMillis()))
        val log2: Log = Log(genSeq1, userSeq1, false, Date(System.currentTimeMillis()))
        val log3: Log = Log(genSeq1, userSeq1, false, Date(System.currentTimeMillis()))
        val log4: Log = Log(genSeq1, userSeq1, false, Date(System.currentTimeMillis()))
        val log5: Log = Log(genSeq1, userSeq1, false, Date(System.currentTimeMillis()))

        val logs = mutableListOf<Log>(log1,log2,log3,log4,log5)
        val themeColor = Colors()
        setContent {
            LeighanRapidRecallTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RapidRecallScreen(
                        logs,
                        themeColor,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}