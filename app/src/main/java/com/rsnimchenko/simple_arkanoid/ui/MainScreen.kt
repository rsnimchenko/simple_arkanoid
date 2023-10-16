package com.rsnimchenko.simple_arkanoid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(
    toGame: () -> Unit,
    toHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 170, 255, 255)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Simple Arkanoid", color = Color.White, fontSize = 46.sp)
        Spacer(modifier = Modifier.height(50.dp))
        Row {
            Button(onClick = { toGame() }) {
                Text(text = "Play", color = Color.White, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(100.dp))
            Button(onClick = { toHistory() }) {
                Text(text = "History", color = Color.White, fontSize = 24.sp)
            }
        }
    }
}