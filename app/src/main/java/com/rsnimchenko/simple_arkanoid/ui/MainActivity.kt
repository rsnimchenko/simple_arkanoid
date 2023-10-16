package com.rsnimchenko.simple_arkanoid.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rsnimchenko.simple_arkanoid.ui.ui.theme.SimpleArkanoidTheme
import com.rsnimchenko.simple_arkanoid.util.FullScreen
import com.rsnimchenko.simple_arkanoid.util.SCORE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FullScreen.fullScreen(this)
        FullScreen.enableFullScreen(this)

        val score = intent.getIntExtra(SCORE, -1)
        setContent {
            SimpleArkanoidTheme {
                NavComponent(score = score)
            }
        }
    }
}