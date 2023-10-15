package com.rsnimchenko.simple_arkanoid

import android.graphics.RectF

class Paddle(screenX: Int, screenY: Int) {
    private var x = screenX / 2f - PADDLE_WIDTH / 2f
    private var y = screenY - PADDLE_HEIGHT * 2
    val rect: RectF = RectF(x, y, x + PADDLE_WIDTH, y + PADDLE_HEIGHT)

    private val paddleSpeed = 1000f

    private var paddleMoving = PaddleMoving.STOPPED

    fun setMovementState(paddleMoving: PaddleMoving) {
        this.paddleMoving = paddleMoving
    }
    fun update(fps: Long) {
        when(paddleMoving) {
            PaddleMoving.LEFT -> x -= paddleSpeed / fps
            PaddleMoving.RIGHT -> x += paddleSpeed / fps
            else -> {}
        }

        rect.let {
            it.left = x
            it.right = x + PADDLE_WIDTH
        }
    }
}

enum class PaddleMoving {
    STOPPED, LEFT, RIGHT
}