package com.rsnimchenko.simple_arkanoid.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rsnimchenko.simple_arkanoid.util.SharedPrefUtil

@Composable
fun HistoryScreen(
    toBack: () -> Unit
) {
    val historyList = remember { mutableStateOf<List<String>>(listOf()) }
    LaunchedEffect(key1 = Unit) {
        val history = SharedPrefUtil.readHistory().split("|").toMutableList()
            .apply { removeIf { it.isEmpty() } }
        historyList.value = history
    }
    BackHandler { toBack() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0, 170, 255, 255))
            .padding(15.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { toBack() }, modifier = Modifier.align(Alignment.TopStart)) {
                Text(text = "Back", color = Color.White, fontSize = 24.sp)
            }
            Text(
                text = "History",
                color = Color.White,
                fontSize = 46.sp,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(
                items = historyList.value
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = it,
                        color = Color.Black,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}