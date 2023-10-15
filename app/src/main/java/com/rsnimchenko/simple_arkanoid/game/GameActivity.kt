package com.rsnimchenko.simple_arkanoid.game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsnimchenko.simple_arkanoid.FullScreen

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FullScreen.fullScreen(this)
        FullScreen.enableFullScreen(this)

        gameView = GameView(this)
        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }
}