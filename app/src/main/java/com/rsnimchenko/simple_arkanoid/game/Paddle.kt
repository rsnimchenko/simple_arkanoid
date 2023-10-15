package com.rsnimchenko.simple_arkanoid.game

import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Paddle(private val screenX: Int) {
    private var x = screenX / 2f - PADDLE_WIDTH / 2f
    val rect: RectF = RectF()
    var paint = Paint().apply { color = Color.argb(255, 255, 255, 255) }

    private val paddleSpeed = 1000f

    var paddleMoving = PaddleMoving.STOPPED
        private set

    fun setMovementState(paddleMoving: PaddleMoving) {
        this.paddleMoving = paddleMoving
    }

    fun update(fps: Long) {
        when (paddleMoving) {
            PaddleMoving.LEFT -> if (rect.left > 0f) x -= paddleSpeed / fps
            PaddleMoving.RIGHT -> if (rect.right < screenX) x += paddleSpeed / fps
            else -> {}
        }

        rect.let {
            it.left = x
            it.right = x + PADDLE_WIDTH
        }
    }

    fun reset(screenX: Int, screenY: Int) {
        paint.color = Color.argb(255, 255, 255, 255)

        rect.left = screenX / 2f - PADDLE_WIDTH / 2f
        rect.top = screenY - PADDLE_HEIGHT * 2
        rect.right = rect.left + PADDLE_WIDTH
        rect.bottom = rect.top + PADDLE_HEIGHT

    }
}

enum class PaddleMoving {
    STOPPED, LEFT, RIGHT
}