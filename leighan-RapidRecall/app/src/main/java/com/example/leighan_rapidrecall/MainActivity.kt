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
import androidx.compose.ui.Modifier
import com.example.leighan_rapidrecall.boundaries.RapidRecallScreen
import com.example.leighan_rapidrecall.entities.Log
import com.example.leighan_rapidrecall.ui.theme.Colors
import com.example.leighan_rapidrecall.ui.theme.LeighanRapidRecallTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val logs = mutableListOf<Log>()
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