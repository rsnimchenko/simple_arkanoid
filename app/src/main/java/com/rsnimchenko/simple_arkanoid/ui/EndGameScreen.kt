package com.rsnimchenko.simple_arkanoid.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsnimchenko.simple_arkanoid.util.SharedPrefUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EndGameScreen(
    score: Int,
    toMain: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        val history = SharedPrefUtil.readHistory()
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US)
        SharedPrefUtil.writeHistory(history + "Date: ${formatter.format(Date())} Score: $score|")
    }
    BackHandler { toMain() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 170, 255, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Game over", color = Color.White, fontSize = 56.sp)
        Text(
            text = "${if (score == 240) "You have Won" else "You have Lost"} with $score score",
            color = Color.White,
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(100.dp))
        Button(onClick = { toMain() }) {
            Text(text = "Menu", color = Color.White, fontSize = 24.sp)
        }
    }
}