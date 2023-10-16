package com.rsnimchenko.simple_arkanoid.game

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.rsnimchenko.simple_arkanoid.ui.MainActivity
import com.rsnimchenko.simple_arkanoid.util.FullScreen
import com.rsnimchenko.simple_arkanoid.util.SCORE

class GameActivity : AppCompatActivity() {
    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FullScreen.fullScreen(this)
        FullScreen.enableFullScreen(this)

        gameView = GameView(this)
        setContentView(gameView)
        onBackPressedDispatcher.addCallback {
            startActivity(Intent(this@GameActivity, MainActivity::class.java).apply {
                putExtra(SCORE, -1)
            })
            finish()
        }
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